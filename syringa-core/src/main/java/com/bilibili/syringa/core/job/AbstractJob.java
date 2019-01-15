/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.job;

/**
 *
 * @author xuezhaoming
 * @version $Id: AbstractJob.java, v 0.1 2019-01-15 2:30 PM Exp $$
 */
public abstract class AbstractJob implements Job {

    protected String  name;

    protected long    messageCounter;

    protected boolean isRunning;

    public AbstractJob(String name, long messageCounter) {
        this.name = name;
        this.messageCounter = messageCounter;
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
