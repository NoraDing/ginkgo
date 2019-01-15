/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.config;

import com.bilibili.syringa.core.enums.TypeEnums;
import com.bilibili.syringa.core.job.JobMessageConfig;

import java.util.List;

/**
 * @author dingsainan
 * @version $Id: SyringaSystemConfig.java, v 0.1 2019-01-14 上午11:43 dingsainan Exp $$
 */
public class SyringaSystemConfig {

    private TypeEnums              type;
    private int                    branches;
    private long                   messages;
    private int                    concurrency;
    private List<JobMessageConfig> jobMessageConfigList;
    private List<String>           topicList;

    public TypeEnums getType() {
        return type;
    }

    public void setType(TypeEnums type) {
        this.type = type;
    }

    public int getBranches() {
        return branches;
    }

    public void setBranches(int branches) {
        this.branches = branches;
    }

    public long getMessages() {
        return messages;
    }

    public void setMessages(long messages) {
        this.messages = messages;
    }

    public int getConcurrency() {
        return concurrency;
    }

    public void setConcurrency(int concurrency) {
        this.concurrency = concurrency;
    }

    public List<JobMessageConfig> getJobMessageConfigList() {
        return jobMessageConfigList;
    }

    public void setJobMessageConfigList(List<JobMessageConfig> jobMessageConfigList) {
        this.jobMessageConfigList = jobMessageConfigList;
    }

    public List<String> getTopicList() {
        return topicList;
    }

    public void setTopicList(List<String> topicList) {
        this.topicList = topicList;
    }

}