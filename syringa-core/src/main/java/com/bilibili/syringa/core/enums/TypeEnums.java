/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 *
 * @author dingsainan
 * @version $Id: TypeEnums.java, v 0.1 2019-01-14 下午2:16 dingsainan Exp $$
 */
public enum TypeEnums {
    PRODUCER(1, "生产者"),

    CONSUMER(2, "消费者");

    private int    type;
    private String field;

    TypeEnums(int type, String field) {
        this.type = type;
        this.field = field;
    }

    /**
     * 检查是否有效类型
     * @param type  类型编码
     * @return null : 无效编码
     */
    public static TypeEnums isValid(int type) {

        TypeEnums[] values = TypeEnums.values();
        for (TypeEnums value : values) {
            if (value.getType() == type) {
                return value;
            }
        }
        return null;
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