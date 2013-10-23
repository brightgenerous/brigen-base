package com.brightgenerous.poi.reader;

import java.util.Iterator;

public interface IWorkbookReaderStrategy {

    Iterator<ISheetReaderStrategy> getSheetStrategys();
}
