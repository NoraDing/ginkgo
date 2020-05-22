/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

import com.bilibili.syringa.core.client.OptionInit;
import com.bilibili.syringa.core.config.SyringaSystemConfig;
import com.bilibili.syringa.core.job.MessageGenerator;
import com.bilibili.syringa.core.statistics.RunResult;
import com.google.common.eventbus.AsyncEventBus;

/**
 * @author xuezhaoming
 * @version $Id: SyringaContext.java, v 0.1 2019-01-14 6:30 PM Exp $$
 */
public class SyringaContext {

    private static SyringaContext syringaContext = new SyringaContext();

    private CountDownLatch countDownLatch;
    private OptionInit optionInit;

    private AsyncEventBus asyncEventBus;

    private MessageGenerator messageGenerator;

    private List<RunResult> runResults;

    private boolean isFinish;


    private SyringaContext() {


    }

    public static SyringaContext getInstance() {
        return syringaContext;
    }

    public List<RunResult> getRunResults() {
        return runResults;
    }

    public void setRunResults(List<RunResult> runResults) {
        this.runResults = runResults;
    }

    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }

    private SyringaSystemConfig syringaSystemConfig;

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }

    public OptionInit getOptionInit() {
        return optionInit;
    }

    public AsyncEventBus getAsyncEventBus() {
        return asyncEventBus;
    }

    public void setAsyncEventBus(AsyncEventBus asyncEventBus) {
        this.asyncEventBus = asyncEventBus;
    }

    public void setOptionInit(OptionInit optionInit) {
        this.optionInit = optionInit;
    }

    public MessageGenerator getMessageGenerator() {
        return messageGenerator;
    }

    public void setMessageGenerator(MessageGenerator messageGenerator) {
        this.messageGenerator = messageGenerator;
    }

    public SyringaSystemConfig getSyringaSystemConfig() {
        return syringaSystemConfig;
    }

    public void setSyringaSystemConfig(SyringaSystemConfig syringaSystemConfig) {
        this.syringaSystemConfig = syringaSystemConfig;
    }
}
