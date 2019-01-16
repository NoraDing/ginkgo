/**
 * Bilibili.com Inc.
 * Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.properties;

/**
 *
 * @author dingsainan
 * @version $Id: Properties.java, v 0.1 2019-01-16 上午10:29 dingsainan Exp $$
 */
public class Properties {
    private String name;
    private String value;

    public Properties(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Properties{" + "name='" + name + '\'' + ", value='" + value + '\'' + '}';
    }
}