package com.brightgenerous.poi.writer;

import static com.brightgenerous.commons.ObjectUtils.*;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.brightgenerous.poi.CellStyleRegister;
import com.brightgenerous.poi.FormatterRegister;

public class WorkbookWriterBuilder {

    // static default

    private static final String DEFAULT_EMPTY_SHEET_NAME = "sheet";

    private static final ISheetWriter DEFAULT_EMPTY_SHEET_WRITER = new AbstractSheetWriter() {

        @Override
        public void write(Workbook workbook, int index, Sheet sheet, CellStyleRegister register) {
        }
    };

    private static final Boolean DEFAULT_EMPTY_CONVERT_TO_STRING = Boolean.FALSE;

    private static final Boolean DEFAULT_USE_TEMPLATE_FLAG = Boolean.TRUE;

    private static final TemplateLoader DEFAULT_TEMPLATE_LOADER = new DefaultTemplateLoader();

    private static final FormatterRegister DEFAULT_EMPTY_FORMATTER_REGISTER = new FormatterRegister() {

        @Override
        public boolean isNumberFormat(String str) {
            return false;
        }

        @Override
        public boolean isDateFormat(String str) {
            return false;
        }

        @Override
        public NumberFormat getNumberFormat(String str) {
            return null;
        }

        @Override
        public DateFormat getDateFormat(String str) {
            return null;
        }
    };

    private static final Boolean DEFAULT_XLSX_FLAG = Boolean.FALSE;

    // specify

    private String emptySheetName;

    private ISheetWriter emptySheetWriter;

    private Boolean emptyConvertToString;

    private FormatterRegister emptyFormatterRegister;

    private Boolean xlsxFlag;

    private Boolean useTemplateFlag;

    private TemplateLoader templateLoader;

    private String defaultSheetName;

    private ISheetWriter defaultSheetWriter;

    private Boolean defaultConvertToString;

    private FormatterRegister defaultFormatterRegister;

    private final List<SheetStrategyHolder> sheetStrategyHolders = new ArrayList<>();

    protected WorkbookWriterBuilder() {
    }

    public static WorkbookWriterBuilder create() {
        return new WorkbookWriterBuilder();
    }

    public WorkbookWriterBuilder clear() {
        emptySheetName = null;
        emptySheetWriter = null;
        emptyConvertToString = null;
        emptyFormatterRegister = null;
        xlsxFlag = null;
        useTemplateFlag = null;
        templateLoader = null;
        defaultSheetName = null;
        defaultSheetWriter = null;
        defaultConvertToString = null;
        defaultFormatterRegister = null;
        sheetStrategyHolders.clear();
        return this;
    }

    public String emptySheetName() {
        return emptySheetName;
    }

    public WorkbookWriterBuilder emptySheetName(String sheetName) {
        emptySheetName = sheetName;
        return this;
    }

    public ISheetWriter emptySheetWriter() {
        return emptySheetWriter;
    }

    public WorkbookWriterBuilder emptySheetWriter(ISheetWriter sheetWriter) {
        emptySheetWriter = sheetWriter;
        return this;
    }

    public Boolean emptyConvertToString() {
        return emptyConvertToString;
    }

    public WorkbookWriterBuilder emptyConvertToString(Boolean convertToString) {
        emptyConvertToString = convertToString;
        return this;
    }

    public FormatterRegister emptyFormatterRegister() {
        return emptyFormatterRegister;
    }

    public WorkbookWriterBuilder emptyFormatterRegister(FormatterRegister formatterRegister) {
        emptyFormatterRegister = formatterRegister;
        return this;
    }

    public Boolean xlsxFlag() {
        return xlsxFlag;
    }

    public WorkbookWriterBuilder xlsxFlag(Boolean xlsxFlag) {
        this.xlsxFlag = xlsxFlag;
        return this;
    }

    public Boolean useTemplateFlag() {
        return useTemplateFlag;
    }

    public WorkbookWriterBuilder useTemplateFlag(Boolean useTemplateFlag) {
        this.useTemplateFlag = useTemplateFlag;
        return this;
    }

    public TemplateLoader templateLoader() {
        return templateLoader;
    }

    public WorkbookWriterBuilder templateLoader(TemplateLoader templateLoader) {
        return templateLoader(templateLoader, true);
    }

    public WorkbookWriterBuilder templateLoader(TemplateLoader templateLoader, boolean use) {
        this.templateLoader = templateLoader;
        useTemplateFlag = use ? Boolean.TRUE : Boolean.FALSE;
        return this;
    }

