package com.brightgenerous.pdfbox.writer.appender;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;

import com.brightgenerous.pdfbox.writer.IPageAppender;

class CompositeIPageAppender implements IPageAppender {

    private final List<IPageAppender> delegs;

    public CompositeIPageAppender(List<IPageAppender> delegs) {
        this.delegs = (delegs == null) ? new ArrayList<IPageAppender>() : delegs;
    }

    public CompositeIPageAppender(IPageAppender... delegs) {
        this(Arrays.asList(delegs));
    }

    public CompositeIPageAppender add(IPageAppender deleg, IPageAppender... delegs) {
        this.delegs.add(deleg);
        for (IPageAppender d : delegs) {
            this.delegs.add(d);
        }
        return this;
    }

    public CompositeIPageAppender clear() {
        delegs.clear();
        return this;
    }

    @Override
    public PDDocument append(int start, PDDocument document) throws IOException {
        PDDocument ret = document;
        for (IPageAppender deleg : delegs) {
            ret = deleg.append(start, ret);
        }
        return ret;
    }
}
