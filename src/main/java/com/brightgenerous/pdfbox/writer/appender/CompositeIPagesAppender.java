package com.brightgenerous.pdfbox.writer.appender;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;

import com.brightgenerous.pdfbox.writer.IPagesAppender;

class CompositeIPagesAppender implements IPagesAppender {

    private final List<IPagesAppender> delegs;

    public CompositeIPagesAppender(List<IPagesAppender> delegs) {
        this.delegs = (delegs == null) ? new ArrayList<IPagesAppender>() : delegs;
    }

    public CompositeIPagesAppender(IPagesAppender... delegs) {
        this(Arrays.asList(delegs));
    }

    public CompositeIPagesAppender add(IPagesAppender deleg, IPagesAppender... delegs) {
        this.delegs.add(deleg);
        for (IPagesAppender d : delegs) {
            this.delegs.add(d);
        }
        return this;
    }

    public CompositeIPagesAppender clear() {
        delegs.clear();
        return this;
    }

    @Override
    public PDDocument append(int end, int start, PDDocument document) throws IOException {
        PDDocument ret = document;
        for (IPagesAppender deleg : delegs) {
            ret = deleg.append(end, start, ret);
        }
        return ret;
    }
}
