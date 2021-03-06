package com.brightgenerous.zxing.deleg.android;

import com.brightgenerous.zxing.DecodeArguments;
import com.google.zxing.LuminanceSource;

interface LuminanceSourceCreater {

    boolean useful(DecodeArguments args);

    LuminanceSource create(DecodeArguments args);
}
