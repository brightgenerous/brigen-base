package com.brightgenerous.csv;

class EmptyDataConverter implements IDataConverter<String[]> {

    private static final long serialVersionUID = -7726223957789262342L;

    @Override
    public String[] convertToData(String[] strs) {
        return strs;
    }

    @Override
    public String[] convertToLine(String[] data) {
        return data;
    }

    @Override
    public String[] header() {
        return null;
    }
}
