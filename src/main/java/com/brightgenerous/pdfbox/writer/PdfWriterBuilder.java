package com.brightgenerous.pdfbox.writer;

import java.util.ArrayList;
import java.util.List;

import com.brightgenerous.pdfbox.writer.creater.DocumentCreater;
import com.brightgenerous.pdfbox.writer.creater.DocumentCreaters;

public class PdfWriterBuilder<T> {

    private static final IPagesAppender DEFAULT_MULTI_APPENDER = new EmptyPagesAppender();

    private static final IPageAppender DEFAULT_APPENDER = new EmptyPageAppender();

    private static final Boolean DEFAULT_FLATTEN = Boolean.FALSE;

    private IPagesAppender pagesAppender;

    private CreaterHolder<T> emptyHolder;

    private final List<CreaterHolder<T>> createrHolders = new ArrayList<>();

    private IResourceLoader<T> defaultResource;

    private IDataConverter<T> defaultConverter;

    private IPageAppender defaultAppender;

    private Boolean defaultFlatten;

    protected PdfWriterBuilder() {
    }

    public static <T> PdfWriterBuilder<T> create() {
        return new PdfWriterBuilder<>();
    }

    public PdfWriterBuilder<T> clear() {
        pagesAppender = null;
        emptyHolder = null;
        createrHolders.clear();
        defaultResource = null;
        defaultConverter = null;
        defaultAppender = null;
        defaultFlatten = null;
        return this;
    }

    // pagesAppender

    public IPagesAppender pagesAppender() {
        return pagesAppender;
    }

    public PdfWriterBuilder<T> pagesAppender(IPagesAppender pagesAppender) {
        this.pagesAppender = pagesAppender;
        return this;
    }

    // emptyCreater

    public PdfWriterBuilder<T> emptyCreater() {
        return emptyCreater(null, null, null, null);
    }

    public PdfWriterBuilder<T> emptyCreater(Boolean flatten) {
        return emptyCreater(null, null, null, flatten);
    }

    public PdfWriterBuilder<T> emptyCreater(IDataConverter<T> converter) {
        return emptyCreater(null, converter, null, null);
    }

    public PdfWriterBuilder<T> emptyCreater(IDataConverter<T> converter, Boolean flatten) {
        return emptyCreater(null, converter, null, flatten);
    }

    public PdfWriterBuilder<T> emptyCreater(IPageAppender appender) {
        return emptyCreater(null, null, appender, null);
    }

    public PdfWriterBuilder<T> emptyCreater(IPageAppender appender, Boolean flatten) {
        return emptyCreater(null, null, appender, flatten);
    }

    public PdfWriterBuilder<T> emptyCreater(IDataConverter<T> converter, IPageAppender appender) {
        return emptyCreater(null, converter, appender, null);
    }

    public PdfWriterBuilder<T> emptyCreater(IResourceLoader<T> resource) {
        return emptyCreater(resource, null, null, null);
    }

    public PdfWriterBuilder<T> emptyCreater(IResourceLoader<T> resource, Boolean flatten) {
        return emptyCreater(resource, null, null, flatten);
    }

    public PdfWriterBuilder<T> emptyCreater(IResourceLoader<T> resource, IDataConverter<T> converter) {
        return emptyCreater(resource, converter, null, null);
    }

    public PdfWriterBuilder<T> emptyCreater(IResourceLoader<T> resource,
            IDataConverter<T> converter, Boolean flatten) {
        return emptyCreater(resource, converter, null, flatten);
    }

    public PdfWriterBuilder<T> emptyCreater(IResourceLoader<T> resource, IPageAppender appender) {
        return emptyCreater(resource, null, appender, null);
    }

    public PdfWriterBuilder<T> emptyCreater(IResourceLoader<T> resource, IPageAppender appender,
            Boolean flatten) {
        return emptyCreater(resource, null, appender, flatten);
    }

    public PdfWriterBuilder<T> emptyCreater(IResourceLoader<T> resource,
            IDataConverter<T> converter, IPageAppender appender) {
        return emptyCreater(resource, converter, appender, null);
    }

