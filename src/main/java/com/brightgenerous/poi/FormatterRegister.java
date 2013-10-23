package com.brightgenerous.poi;

import java.text.DateFormat;
import java.text.NumberFormat;

public interface FormatterRegister {

    boolean isNumberFormat(String str);

    NumberFormat getNumberFormat(String str);

    boolean isDateFormat(String str);

    DateFormat getDateFormat(String str);
}
