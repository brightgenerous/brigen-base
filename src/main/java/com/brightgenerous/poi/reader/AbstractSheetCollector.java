package com.brightgenerous.poi.reader;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public abstract class AbstractSheetCollector<T> implements ISheetCollector<T> {

    private volatile Workbook workbook;

    private volatile Sheet sheet;

    private volatile boolean binded;

    @Override
    public void bind(Workbook workbook, Sheet sheet) {
        synchronized (this) {
            if (binded) {
                throw new IllegalStateException("bind already has been called.");
            }
            this.workbook = workbook;
            this.sheet = sheet;
            binded = true;
        }
    }

    protected boolean binded() {
        return binded;
    }

    @Override
    public Workbook getWorkbook() {
        return workbook;
    }

    @Override
    public Sheet getSheet() {
        return sheet;
    }

    @Override
    public T get() {
        if (!binded()) {
            throw new IllegalStateException("bind yet has not been called.");
        }
        return getProcess();
    }

    protected abstract T getProcess();
}
