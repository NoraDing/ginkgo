/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.job;

/**
 *
 * @author xuezhaoming
 * @version $Id: JobMessageConfig.java, v 0.1 2019-01-15 10:48 AM Exp $$
 */
public class JobMessageConfig {

    private double percent;
    private long   size;

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
