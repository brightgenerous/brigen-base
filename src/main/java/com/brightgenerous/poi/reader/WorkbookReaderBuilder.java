package com.brightgenerous.poi.reader;

import static com.brightgenerous.commons.ObjectUtils.*;

import java.util.ArrayList;
import java.util.List;

public class WorkbookReaderBuilder {

    private static final ISheetSelector FIRST_SHEET_SELECTOR = new IndexSheetSelector(0);

    private final List<SheetStrategyHolder> sheetStrategyHolders = new ArrayList<>();

    protected WorkbookReaderBuilder() {
    }

    public static WorkbookReaderBuilder create() {
        return new WorkbookReaderBuilder();
    }

    public WorkbookReaderBuilder clear() {
        sheetStrategyHolders.clear();
        return this;
    }

    public int sheets() {
        return sheetStrategyHolders.size();
    }

    public WorkbookReaderBuilder addSheet(ISheetCollector<?>... collectors) {
        return addSheet(null, collectors);
    }

    public WorkbookReaderBuilder addSheet(ISheetReader sheetReader) {
        return addSheet(null, sheetReader);
    }

    public WorkbookReaderBuilder addSheet(ISheetSelector sheetSelector,
            ISheetCollector<?>... collectors) {
        return addSheet(sheetSelector, new CollectSheetReader(collectors));
    }

    public WorkbookReaderBuilder addSheet(ISheetSelector sheetSelector, ISheetReader sheetReader) {
        SheetStrategyHolder sheetStrategyHolder = new SheetStrategyHolder();
        sheetStrategyHolder.sheetSelector = sheetSelector;
        sheetStrategyHolder.sheetReader = sheetReader;
        sheetStrategyHolders.add(sheetStrategyHolder);
        return this;
    }

    protected List<ISheetReaderStrategy> getSheetStrategys() {
        List<ISheetReaderStrategy> sheets = new ArrayList<>();
        for (SheetStrategyHolder holder : sheetStrategyHolders) {
            sheets.add(new SheetReaderStrategy(defaultIfNull(holder.sheetSelector,
                    FIRST_SHEET_SELECTOR), holder.sheetReader));
        }
        return sheets;
    }

    public IWorkbookReader build() {
        return newWorkbookReader(new WorkbookReaderStrategy(getSheetStrategys()));
    }

    protected IWorkbookReader newWorkbookReader(IWorkbookReaderStrategy strategy) {
        return new WorkbookReader(strategy);
    }

    protected static class SheetStrategyHolder {

        ISheetSelector sheetSelector;

        ISheetReader sheetReader;
    }
}
