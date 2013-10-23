package com.brightgenerous.poi.reader;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public abstract class LoadableSheetCollector<T> extends AbstractSheetCollector<T> {

    private volatile T obj;

    private volatile boolean loaded;

    @Override
    public T getProcess() {
        if (!loaded) {
            synchronized (this) {
                if (!loaded) {
                    obj = load(getWorkbook(), getSheet());
                    loaded = true;
                }
            }
        }
        return obj;
    }

    protected abstract T load(Workbook workbook, Sheet sheet);
}