    public PdfWriterBuilder<T> emptyCreater(IResourceLoader<T> resource,
            IDataConverter<T> converter, IPageAppender appender, Boolean flatten) {
        CreaterHolder<T> holder = new CreaterHolder<>();
        holder.resource = resource;
        holder.converter = converter;
        holder.appender = appender;
        holder.flatten = flatten;
        emptyHolder = holder;
        return this;
    }

    // addCreater

    public PdfWriterBuilder<T> addCreater() {
        return addCreater(null, null, null, null);
    }

    public PdfWriterBuilder<T> addCreater(Boolean flatten) {
        return addCreater(null, null, null, flatten);
    }

    public PdfWriterBuilder<T> addCreater(IDataConverter<T> converter) {
        return addCreater(null, converter, null, null);
    }

    public PdfWriterBuilder<T> addCreater(IDataConverter<T> converter, Boolean flatten) {
        return addCreater(null, converter, null, flatten);
    }

    public PdfWriterBuilder<T> addCreater(IPageAppender appender) {
        return addCreater(null, null, appender, null);
    }

    public PdfWriterBuilder<T> addCreater(IPageAppender appender, Boolean flatten) {
        return addCreater(null, null, appender, flatten);
    }

    public PdfWriterBuilder<T> addCreater(IDataConverter<T> converter, IPageAppender appender) {
        return addCreater(null, converter, appender, null);
    }

    public PdfWriterBuilder<T> addCreater(IResourceLoader<T> resource) {
        return addCreater(resource, null, null, null);
    }

    public PdfWriterBuilder<T> addCreater(IResourceLoader<T> resource, Boolean flatten) {
        return addCreater(resource, null, null, flatten);
    }

    public PdfWriterBuilder<T> addPdfReaderCreater(IResourceLoader<T> resource,
            IDataConverter<T> converter) {
        return addCreater(resource, converter, null, null);
    }

    public PdfWriterBuilder<T> addCreater(IResourceLoader<T> resource, IDataConverter<T> converter,
            Boolean flatten) {
        return addCreater(resource, converter, null, flatten);
    }

    public PdfWriterBuilder<T> addCreater(IResourceLoader<T> resource, IPageAppender appender) {
        return addCreater(resource, null, appender, null);
    }

    public PdfWriterBuilder<T> addCreater(IResourceLoader<T> resource, IPageAppender appender,
            Boolean flatten) {
        return addCreater(resource, null, appender, flatten);
    }

    public PdfWriterBuilder<T> addCreater(IResourceLoader<T> resource, IDataConverter<T> converter,
            IPageAppender appender) {
        return addCreater(resource, converter, appender, null);
    }

    public PdfWriterBuilder<T> addCreater(IResourceLoader<T> resource, IDataConverter<T> converter,
            IPageAppender appender, Boolean flatten) {
        CreaterHolder<T> holder = new CreaterHolder<>();
        holder.resource = resource;
        holder.converter = converter;
        holder.appender = appender;
        holder.flatten = flatten;
        createrHolders.add(holder);
        return this;
    }

    // defaultResource

    public IResourceLoader<T> defaultResource() {
        return defaultResource;
    }

    public PdfWriterBuilder<T> defaultResource(IResourceLoader<T> defaultResource) {
        this.defaultResource = defaultResource;
        return this;
    }

    // defaultConverter

    public IDataConverter<T> defaultConverter() {
        return defaultConverter;
    }

    public PdfWriterBuilder<T> defaultConverter(IDataConverter<T> defaultConverter) {
        this.defaultConverter = defaultConverter;
        return this;
    }

    // defaultAppender

    public IPageAppender defaultAppender() {
        return defaultAppender;
    }

    public PdfWriterBuilder<T> defaultAppender(IPageAppender defaultAppender) {
        this.defaultAppender = defaultAppender;
        return this;
    }

    // defaultFlatten

    public Boolean defaultFlatten() {
        return defaultFlatten;
    }

