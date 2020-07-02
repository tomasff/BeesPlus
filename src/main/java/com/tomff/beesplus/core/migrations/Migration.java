package com.tomff.beesplus.core.migrations;

import java.util.ArrayList;
import java.util.List;

public class Migration {
    private List<Operation> operations;

    public Migration() {
        this.operations = new ArrayList<>();
    }

    public Migration add(Operation operation) {
        operations.add(operation);
        return this;
    }

    public List<Operation> getOperations() {
        return operations;
    }
}
