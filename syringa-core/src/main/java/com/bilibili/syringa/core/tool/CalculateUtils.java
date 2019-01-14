/**
 * Bilibili.com Inc.
 * Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.tool;

import java.math.BigDecimal;

import com.google.common.base.Preconditions;

/**
 *
 * @author dingsainan
 * @version $Id: CalculateUtils.java, v 0.1 2019-01-14 下午6:02 dingsainan Exp $$
 */
public final class CalculateUtils {

    public static int div(int v1, int v2) {
        Preconditions.checkArgument(v2 != 0, "The denominator cannot be zero");
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);

        return b1.divide(b2, BigDecimal.ROUND_HALF_UP).intValue();
    }

    public static int mutiple(int v1, int v2) {

        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        BigDecimal b3 = new BigDecimal(0.01);

        return b1.multiply(b2).multiply(b3).intValue();
    }
}