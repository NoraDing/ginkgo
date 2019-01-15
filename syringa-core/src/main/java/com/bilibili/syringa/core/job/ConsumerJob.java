/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.job;

/**
 *
 * @author xuezhaoming
 * @version $Id: ConsumerJob.java, v 0.1 2019-01-15 2:32 PM Exp $$
 */
public class ConsumerJob extends AbstractJob {

    public ConsumerJob(String name, long messageCounter) {
        super(name, messageCounter);
    }
}
