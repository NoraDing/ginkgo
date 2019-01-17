/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.job;

import java.util.List;
import java.util.concurrent.Callable;

import com.bilibili.syringa.core.statistics.RunResult;

/**
 *
 * @author xuezhaoming
 * @version $Id: Job.java, v 0.1 2019-01-15 2:29 PM Exp $$
 */
public interface Job extends Callable<List<RunResult>> {

    /**
     * 作业名称
     * @return
     */
    String getName();

}
