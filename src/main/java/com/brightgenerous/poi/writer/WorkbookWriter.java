package com.brightgenerous.poi.writer;

import static com.brightgenerous.poi.PoiMethods.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.brightgenerous.lang.Args;
import com.brightgenerous.poi.CellStyleRegister;

public class WorkbookWriter implements IWorkbookWriter {

    private final IWorkbookWriterStrategy workbookStrategy;

    private final TemplateLoader templateLoader;

    public WorkbookWriter(IWorkbookWriterStrategy workbookStrategy, TemplateLoader templateLoader) {
        Args.notNull(workbookStrategy, "workbookStrategy");

        this.workbookStrategy = workbookStrategy;
        this.templateLoader = templateLoader;
    }

    protected IWorkbookWriterStrategy getWorkbookStrategy() {
        return workbookStrategy;
    }

    protected TemplateLoader getTemplateLoader() {
        return templateLoader;
    }

    @Override
    public void write(OutputStream outputStream) throws IOException {
        Args.notNull(outputStream, "outputStream");

        IWorkbookWriterStrategy strategy = getWorkbookStrategy();
        boolean hasTemplate = false;

        Workbook workbook;
        TemplateLoader templateLoader = getTemplateLoader();
        if (strategy.getXlsxFlag()) {
            if (templateLoader != null) {
                try (InputStream is = templateLoader.loadXSSF()) {
                    workbook = new XSSFWorkbook(is);
                }
                if (0 < workbook.getNumberOfSheets()) {
                    hasTemplate = true;
                }
            } else {
                workbook = new XSSFWorkbook();
            }
        } else {
            if (templateLoader != null) {
                try (InputStream is = templateLoader.loadHSSF()) {
                    workbook = new HSSFWorkbook(is);
                }
                if (0 < workbook.getNumberOfSheets()) {
                    hasTemplate = true;
                }
            } else {
                workbook = new HSSFWorkbook();
            }
        }

        boolean empty = true;
        int index = 0;
        List<ISheetWriterStrategy> sheetStrategys = new ArrayList<>();
        CellStyleRegister cellStyleRegister = newCellStyleRegister(workbook);
        Set<String> sheetNameCache = new HashSet<>();
        Iterator<ISheetWriterStrategy> itr = strategy.getSheetStrategys();
        while (itr.hasNext()) {
            ISheetWriterStrategy sheetStrategy = itr.next();
            sheetStrategys.add(sheetStrategy);
            writeSheet(workbook, index, sheetStrategy, cellStyleRegister, hasTemplate,
                    sheetNameCache);

            index += 1;
            if (empty) {
                empty = false;
            }
        }

        if (empty) {
            ISheetWriterStrategy sheetStrategy = strategy.getEmptySheetStrategy();
            writeSheet(workbook, index, sheetStrategy, cellStyleRegister, hasTemplate,
                    sheetNameCache);
        }

        if (hasTemplate) {
            removeTemplate(workbook);
        }

        {
            int sheetNum = sheetStrategys.size();
            CreationHelper crateHelper = workbook.getCreationHelper();
            FormulaEvaluator evaluator = crateHelper.createFormulaEvaluator();
            for (int i = 0; i < sheetNum; i++) {
                ISheetWriterStrategy sheetStrategy = sheetStrategys.get(i);
                if (sheetStrategy.getConvertToString()) {
                    Sheet sheet = workbook.getSheetAt(i);
                    evaluateAllCell(evaluator, sheet);
                }
            }
            DataFormatter dataFormatter = new DataFormatter();
            for (int i = 0; i < sheetNum; i++) {
                ISheetWriterStrategy sheetStrategy = sheetStrategys.get(i);
                Sheet sheet = workbook.getSheetAt(i);
                if (sheetStrategy.getConvertToString()) {
                    convertAllCellToString(dataFormatter, sheet,
                            sheetStrategy.getFormatterRegister());
                }
            }
        }

        {
            // active
            setSelectActive(workbook);
        }

        workbook.write(outputStream);
    }

    protected void writeSheet(Workbook workbook, int index, ISheetWriterStrategy sheetStrategy,
            CellStyleRegister register, boolean hasTemplate, Set<String> sheetNameCache) {
        String sheetName = sheetStrategy.getSheetName(workbook, index);
        sheetName = escapeSheetName(sheetName);
        if (sheetNameCache.contains(sheetName)) {
            int idx = 0;
            String tmp = sheetName;
            while (sheetNameCache.contains(tmp)) {
                idx++;
                tmp = sheetName + " (" + idx + ")";
            }
            sheetName = tmp;
            sheetNameCache.add(sheetName);
        } else {
            sheetNameCache.add(sheetName);
        }
        Sheet sheet;
        if (hasTemplate) {
            sheet = cloneSheetFromTemplate(workbook, sheetName);
        } else {
            sheet = workbook.createSheet(sheetName);
        }
        {
            int idx = sheetStrategy.getWriter().getIndex();
            if ((0 <= idx) && (idx < index)) {
                index = idx;
                workbook.setSheetOrder(sheetName, index);
            }
        }
        sheetStrategy.getWriter().write(workbook, index, sheet, register);
    }

    protected Sheet cloneSheetFromTemplate(Workbook workbook, String sheetName) {
        int lastIndex = workbook.getNumberOfSheets() - 1;
        Sheet sheet = workbook.cloneSheet(lastIndex);
        int index = workbook.getSheetIndex(sheet);
        workbook.setSheetName(index, sheetName);
        workbook.setSheetOrder(sheetName, lastIndex);
        return workbook.getSheetAt(lastIndex);
    }

    protected void removeTemplate(Workbook workbook) {
        if (workbook instanceof XSSFWorkbook) {
            workbook.removeSheetAt(workbook.getNumberOfSheets() - 1);
        } else {
            workbook.setSheetHidden(workbook.getNumberOfSheets() - 1, true);
        }
    }

    protected void setSelectActive(Workbook workbook) {
        int num = workbook.getNumberOfSheets();
        select: {
            int first = -1;
            for (int i = 0; i < num; i++) {
                if (!workbook.isSheetHidden(i)) {
                    if (first != -1) {
                        first = i;
                    }
                    if (workbook.getSheetAt(i).isSelected()) {
                        break select;
                    }
                }
            }
            if (first != -1) {
                workbook.getSheetAt(first).setSelected(true);
            }
        }
        {
            int active = workbook.getActiveSheetIndex();
            if ((active < 0) || (num <= active) || workbook.isSheetHidden(active)) {
                for (int i = 0; i < num; i++) {
                    if (!workbook.isSheetHidden(i)) {
                        workbook.setActiveSheet(i);
                        break;
                    }
                }
            }
        }
    }
}
