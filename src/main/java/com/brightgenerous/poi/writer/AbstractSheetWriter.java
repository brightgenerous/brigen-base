package com.brightgenerous.poi.writer;

public abstract class AbstractSheetWriter implements ISheetWriter {

    private final int index;

    protected AbstractSheetWriter() {
        this(-1);
    }

    protected AbstractSheetWriter(int index) {
        if (index < -1) {
            index = -1;
        }
        this.index = index;
    }

    @Override
    public int getIndex() {
        return index;
    }
}
