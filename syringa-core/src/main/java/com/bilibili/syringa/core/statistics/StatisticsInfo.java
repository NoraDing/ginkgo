/**
 * Bilibili.com Inc.
 * Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.statistics;

import java.time.LocalDateTime;

/**
 *
 * @author dingsainan
 * @version $Id: StatisticsInfo.java, v 0.1 2019-01-16 下午4:06 dingsainan Exp $$
 */
public class StatisticsInfo {

    private double        message;
    private double        totalSize;

    private LocalDateTime startDate;
    private LocalDateTime finishDate;
    private double        mbSecs;
    private double        nMessageSecs;

    public double getMessage() {
        return message;
    }

    public void setMessage(double message) {
        this.message = message;
    }

    public double getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(double totalSize) {
        this.totalSize = totalSize;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDateTime finishDate) {
        this.finishDate = finishDate;
    }

    public double getMbSecs() {
        return mbSecs;
    }

    public void setMbSecs(double mbSecs) {
        this.mbSecs = mbSecs;
    }

    public double getnMessageSecs() {
        return nMessageSecs;
    }

    public void setnMessageSecs(double nMessageSecs) {
        this.nMessageSecs = nMessageSecs;
    }

    @Override
    public String toString() {
        return "StatisticsInfo{" + "message=" + message + ", totalSize=" + totalSize
               + ", startDate=" + startDate + ", finishDate=" + finishDate + ", mbSecs=" + mbSecs
               + ", nMessageSecs=" + nMessageSecs + '}';
    }
}