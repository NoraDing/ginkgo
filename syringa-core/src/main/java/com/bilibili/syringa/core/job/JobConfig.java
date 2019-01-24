/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.job;

import java.util.List;

import com.bilibili.syringa.core.config.SyringaTopicConfig;

/**
 *
 * @author xuezhaoming
 * @version $Id: JobConfig.java, v 0.1 2019-01-14 6:43 PM Exp $$
 */
public class JobConfig {
    private int                      appType;
    private int                      appNumber;
    private int                      topicNumber;
    private int                      nMessage;
    private List<SyringaTopicConfig> syringaTopicConfigs;

    public int getAppType() {
        return appType;
    }

    public void setAppType(int appType) {
        this.appType = appType;
    }

    public int getAppNumber() {
        return appNumber;
    }

    public void setAppNumber(int appNumber) {
        this.appNumber = appNumber;
    }

    public int getTopicNumber() {
        return topicNumber;
    }

    public void setTopicNumber(int topicNumber) {
        this.topicNumber = topicNumber;
    }

    public int getnMessage() {
        return nMessage;
    }

    public void setnMessage(int nMessage) {
        this.nMessage = nMessage;
    }

    public List<SyringaTopicConfig> getSyringaTopicConfigs() {
        return syringaTopicConfigs;
    }

    public void setSyringaTopicConfigs(List<SyringaTopicConfig> syringaTopicConfigs) {
        this.syringaTopicConfigs = syringaTopicConfigs;
    }
}
