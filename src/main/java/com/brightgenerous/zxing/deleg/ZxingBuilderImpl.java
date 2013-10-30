package com.brightgenerous.zxing.deleg;

import static com.brightgenerous.commons.ObjectUtils.*;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.brightgenerous.zxing.DecodeArguments;
import com.brightgenerous.zxing.EncodeArguments;
import com.brightgenerous.zxing.ZxingBuilder;
import com.brightgenerous.zxing.ZxingDecoder;
import com.brightgenerous.zxing.ZxingEncoder;
import com.brightgenerous.zxing.deleg.android.ZxingAndroidUtility;
import com.brightgenerous.zxing.deleg.javase.ZxingJavaseUtility;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Dimension;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.datamatrix.encoder.SymbolShapeHint;
import com.google.zxing.pdf417.encoder.Compaction;
import com.google.zxing.pdf417.encoder.Dimensions;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

@SuppressWarnings("deprecation")
class ZxingBuilderImpl
        implements
        ZxingBuilder<BarcodeFormat, ErrorCorrectionLevel, ResultPointCallback, SymbolShapeHint, Dimension, Compaction, Dimensions> {

    static {
        try {
            Class.forName(BarcodeFormat.class.getName());
            Class.forName(DecodeHintType.class.getName());
            Class.forName(EncodeHintType.class.getName());
            Class.forName(LuminanceSource.class.getName());
            Class.forName(BitMatrix.class.getName());
            Class.forName(ErrorCorrectionLevel.class.getName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static final BarcodeFormat DEFAULT_BARCODE_FORMAT = BarcodeFormat.QR_CODE;

    private static final Integer DEFAULT_WIDTH = Integer.valueOf(120);

    private static final Integer DEFAULT_HEIGHT = Integer.valueOf(120);

    private static final String DEFAULT_FORMAT = "png";

    private static final Integer DEFAULT_QUALITY = Integer.valueOf(100);

    private String contents;

    private BarcodeFormat barcodeFormat;

    private Integer width;

    private Integer height;

    private String format;

    private Integer quality;

    // common hints

    private String characterSet;

    // decode hints

    private Object other;

    private Boolean pureBarcode;

    private Collection<BarcodeFormat> possibleFormats;

    private Boolean tryHarder;

    private int[] allowedLength;

    private Boolean assumeCode39CheckDigit;

    private Boolean assumeGs1;

    private ResultPointCallback needResultPointCallback;

    // encode hints

    private ErrorCorrectionLevel errorCorrection;

    private SymbolShapeHint dataMatrixShape;

    private Dimension minSize;

    private Dimension maxSize;

    private Integer margin;

    private Boolean pdf417Compact;

    private Compaction pdf417Compaction;

    private Dimensions pdf417Dimensions;

    protected ZxingBuilderImpl() {
    }

    public static ZxingBuilderImpl create() {
        return new ZxingBuilderImpl();
    }

    @Override
    public ZxingBuilderImpl clear() {
        contents = null;
        barcodeFormat = null;
        width = null;
        height = null;
        format = null;
        quality = null;

        characterSet = null;

        other = null;
        pureBarcode = null;
        possibleFormats = null;
        tryHarder = null;
        allowedLength = null;
        assumeCode39CheckDigit = null;
        assumeGs1 = null;
        needResultPointCallback = null;

        errorCorrection = null;
        dataMatrixShape = null;
        minSize = null;
        maxSize = null;
        margin = null;
        pdf417Compact = null;
        pdf417Compaction = null;
        pdf417Dimensions = null;

        return this;
    }

    @Override
    public ZxingDecoder buildDecoder() {
        return buildDecoder(null);
    }

    @Override
    public ZxingDecoder buildDecoder(String characterSet) {
        return new ZxingDecoderImpl(getDecodeHints(characterSet));
    }

    @Override
    public ZxingEncoder buildEncoder() {
        return buildEncoder(contents);
    }

    @Override
    public ZxingEncoder buildEncoder(String contents) {
        return buildEncoder(contents, characterSet);
    }

    @Override
    public ZxingEncoder buildEncoder(String contents, String characterSet) {
        return new ZxingEncoderImpl(contents, defaultIfNull(barcodeFormat, DEFAULT_BARCODE_FORMAT),
                defaultIfNull(width, DEFAULT_WIDTH).intValue(), defaultIfNull(height,
                        DEFAULT_HEIGHT).intValue(), defaultIfNull(format, DEFAULT_FORMAT),
                defaultIfNull(quality, DEFAULT_QUALITY).intValue(), getEncodeHints(characterSet));
    }

    @Override
    public String contents() {
        return contents;
    }

    @Override
    public ZxingBuilderImpl contents(String contents) {
        this.contents = contents;
        return this;
    }

    @Override
    public BarcodeFormat barcodeFormat() {
        return barcodeFormat;
    }

    @Override
    public ZxingBuilderImpl barcodeFormat(BarcodeFormat barcodeFormat) {
        this.barcodeFormat = barcodeFormat;
        return this;
    }

    @Override
    public Integer width() {
        return width;
    }

    @Override
    public ZxingBuilderImpl width(int width) {
        return width(Integer.valueOf(width));
    }

    @Override
    public ZxingBuilderImpl width(Integer width) {
        this.width = width;
        return this;
    }

    @Override
    public Integer height() {
        return height;
    }

    @Override
    public ZxingBuilderImpl height(int height) {
        return height(Integer.valueOf(height));
    }

    @Override
    public ZxingBuilderImpl height(Integer height) {
        this.height = height;
        return this;
    }

    @Override
    public ZxingBuilderImpl size(int width, int height) {
        width(width);
        height(height);
        return this;
    }

    @Override
    public ZxingBuilderImpl size(Integer width, Integer height) {
        width(width);
        height(height);
        return this;
    }

    @Override
    public String format() {
        return format;
    }

    @Override
    public ZxingBuilderImpl format(String format) {
        this.format = format;
        return this;
    }

    @Override
    public ZxingBuilderImpl format(String format, int quality) {
        format(format);
        quality(quality);
        return this;
    }

    @Override
    public ZxingBuilderImpl format(String format, Integer quality) {
        format(format);
        quality(quality);
        return this;
    }

    @Override
    public Integer quality() {
        return quality;
    }

    @Override
    public ZxingBuilderImpl quality(int quality) {
        return quality(Integer.valueOf(quality));
    }

    @Override
    public ZxingBuilderImpl quality(Integer quality) {
        this.quality = quality;
        return this;
    }

    // common hints

    @Override
    public String characterSet() {
        return characterSet;
    }

    @Override
    public ZxingBuilderImpl characterSet(String characterSet) {
        this.characterSet = characterSet;
        return this;
    }

    // decode hints

    @Override
    public Object other() {
        return other;
    }

    @Override
    public ZxingBuilderImpl other(Object other) {
        this.other = other;
        return this;
    }

    @Override
    public Boolean pureBarcode() {
        return pureBarcode;
    }

    @Override
    public ZxingBuilderImpl pureBarcode(boolean pureBarcode) {
        return pureBarcode(pureBarcode ? Boolean.TRUE : Boolean.FALSE);
    }

    @Override
    public ZxingBuilderImpl pureBarcode(Boolean pureBarcode) {
        this.pureBarcode = pureBarcode;
        return this;
    }

    @Override
    public Collection<BarcodeFormat> possibleFormats() {
        return possibleFormats;
    }

    @Override
    public ZxingBuilderImpl possibleFormats(BarcodeFormat... possibleFormats) {
        return possibleFormats(Arrays.asList(possibleFormats));
    }

    @Override
    public ZxingBuilderImpl possibleFormats(Collection<BarcodeFormat> possibleFormats) {
        this.possibleFormats = possibleFormats;
        return this;
    }

    @Override
    public Boolean tryHarder() {
        return tryHarder;
    }

    @Override
    public ZxingBuilderImpl tryHarder(boolean tryHarder) {
        return tryHarder(tryHarder ? Boolean.TRUE : Boolean.FALSE);
    }

    @Override
    public ZxingBuilderImpl tryHarder(Boolean tryHarder) {
        this.tryHarder = tryHarder;
        return this;
    }

    @Override
    public int[] allowedLength() {
        return allowedLength;
    }

    @Override
    public ZxingBuilderImpl allowedLength(int[] allowedLength) {
        this.allowedLength = allowedLength;
        return this;
    }

    @Override
    public Boolean assumeCode39CheckDigit() {
        return assumeCode39CheckDigit;
    }

    @Override
    public ZxingBuilderImpl assumeCode39CheckDigit(boolean assumeCode39CheckDigit) {
        return assumeCode39CheckDigit(assumeCode39CheckDigit ? Boolean.TRUE : Boolean.FALSE);
    }

    @Override
    public ZxingBuilderImpl assumeCode39CheckDigit(Boolean assumeCode39CheckDigit) {
        this.assumeCode39CheckDigit = assumeCode39CheckDigit;
        return this;
    }

    @Override
    public Boolean assumeGs1() {
        return assumeGs1;
    }

    @Override
    public ZxingBuilderImpl assumeGs1(boolean assumeGs1) {
        return assumeGs1(assumeGs1 ? Boolean.TRUE : Boolean.FALSE);
    }

    @Override
    public ZxingBuilderImpl assumeGs1(Boolean assumeGs1) {
        this.assumeGs1 = assumeGs1;
        return this;
    }

    @Override
    public ResultPointCallback needResultPointCallback() {
        return needResultPointCallback;
    }

    @Override
    public ZxingBuilderImpl needResultPointCallback(ResultPointCallback needResultPointCallback) {
        this.needResultPointCallback = needResultPointCallback;
        return this;
    }

    // encode hints

    @Override
    public ErrorCorrectionLevel errorCorrection() {
        return errorCorrection;
    }

    @Override
    public ZxingBuilderImpl errorCorrection(ErrorCorrectionLevel errorCorrection) {
        this.errorCorrection = errorCorrection;
        return this;
    }

    @Override
    public SymbolShapeHint dataMatrixShape() {
        return dataMatrixShape;
    }

    @Override
    public ZxingBuilderImpl dataMatrixShape(SymbolShapeHint dataMatrixShape) {
        this.dataMatrixShape = dataMatrixShape;
        return this;
    }

    @Override
    public Dimension minSize() {
        return minSize;
    }

    @Override
    public ZxingBuilderImpl minSize(Dimension minSize) {
        this.minSize = minSize;
        return this;
    }

    @Override
    public Dimension maxSize() {
        return maxSize;
    }

    @Override
    public ZxingBuilderImpl maxSize(Dimension maxSize) {
        this.maxSize = maxSize;
        return this;
    }

    @Override
    public Integer margin() {
        return margin;
    }

    @Override
    public ZxingBuilderImpl margin(Integer margin) {
        this.margin = margin;
        return this;
    }

    @Override
    public Boolean pdf417Compact() {
        return pdf417Compact;
    }

    @Override
    public ZxingBuilderImpl pdf417Compact(boolean pdf417Compact) {
        return pdf417Compact(pdf417Compact ? Boolean.TRUE : Boolean.FALSE);
    }

    @Override
    public ZxingBuilderImpl pdf417Compact(Boolean pdf417Compact) {
        this.pdf417Compact = pdf417Compact;
        return this;
    }

    @Override
    public Compaction pdf417Compaction() {
        return pdf417Compaction;
    }

    @Override
    public ZxingBuilderImpl pdf417Compaction(Compaction pdf417Compaction) {
        this.pdf417Compaction = pdf417Compaction;
        return this;
    }

    @Override
    public Dimensions pdf417Dimensions() {
        return pdf417Dimensions;
    }

    @Override
    public ZxingBuilderImpl pdf417Dimensions(Dimensions pdf417Dimensions) {
        this.pdf417Dimensions = pdf417Dimensions;
        return this;
    }

    // create hints

    protected Map<DecodeHintType, Object> getDecodeHints(String charset) {
        Map<DecodeHintType, Object> ret = new HashMap<>();
        if (charset != null) {
            ret.put(DecodeHintType.CHARACTER_SET, charset);
        } else if (characterSet != null) {
            ret.put(DecodeHintType.CHARACTER_SET, characterSet);
        }
        if (other != null) {
            ret.put(DecodeHintType.OTHER, other);
        }
        if ((pureBarcode != null) && pureBarcode.booleanValue()) {
            ret.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);
        }
        if (possibleFormats != null) {
            ret.put(DecodeHintType.POSSIBLE_FORMATS, possibleFormats);
        }
        if ((tryHarder != null) && tryHarder.booleanValue()) {
            ret.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
        }
        if (allowedLength != null) {
            ret.put(DecodeHintType.ALLOWED_LENGTHS, allowedLength);
        }
        if ((assumeCode39CheckDigit != null) && assumeCode39CheckDigit.booleanValue()) {
            ret.put(DecodeHintType.ASSUME_CODE_39_CHECK_DIGIT, Boolean.TRUE);
        }
        if ((assumeGs1 != null) && assumeGs1.booleanValue()) {
            ret.put(DecodeHintType.ASSUME_GS1, Boolean.TRUE);
        }
        if (needResultPointCallback != null) {
            ret.put(DecodeHintType.NEED_RESULT_POINT_CALLBACK, needResultPointCallback);
        }
        return ret;
    }

    protected Map<EncodeHintType, Object> getEncodeHints(String charset) {
        Map<EncodeHintType, Object> ret = new HashMap<>();
        if (charset != null) {
            ret.put(EncodeHintType.CHARACTER_SET, charset);
        } else if (characterSet != null) {
            ret.put(EncodeHintType.CHARACTER_SET, characterSet);
        }
        if (errorCorrection != null) {
            ret.put(EncodeHintType.ERROR_CORRECTION, errorCorrection);
        }
        if (dataMatrixShape != null) {
            ret.put(EncodeHintType.DATA_MATRIX_SHAPE, dataMatrixShape);
        }
        if (minSize != null) {
            ret.put(EncodeHintType.MIN_SIZE, minSize);
        }
        if (maxSize != null) {
            ret.put(EncodeHintType.MAX_SIZE, maxSize);
        }
        if (margin != null) {
            ret.put(EncodeHintType.MARGIN, margin);
        }
        if (pdf417Compact != null) {
            ret.put(EncodeHintType.PDF417_COMPACT, pdf417Compact);
        }
        if (pdf417Compaction != null) {
            ret.put(EncodeHintType.PDF417_COMPACTION, pdf417Compaction);
        }
        if (pdf417Dimensions != null) {
            ret.put(EncodeHintType.PDF417_DIMENSIONS, pdf417Dimensions);
        }
        return ret;
    }

    static LuminanceSource createLuminanceSource(DecodeArguments args) {
        LuminanceSource ret = null;
        if (ZxingJavaseUtility.USEFUL && ZxingJavaseUtility.useful(args)) {
            ret = ZxingJavaseUtility.createLuminanceSource(args);
        }
        if ((ret == null) && ZxingAndroidUtility.USEFUL && ZxingAndroidUtility.useful(args)) {
            ret = ZxingAndroidUtility.createLuminanceSource(args);
        }
        if (ret != null) {
            return ret;
        }
        throw new IllegalStateException(String.format("not found LuminanceSource for %s",
                (args == null) ? null : args.getClass().getName()));
    }

    static <T> T toImage(BitMatrix bitMatrix, EncodeArguments args) {
        T ret = null;
        if (ZxingJavaseUtility.USEFUL && ZxingJavaseUtility.useful(args)) {
            ret = ZxingJavaseUtility.toImage(bitMatrix);
        }
        if ((ret == null) && ZxingAndroidUtility.USEFUL && ZxingAndroidUtility.useful(args)) {
            ret = ZxingAndroidUtility.toImage(bitMatrix, args);
        }
        if (ret != null) {
            return ret;
        }
        throw new IllegalStateException(String.format("could not create image for %s",
                (bitMatrix == null) ? null : bitMatrix.getClass().getName()));
    }

    static void writeToStream(BitMatrix bitMatrix, String format, int quality,
            OutputStream outputStream, EncodeArguments args) throws IOException {
        boolean write = false;
        if (ZxingJavaseUtility.USEFUL && ZxingJavaseUtility.useful(args)) {
            ZxingJavaseUtility.writeToStream(bitMatrix, format, outputStream);
            write = true;
        }
        if (!write && ZxingAndroidUtility.USEFUL && ZxingAndroidUtility.useful(args)) {
            ZxingAndroidUtility.writeToStream(bitMatrix, format, quality, outputStream, args);
            write = true;
        }
        if (write) {
            return;
        }
        throw new IllegalStateException(String.format("could not write image for %s, %s",
                (bitMatrix == null) ? null : bitMatrix.getClass().getName(), format));
    }
}
