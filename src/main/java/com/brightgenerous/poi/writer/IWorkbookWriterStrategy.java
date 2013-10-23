package com.brightgenerous.poi.writer;

import java.util.Iterator;

public interface IWorkbookWriterStrategy {

    Iterator<ISheetWriterStrategy> getSheetStrategys();

    ISheetWriterStrategy getEmptySheetStrategy();

    boolean getXlsxFlag();
}
