/**
 * Bilibili.com Inc.
 * Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.statistics;

import java.util.List;

/**
 *
 * @author dingsainan
 * @version $Id: StatisticsInfoSummary.java, v 0.1 2019-01-16 下午6:22 dingsainan Exp $$
 */
public class StatisticsInfoSummary {
    private double               nMessageMax;

    private double               nMessageMin;
    private double               nMessageAverage;

    private double               mbMax;

    private double               mbMin;

    private double               mbAverage;

    private List<StatisticsInfo> statisticsInfos;

    public double getnMessageMax() {
        return nMessageMax;
    }

    public void setnMessageMax(double nMessageMax) {
        this.nMessageMax = nMessageMax;
    }

    public double getnMessageMin() {
        return nMessageMin;
    }

    public void setnMessageMin(double nMessageMin) {
        this.nMessageMin = nMessageMin;
    }

    public double getnMessageAverage() {
        return nMessageAverage;
    }

    public void setnMessageAverage(double nMessageAverage) {
        this.nMessageAverage = nMessageAverage;
    }

    public double getMbMax() {
        return mbMax;
    }

    public void setMbMax(double mbMax) {
        this.mbMax = mbMax;
    }

    public double getMbMin() {
        return mbMin;
    }

    public void setMbMin(double mbMin) {
        this.mbMin = mbMin;
    }

    public double getMbAverage() {
        return mbAverage;
    }

    public void setMbAverage(double mbAverage) {
        this.mbAverage = mbAverage;
    }

    public List<StatisticsInfo> getStatisticsInfos() {
        return statisticsInfos;
    }

    public void setStatisticsInfos(List<StatisticsInfo> statisticsInfos) {
        this.statisticsInfos = statisticsInfos;
    }

    @Override
    public String toString() {
        return "StatisticsInfoSummary{" + "nMessageMax=" + nMessageMax + ", nMessageMin="
               + nMessageMin + ", nMessageAverage=" + nMessageAverage + ", mbMax=" + mbMax
               + ", mbMin=" + mbMin + ", mbAverage=" + mbAverage + ", statisticsInfos="
               + statisticsInfos + '}';
    }
}