package com.brightgenerous.poi.reader;

import java.util.Iterator;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.brightgenerous.poi.reader.RowIterableSheetCollector.RowData;

public abstract class IterableSheetCollector<T> extends AbstractSheetCollector<Iterable<T>> {

    private RowIterableSheetCollector<T> deleg = new DelegRowIterableSheetCollector();

    @Override
    public void bind(Workbook workbook, Sheet sheet) {
        super.bind(workbook, sheet);

        deleg.bind(workbook, sheet);
    }

    @Override
    protected Iterable<T> getProcess() {
        return new Iterable<T>() {

            @Override
            public Iterator<T> iterator() {

                final Iterator<RowData<T>> iterator = deleg.get().iterator();

                return new Iterator<T>() {

                    @Override
                    public boolean hasNext() {
                        return iterator.hasNext();
                    }

                    @Override
                    public T next() {
                        return iterator.next().get();
                    }

                    @Override
                    public void remove() {
                        iterator.remove();
                    }
                };
            }
        };
    }

    protected abstract T rowToData(int r, int index, Row row);

    private class DelegRowIterableSheetCollector extends RowIterableSheetCollector<T> {

        @Override
        protected T rowToData(int r, int index, Row row) {
            return IterableSheetCollector.this.rowToData(r, index, row);
        }
    }
}
