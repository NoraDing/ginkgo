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

    private int scale;       //所占百分比
    private int dataSize;    //数据大小
    private int sharedCount; //占用消息总数的大小


    public SyringaTopicConfig(int scale, int dataSize, int sharedCount) {
        this.scale = scale;
        this.dataSize = dataSize;
        this.sharedCount = sharedCount;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public int getDataSize() {
        return dataSize;
    }

    public void setDataSize(int dataSize) {
        this.dataSize = dataSize;
    }

    public int getSharedCount() {
        return sharedCount;
    }

    public void setSharedCount(int sharedCount) {
        this.sharedCount = sharedCount;
    }

    @Override
    public String toString() {
        return "SyringaTopicConfig{" + "SCALE=" + scale + ", dataSize=" + dataSize
               + ", sharedCount=" + sharedCount + '}';
    }
}