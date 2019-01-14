/**
 * Bilibili.com Inc.
 * Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.enums;

import com.google.common.base.Preconditions;

/**
 *
 * @author dingsainan
 * @version $Id: DataSizeEnums.java, v 0.1 2019-01-14 下午3:10 dingsainan Exp $$
 */
public enum  DataSizeEnums {
    OneTwoEightK(1,128*1024),
    FiveOneSixK(2,516*1024),
    OneM(3,1024*1024),
    OnePointFiveM(4,1536*1024),
    TwoM(5,2048*1024),
    FourM(6,4096*1024),
    FiveM(7,5120*1024);


    private int type;
    private int value;

    DataSizeEnums(int type, int value) {
        this.type = type;
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }


    public static Integer getValueByType(Integer  type) {

        Preconditions.checkArgument(type != null);
        DataSizeEnums[] values = DataSizeEnums.values();
        for (DataSizeEnums value : values) {

            if (type==value.type) {
                return value.value;
            }
        }
        return null;
    }

}