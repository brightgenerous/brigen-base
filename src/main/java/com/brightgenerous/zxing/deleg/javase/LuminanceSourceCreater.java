package com.brightgenerous.zxing.deleg.javase;

import com.brightgenerous.zxing.DecodeArguments;
import com.google.zxing.LuminanceSource;

interface LuminanceSourceCreater {

    boolean useful(DecodeArguments args);

    LuminanceSource create(DecodeArguments args);
}