    public WorkbookWriterBuilder defaultSheetName(String sheetName) {
        defaultSheetName = sheetName;
        return this;
    }

    public WorkbookWriterBuilder defaultSheetWriter(ISheetWriter sheetWriter) {
        defaultSheetWriter = sheetWriter;
        return this;
    }

    public WorkbookWriterBuilder defaultConvetToString(Boolean convertToString) {
        defaultConvertToString = convertToString;
        return this;
    }

    public FormatterRegister defaultFormatterRegister() {
        return defaultFormatterRegister;
    }

    public WorkbookWriterBuilder defaultFormatterRegister(FormatterRegister formatterRegister) {
        defaultFormatterRegister = formatterRegister;
        return this;
    }

    public int sheets() {
        return sheetStrategyHolders.size();
    }

    public WorkbookWriterBuilder addSheet() {
        return addSheet((String) null);
    }

    public WorkbookWriterBuilder addSheet(String sheetName) {
        return addSheet(sheetName, null);
    }

    public WorkbookWriterBuilder addSheet(ISheetWriter sheetWriter) {
        return addSheet(null, sheetWriter);
    }

    public WorkbookWriterBuilder addSheet(String sheetName, ISheetWriter sheetWriter) {
        return addSheet(sheetName, sheetWriter, null);
    }

    public WorkbookWriterBuilder addSheet(String sheetName, ISheetWriter sheetWriter,
            Boolean convertToString) {
        return addSheet(sheetName, sheetWriter, convertToString, null);
    }

    public WorkbookWriterBuilder addSheet(String sheetName, ISheetWriter sheetWriter,
            Boolean convertToString, FormatterRegister formatterRegister) {
        SheetStrategyHolder sheetStrategyHolder = new SheetStrategyHolder();
        sheetStrategyHolder.sheetName = sheetName;
        sheetStrategyHolder.sheetWriter = sheetWriter;
        sheetStrategyHolder.convertToString = convertToString;
        sheetStrategyHolder.formatterRegister = formatterRegister;
        sheetStrategyHolders.add(sheetStrategyHolder);
        return this;
    }

    protected List<ISheetWriterStrategy> getSheetStrategys() {
        List<ISheetWriterStrategy> sheets = new ArrayList<>();
        for (SheetStrategyHolder holder : sheetStrategyHolders) {
            sheets.add(new SheetWriterStrategy(defaultIfNull(holder.sheetName, defaultSheetName,
                    emptySheetName, DEFAULT_EMPTY_SHEET_NAME), defaultIfNull(holder.sheetWriter,
                    defaultSheetWriter, emptySheetWriter, DEFAULT_EMPTY_SHEET_WRITER),
                    defaultIfNull(holder.convertToString, defaultConvertToString,
                            emptyConvertToString, DEFAULT_EMPTY_CONVERT_TO_STRING).booleanValue(),
                    defaultIfNull(holder.formatterRegister, defaultFormatterRegister,
                            emptyFormatterRegister, DEFAULT_EMPTY_FORMATTER_REGISTER)));
        }
        return sheets;
    }

    protected ISheetWriterStrategy getEmptySheetStrategy() {
        return new SheetWriterStrategy(defaultIfNull(emptySheetName, DEFAULT_EMPTY_SHEET_NAME),
                defaultIfNull(emptySheetWriter, DEFAULT_EMPTY_SHEET_WRITER), defaultIfNull(
                        emptyConvertToString, DEFAULT_EMPTY_CONVERT_TO_STRING).booleanValue(),
                defaultIfNull(emptyFormatterRegister, DEFAULT_EMPTY_FORMATTER_REGISTER));
    }

    public IWorkbookWriter build() {
        return newWorkbookWriter(
                new WorkbookWriterStrategy(getSheetStrategys(), getEmptySheetStrategy(),
                        defaultIfNull(xlsxFlag, DEFAULT_XLSX_FLAG).booleanValue()),
                defaultIfNull(useTemplateFlag, DEFAULT_USE_TEMPLATE_FLAG).booleanValue() ? defaultIfNull(
                        templateLoader, DEFAULT_TEMPLATE_LOADER) : null);
    }

    protected IWorkbookWriter newWorkbookWriter(IWorkbookWriterStrategy strategy,
            TemplateLoader templateLoader) {
        return new WorkbookWriter(strategy, templateLoader);
    }

    protected static class SheetStrategyHolder {

        String sheetName;

        ISheetWriter sheetWriter;

        Boolean convertToString;

        FormatterRegister formatterRegister;
    }
}
