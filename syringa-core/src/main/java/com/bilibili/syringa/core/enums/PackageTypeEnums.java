/**
 * Bilibili.com Inc.
 * Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.enums;

/**
 *
 * @author dingsainan
 * @version $Id: PackageTypeEnums.java, v 0.1 2019-01-14 下午3:07 dingsainan Exp $$
 */
public enum PackageTypeEnums {
    PackageTypeOne(1,ScalePercentEnums.OneO,DataSizeEnums.OneTwoEightK),
    PackageTypeTwo(2,ScalePercentEnums.FourFive,DataSizeEnums.FourM),
    PackageTypeThree(3,ScalePercentEnums.ThreeFive,DataSizeEnums.TwoM);

    private int type;
    private ScalePercentEnums scale;
    private DataSizeEnums dataSize;

    PackageTypeEnums(int type, ScalePercentEnums scale, DataSizeEnums dataSize) {
        this.type = type;
        this.scale = scale;
        this.dataSize = dataSize;
    }

    @Override
    public String toString() {
        return "PackageTypeEnums{" +
                "type=" + type +
                ", scale=" + scale +
                ", dataSize=" + dataSize +
                '}';
    }
}