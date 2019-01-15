/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.job;

import com.bilibili.syringa.core.statistics.RunResult;

import java.util.concurrent.Callable;

/**
 *
 * @author xuezhaoming
 * @version $Id: Job.java, v 0.1 2019-01-15 2:29 PM Exp $$
 */
public interface Job extends Callable<RunResult> {

    /**
     * 作业名称
     * @return
     */
    String getName();

    /**
     * 是否运行
     * @return true：运行中  false:未运行
     */
    boolean isRunning();

}
