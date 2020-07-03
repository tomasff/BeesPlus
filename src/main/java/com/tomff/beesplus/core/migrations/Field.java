package com.tomff.beesplus.core.migrations;

public class Field {
    private final String path;
    private final Object value;

    public Field(String path, Object value) {
        this.path = path;
        this.value = value;
    }

    public String getPath() {
        return path;
    }

    public Object getValue() {
        return value;
    }
}
