package com.brightgenerous.poi.reader;

import java.util.Iterator;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.brightgenerous.poi.reader.RowIterableSheetCollector.RowData;

public abstract class RowIterableSheetCollector<T> extends
        AbstractSheetCollector<Iterable<RowData<T>>> {

    @Override
    protected Iterable<RowData<T>> getProcess() {
        final Sheet sheet = getSheet();
        final int rows = sheet.getLastRowNum() + 1;
        return new Iterable<RowData<T>>() {

            @Override
            public Iterator<RowData<T>> iterator() {
                return new Iterator<RowData<T>>() {

                    private int lastRow = -1;

                    private int currentIndex;

                    private T next;

                    @Override
                    public boolean hasNext() {
                        if (next != null) {
                            return true;
                        }
                        for (int i = lastRow + 1; i < rows; i++) {
                            Row row = sheet.getRow(i);
                            if ((next = rowToData(i, currentIndex, row)) != null) {
                                lastRow = i;
                                return true;
                            }
                        }
                        lastRow = rows;
                        return false;
                    }

                    @Override
                    public RowData<T> next() {
                        if (!hasNext()) {
                            throw new IllegalStateException("does not have next.");
                        }
                        RowData<T> ret;
                        {
                            ret = new RowData<>(lastRow, currentIndex, next);
                            currentIndex++;
                            next = null;
                        }
                        return ret;
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
    }

    protected abstract T rowToData(int r, int index, Row row);

    public static class RowData<T> {

        private final int row;

        private final int index;

        private final T object;

        public RowData(int row, int index, T object) {
            this.row = row;
            this.index = index;
            this.object = object;
        }

        public int getRow() {
            return row;
        }

        public int getIndex() {
            return index;
        }

        public T get() {
            return object;
        }
    }
}
