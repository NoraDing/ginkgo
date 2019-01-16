/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.config;

import java.util.List;
import java.util.Properties;

import com.bilibili.syringa.core.enums.TypeEnums;
import com.bilibili.syringa.core.job.JobMessageConfig;

/**
 * @author dingsainan
 * @version $Id: SyringaSystemConfig.java, v 0.1 2019-01-14 上午11:43 dingsainan Exp $$
 */
public class SyringaSystemConfig {

    private TypeEnums              type;
    private long                   messages;
    private int                    concurrency;
    private List<JobMessageConfig> jobMessageConfigList;
    private List<String>           topicList;
    private String                 servers;
    private Properties             properties;

    public TypeEnums getType() {
        return type;
    }

    public void setType(TypeEnums type) {
        this.type = type;
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

    public String getServers() {
        return servers;
    }

    public void setServers(String servers) {
        this.servers = servers;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "SyringaSystemConfig{" + "type=" + type + ", messages=" + messages + ", concurrency="
               + concurrency + ", jobMessageConfigList=" + jobMessageConfigList + ", topicList="
               + topicList + ", servers='" + servers + '\'' + ", properties=" + properties + '}';
    }
}