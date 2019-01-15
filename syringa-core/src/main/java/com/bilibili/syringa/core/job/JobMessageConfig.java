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
    private int    startScale;
    private int    endScale;

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

    public int getStartScale() {
        return startScale;
    }

    public void setStartScale(int startScale) {
        this.startScale = startScale;
    }

    public int getEndScale() {
        return endScale;
    }

    public void setEndScale(int endScale) {
        this.endScale = endScale;
    }

    @Override
    public String toString() {
        return "JobMessageConfig{" + "percent=" + percent + ", size=" + size + ", startScale="
               + startScale + ", endScale=" + endScale + '}';
    }
}
