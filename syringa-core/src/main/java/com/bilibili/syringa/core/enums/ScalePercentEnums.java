/**
 * Bilibili.com Inc.
 * Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.enums;

import com.google.common.base.Preconditions;

/**
 *
 * @author dingsainan
 * @version $Id: ScalePercentEnums.java, v 0.1 2019-01-14 下午3:15 dingsainan Exp $$
 */
public enum ScalePercentEnums {
    OneO(1, 10),

    OneFive(2, 15),

    TwoO(3, 20),

    TwoFive(4, 25),

    ThreeO(5, 30),

    ThreeFive(6, 35),

    FourO(7, 40),

    FourFive(8, 45),

    FiveO(9, 50),

    SixO(10, 60),

    SevenO(11, 70),

    EightO(12, 80),

    NineO(13, 90),

    OneOO(14, 100);

    private int type;
    private int value;

    ScalePercentEnums(int type, int value) {
        this.type = type;
        this.value = value;
    }

    public static Integer getValueByType(Integer type) {

        Preconditions.checkArgument(type != null);
        ScalePercentEnums[] values = ScalePercentEnums.values();
        for (ScalePercentEnums value : values) {

            if (type == value.type) {
                return value.value;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "ScalePercentEnums{" + "type=" + type + ", value=" + value + '}';
    }
}