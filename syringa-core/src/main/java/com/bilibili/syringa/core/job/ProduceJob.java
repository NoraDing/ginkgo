/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.job;

/**
 *
 * @author xuezhaoming
 * @version $Id: ProduceJob.java, v 0.1 2019-01-15 2:29 PM Exp $$
 */
public class ProduceJob extends AbstractJob implements Runnable {

    public ProduceJob(String name, long messageCounter) {
        super(name, messageCounter);
    }

    @Override
    public void run() {

    }

    @Override
    public boolean isRunning() {
        return super.isRunning();
    }
}
