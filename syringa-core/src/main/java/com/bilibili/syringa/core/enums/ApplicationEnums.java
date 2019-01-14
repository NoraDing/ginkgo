/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.enums;

/**
 *
 * @author dingsainan
 * @version $Id: ApplicationEnums.java, v 0.1 2019-01-14 下午2:16 dingsainan Exp $$
 */
public enum ApplicationEnums {
    PRODUCER(1, "生产者"),

    CONSUMER(2, "消费者");

    private int type;
    private String field;

    ApplicationEnums(int type, String field) {
        this.type = type;
        this.field = field;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}