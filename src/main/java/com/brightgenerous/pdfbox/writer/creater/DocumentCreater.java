package com.brightgenerous.pdfbox.writer.creater;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import com.brightgenerous.pdfbox.writer.IDataConverter;
import com.brightgenerous.pdfbox.writer.IDocumentCreater;
import com.brightgenerous.pdfbox.writer.IPageAppender;
import com.brightgenerous.pdfbox.writer.IResourceLoader;

public class DocumentCreater<T> implements IDocumentCreater<T> {

    private final IResourceLoader<T> resource;

    private final IDataConverter<T> converter;

    private final IPageAppender appender;

    private final boolean flatten;

    public DocumentCreater(IResourceLoader<T> resource, IDataConverter<T> converter,
            IPageAppender appender, boolean flatten) {
        if (resource == null) {
            throw new IllegalArgumentException("The resource must not be null.");
        }

        this.resource = resource;
        this.converter = converter;
        this.appender = appender;
        this.flatten = flatten;
    }

    @Override
    public PDDocument create(int start, T data) throws IOException {
        PDDocument ret = resource.load(data);
        if (appender != null) {
            ret = appender.append(start, ret);
        }
        if ((converter != null) && (data != null)) {
            Map<String, String> map = converter.convert(data);
            if (((map != null) && !map.isEmpty()) || flatten) {
                PDDocumentCatalog catalog = ret.getDocumentCatalog();
                PDAcroForm acroForm = catalog.getAcroForm();
                List<PDField> fields = acroForm.getFields();
                for (PDField field : fields) {
                    if (map != null) {
                        boolean find = false;
                        String key = field.getFullyQualifiedName();
                        if (map.containsKey(key)) {
                            find = true;
                        } else {
                            key = field.getPartialName();
                            if (map.containsKey(key)) {
                                find = true;
                            } else {
                                key = field.getAlternateFieldName();
                                if (map.containsKey(key)) {
                                    find = true;
                                }
                            }
                        }
                        if (find) {
                            field.setValue(map.get(key));
                        }
                    }
                    if (flatten) {
                        field.setReadonly(true);
                    }
                }
            }
        }
        return ret;
    }
}
