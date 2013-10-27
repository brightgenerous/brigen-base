package com.brightgenerous.zxing;

import java.util.Collection;

public interface ZxingBuilder<B, E, R, S, D, C, DS> {

    ZxingBuilder<B, E, R, S, D, C, DS> clear();

    ZxingDecoder buildDecoder();

    ZxingDecoder buildDecoder(String characterSet);

    ZxingEncoder buildEncoder();

    ZxingEncoder buildEncoder(String contents);

    ZxingEncoder buildEncoder(String contents, String characterSet);

    String contents();

    ZxingBuilder<B, E, R, S, D, C, DS> contents(String contents);

    B barcodeFormat();

    ZxingBuilder<B, E, R, S, D, C, DS> barcodeFormat(B barcodeFormat);

    Integer width();

    ZxingBuilder<B, E, R, S, D, C, DS> width(int width);

    ZxingBuilder<B, E, R, S, D, C, DS> width(Integer width);

    Integer height();

    ZxingBuilder<B, E, R, S, D, C, DS> height(int height);

    ZxingBuilder<B, E, R, S, D, C, DS> height(Integer height);

    ZxingBuilder<B, E, R, S, D, C, DS> size(int width, int height);

    ZxingBuilder<B, E, R, S, D, C, DS> size(Integer width, Integer height);

    String format();

    ZxingBuilder<B, E, R, S, D, C, DS> format(String format);

    ZxingBuilder<B, E, R, S, D, C, DS> format(String format, int quality);

    ZxingBuilder<B, E, R, S, D, C, DS> format(String format, Integer quality);

    Integer quality();

    ZxingBuilder<B, E, R, S, D, C, DS> quality(int quality);

    ZxingBuilder<B, E, R, S, D, C, DS> quality(Integer quality);

    // common hints

    String characterSet();

    ZxingBuilder<B, E, R, S, D, C, DS> characterSet(String characterSet);

    // decode hints
    Object other();

    ZxingBuilder<B, E, R, S, D, C, DS> other(Object other);

    Boolean pureBarcode();

    ZxingBuilder<B, E, R, S, D, C, DS> pureBarcode(boolean pureBarcode);

    ZxingBuilder<B, E, R, S, D, C, DS> pureBarcode(Boolean pureBarcode);

    Collection<B> possibleFormats();

    ZxingBuilder<B, E, R, S, D, C, DS> possibleFormats(B... possibleFormats);

    ZxingBuilder<B, E, R, S, D, C, DS> possibleFormats(Collection<B> possibleFormats);

    Boolean tryHarder();

    ZxingBuilder<B, E, R, S, D, C, DS> tryHarder(boolean tryHarder);

    ZxingBuilder<B, E, R, S, D, C, DS> tryHarder(Boolean tryHarder);

    int[] allowedLength();

    ZxingBuilder<B, E, R, S, D, C, DS> allowedLength(int[] allowedLength);

    Boolean assumeCode39CheckDigit();

    ZxingBuilder<B, E, R, S, D, C, DS> assumeCode39CheckDigit(boolean assumeCode39CheckDigit);

    ZxingBuilder<B, E, R, S, D, C, DS> assumeCode39CheckDigit(Boolean assumeCode39CheckDigit);

    Boolean assumeGs1();

    ZxingBuilder<B, E, R, S, D, C, DS> assumeGs1(boolean assumeGs1);

    ZxingBuilder<B, E, R, S, D, C, DS> assumeGs1(Boolean assumeGs1);

    R needResultPointCallback();

    ZxingBuilder<B, E, R, S, D, C, DS> needResultPointCallback(R needResultPointCallback);

    // encode hints

    E errorCorrection();

    ZxingBuilder<B, E, R, S, D, C, DS> errorCorrection(E errorCorrection);

    S dataMatrixShape();

    ZxingBuilder<B, E, R, S, D, C, DS> dataMatrixShape(S dataMatrixShape);

    D minSize();

    ZxingBuilder<B, E, R, S, D, C, DS> minSize(D minSize);

    D maxSize();

    ZxingBuilder<B, E, R, S, D, C, DS> maxSize(D maxSize);

    Integer margin();

    ZxingBuilder<B, E, R, S, D, C, DS> margin(Integer margin);

    Boolean pdf417Compact();

    ZxingBuilder<B, E, R, S, D, C, DS> pdf417Compact(boolean pdf417Compact);

    ZxingBuilder<B, E, R, S, D, C, DS> pdf417Compact(Boolean pdf417Compact);

    C pdf417Compaction();

    ZxingBuilder<B, E, R, S, D, C, DS> pdf417Compaction(C pdf417Compaction);

    DS pdf417Dimensions();

    ZxingBuilder<B, E, R, S, D, C, DS> pdf417Dimensions(DS pdf417Dimensions);
}
