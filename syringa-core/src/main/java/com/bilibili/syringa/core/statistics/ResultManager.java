/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.statistics;

import java.util.List;
import java.util.concurrent.Future;

import com.google.common.eventbus.Subscribe;
import com.google.common.util.concurrent.AbstractIdleService;

/**
 *
 * @author xuezhaoming
 * @version $Id: ResultManager.java, v 0.1 2019-01-15 3:35 PM Exp $$
 */
public class ResultManager extends AbstractIdleService {

    private List<Future<RunResult>> futureList;

    public ResultManager(List<Future<RunResult>> futureList) {
        this.futureList = futureList;
    }

    @Override
    protected void startUp() throws Exception {

        for (Future<RunResult> runResultFuture : futureList) {

            RunResult runResult = runResultFuture.get();

            TaskEvent taskEvent = new TaskEvent();
            listen(taskEvent);
            boolean success = runResult.isSuccess();

        }

        shutDown();

    }

    @Override
    protected void shutDown() throws Exception {

    }

    @Subscribe
    public void listen(TaskEvent event) {

        //todo

    }

}
