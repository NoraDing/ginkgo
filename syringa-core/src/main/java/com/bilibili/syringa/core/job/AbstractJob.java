/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.job;

import java.util.List;

/**
 *
 * @author xuezhaoming
 * @version $Id: AbstractJob.java, v 0.1 2019-01-15 2:30 PM Exp $$
 */
public abstract class AbstractJob implements Job {

    protected String       name;

    protected long         messageCounter;

    protected List<String> topicList;

    protected boolean      isRunning;

    public AbstractJob(String name, long messageCounter, List<String> topicList) {
        this.name = name;
        this.messageCounter = messageCounter;
        this.topicList = topicList;
    }

    @Override
    public String getName() {
        return name;
    }

    public long getMessageCounter() {
        return messageCounter;
    }

    @Override
    public boolean isRunning() {
        return false;
    }

}
