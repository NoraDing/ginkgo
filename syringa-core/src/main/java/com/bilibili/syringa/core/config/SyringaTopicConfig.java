/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.config;

import com.bilibili.syringa.core.SyringaConfig;

/**
 * @author dingsainan
 * @version $Id: SyringaTopicConfig.java, v 0.1 2019-01-14 下午5:32 dingsainan Exp $$
 */
public class SyringaTopicConfig implements SyringaConfig {

    private int scaleType;   //百分比类型
    private int dataSizeType; //大小类型
    private int sharedCount; //

    public SyringaTopicConfig(int scaleType, int dataSizeType) {
        this.scaleType = scaleType;
        this.dataSizeType = dataSizeType;
    }

    public int getScaleType() {
        return scaleType;
    }

    public void setScaleType(int scaleType) {
        this.scaleType = scaleType;
    }

    public int getDataSizeType() {
        return dataSizeType;
    }

    public void setDataSizeType(int dataSizeType) {
        this.dataSizeType = dataSizeType;
    }

    public int getSharedCount() {
        return sharedCount;
    }

    public void setSharedCount(int sharedCount) {
        this.sharedCount = sharedCount;
    }

    @Override
    public String toString() {
        return "SyringaTopicConfig{" + "scaleType=" + scaleType + ", dataSizeType=" + dataSizeType
               + ", sharedCount=" + sharedCount + '}';
    }
}