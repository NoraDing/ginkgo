/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.statistics;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.eventbus.Subscribe;
import com.google.common.util.concurrent.AbstractIdleService;

/**
 *
 * @author xuezhaoming
 * @version $Id: ResultManager.java, v 0.1 2019-01-15 3:35 PM Exp $$
 */
public class ResultManager extends AbstractIdleService {

    private static final Logger     LOGGER = LoggerFactory.getLogger(ResultManager.class);
    private static final int        SCALE  = 1024;

    private List<Future<RunResult>> futureList;

    public ResultManager(List<Future<RunResult>> futureList) {
        this.futureList = futureList;
    }

    @Override
    protected void startUp() throws Exception {

        LOGGER.info("start.time, end.time,total.data.sent.in.MB, MB.sec, "
                    + "total.data.sent.in.nMsg, nMsg.sec");

        List<StatisticsInfo> statisticsInfos = new ArrayList<>();
        for (Future<RunResult> runResultFuture : futureList) {
            StatisticsInfo statisticsInfo = new StatisticsInfo();
            RunResult runResult = runResultFuture.get();

            boolean success = runResult.isSuccess();
            if (!success) {
                continue;
            }

            long message = runResult.getMessage();
            long sizePer = runResult.getSizePer();
            long totalSize = BigDecimal.valueOf(message).multiply(BigDecimal.valueOf(sizePer))
                .divide(BigDecimal.valueOf(SCALE)).divide(BigDecimal.valueOf(SCALE)).longValue();

            LocalDateTime startDate = runResult.getStartDate();
            LocalDateTime finishDate = runResult.getFinishDate();
            long duration = Duration.between(startDate, finishDate).getSeconds();
            double mbSec = BigDecimal.valueOf(totalSize).divide(BigDecimal.valueOf(duration))
                .doubleValue();
            double nMessageSec = BigDecimal.valueOf(message).divide(BigDecimal.valueOf(duration))
                .doubleValue();
            statisticsInfo.setStartDate(startDate);
            statisticsInfo.setFinishDate(finishDate);
            statisticsInfo.setMessage(message);
            statisticsInfo.setTotalSize(totalSize);
            statisticsInfo.setMbSecs(totalSize);
            statisticsInfo.setMbSecs(mbSec);
            statisticsInfo.setnMessageSecs(nMessageSec);
            statisticsInfos.add(statisticsInfo);
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
