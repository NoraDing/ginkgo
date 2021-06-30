package com.nora.ginkgo.core.enums;

public enum OperateType {
    REGISTER(1, "register"),
    DELETE(2, "delete");
    private int type;
    private String name;

    OperateType(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