    public PdfWriterBuilder<T> defaultFlatten(Boolean defaultFlatten) {
        this.defaultFlatten = defaultFlatten;
        return this;
    }

    public PdfWriterBuilder<T> defaultFlatten(boolean defaultFlatten) {
        return defaultFlatten(defaultFlatten ? Boolean.TRUE : Boolean.FALSE);
    }

    // --

    protected IPdfWriterStrategy<T> getStrategy() {
        return new PdfWriterStrategy<>(getCreaters(), getEmptyCreater());
    }

    protected IDocumentCreaters<T> getCreaters() {
        IPagesAppender appender = pagesAppender;
        if (appender == null) {
            appender = DEFAULT_MULTI_APPENDER;
        }
        List<IDocumentCreater<T>> creaters = new ArrayList<>();
        for (CreaterHolder<T> holder : createrHolders) {
            holder = replaceIfNull(holder);
            creaters.add(new DocumentCreater<>(holder.resource, holder.converter, holder.appender,
                    holder.flatten.booleanValue()));
        }
        return new DocumentCreaters<>(appender, creaters);
    }

    protected IDocumentCreater<T> getEmptyCreater() {
        CreaterHolder<T> holder = emptyHolder;
        if (holder == null) {
            holder = newDefaultEmptyCreaterHolder();
        }
        holder = replaceIfNull(holder);
        return new DocumentCreater<>(holder.resource, holder.converter, holder.appender,
                holder.flatten.booleanValue());
    }

    protected CreaterHolder<T> replaceIfNull(CreaterHolder<T> holder) {
        CreaterHolder<T> ret = new CreaterHolder<>();
        ret.resource = holder.resource;
        if (ret.resource == null) {
            ret.resource = defaultResource;
            if (ret.resource == null) {
                ret.resource = newDefaultResource();
            }
        }
        ret.converter = holder.converter;
        if (ret.converter == null) {
            ret.converter = defaultConverter;
            if (ret.converter == null) {
                ret.converter = newDefaultConverter();
            }
        }
        ret.appender = holder.appender;
        if (ret.appender == null) {
            ret.appender = defaultAppender;
            if (ret.appender == null) {
                ret.appender = DEFAULT_APPENDER;
            }
        }
        ret.flatten = holder.flatten;
        if (ret.flatten == null) {
            ret.flatten = defaultFlatten;
            if (ret.flatten == null) {
                ret.flatten = DEFAULT_FLATTEN;
            }
        }
        return ret;
    }

    public IPdfWriter<T> build() {
        return getPdfWriter(getStrategy());
    }

    protected IPdfWriter<T> getPdfWriter(IPdfWriterStrategy<T> strategy) {
        return new PdfWriter<>(strategy);
    }

    private volatile CreaterHolder<T> newDefaultEmptyCreaterHolder;

    protected CreaterHolder<T> newDefaultEmptyCreaterHolder() {
        if (newDefaultEmptyCreaterHolder == null) {
            synchronized (this) {
                if (newDefaultEmptyCreaterHolder == null) {
                    newDefaultEmptyCreaterHolder = new CreaterHolder<>();
                }
            }
        }
        return newDefaultEmptyCreaterHolder;
    }

    private volatile IResourceLoader<T> newDefaultResource;

    protected IResourceLoader<T> newDefaultResource() {
        if (newDefaultResource == null) {
            synchronized (this) {
                if (newDefaultResource == null) {
                    newDefaultResource = new EmptyResourceLoader<>();
                }
            }
        }
        return newDefaultResource;
    }

    private volatile IDataConverter<T> newDefaultConverter;

    protected IDataConverter<T> newDefaultConverter() {
        if (newDefaultConverter == null) {
            synchronized (this) {
                if (newDefaultConverter == null) {
                    newDefaultConverter = new EmptyDataConverter<>();
                }
            }
        }
        return newDefaultConverter;
    }

    protected static class CreaterHolder<T> {

        IResourceLoader<T> resource;

        IDataConverter<T> converter;

        IPageAppender appender;

        Boolean flatten;
    }
}
