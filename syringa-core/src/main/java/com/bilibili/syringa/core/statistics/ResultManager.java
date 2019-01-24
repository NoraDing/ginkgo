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

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xuezhaoming
 * @version $Id: ResultManager.java, v 0.1 2019-01-15 3:35 PM Exp $$
 */
public class ResultManager {

    private static final Logger           LOGGER = LoggerFactory.getLogger(ResultManager.class);
    private static final int              SCALE  = 1024;
    private List<StatisticsInfo>          statisticsInfos;
    private List<Future<List<RunResult>>> futureList;

    public ResultManager(List<Future<List<RunResult>>> futureList) {
        this.futureList = futureList;
    }

    public void startUp() throws Exception {

        statisticsInfos = new ArrayList<>();
        for (Future<List<RunResult>> runResultFuture : futureList) {
            StatisticsInfo statisticsInfo = new StatisticsInfo();
            List<RunResult> runResults = runResultFuture.get();

            for (RunResult runResult : runResults) {

                boolean success = runResult.isSuccess();
                if (!success) {
                    continue;
                }

                double message = runResult.getMessage();
                double totalSize = runResult.getTotalSize();

                double totalSizeMb = BigDecimal.valueOf(totalSize)
                    .divide(BigDecimal.valueOf(SCALE), 5, BigDecimal.ROUND_HALF_UP)
                    .divide(BigDecimal.valueOf(SCALE), 5, BigDecimal.ROUND_HALF_UP).doubleValue();

                LocalDateTime startDate = runResult.getStartDate();
                LocalDateTime finishDate = runResult.getFinishDate();
                if (startDate == null) {
                    LOGGER.warn("the startDate is null");
                    continue;

                }

                if (finishDate == null) {
                    LOGGER.warn("the finishDate is null");
                    continue;

                }

                long duration = Duration.between(startDate, finishDate).toMillis();
                if (duration == 0) {
                    LOGGER.warn("no need catch this record");
                    continue;

                }
                double mbSec = BigDecimal.valueOf(totalSizeMb).multiply(BigDecimal.valueOf(1000))
                    .divide(BigDecimal.valueOf(duration), 5, BigDecimal.ROUND_HALF_UP)
                    .doubleValue();
                double nMessageSec = BigDecimal.valueOf(message).multiply(BigDecimal.valueOf(1000))
                    .divide(BigDecimal.valueOf(duration), 5, BigDecimal.ROUND_HALF_UP)
                    .doubleValue();
                statisticsInfo.setTopic(runResult.getTopicName());
                statisticsInfo.setStartDate(startDate);
                statisticsInfo.setFinishDate(finishDate);
                statisticsInfo.setMessage(message);
                statisticsInfo.setTotalSize(totalSizeMb);
                statisticsInfo.setMbSecs(mbSec);
                statisticsInfo.setnMessageSecs(nMessageSec);
                statisticsInfos.add(statisticsInfo);

            }

        }

    }

    public void infoStatistics() {

        if (CollectionUtils.isEmpty(statisticsInfos)) {
            LOGGER.info("no info can be found");
            System.exit(-1);

        }

        StatisticsInfoSummary statisticsInfoSummary = new StatisticsInfoSummary();

        double nMessageMax = statisticsInfos.stream().mapToDouble(StatisticsInfo::getnMessageSecs)
            .max().getAsDouble();

        double nMessageMin = statisticsInfos.stream().mapToDouble(StatisticsInfo::getnMessageSecs)
            .min().getAsDouble();
        double nMessageAverage = statisticsInfos.stream()
            .mapToDouble(StatisticsInfo::getnMessageSecs).average().getAsDouble();

        double mbMax = statisticsInfos.stream().mapToDouble(StatisticsInfo::getMbSecs).max()
            .getAsDouble();

        double mbMin = statisticsInfos.stream().mapToDouble(StatisticsInfo::getMbSecs).min()
            .getAsDouble();

        double mbAverage = statisticsInfos.stream().mapToDouble(StatisticsInfo::getMbSecs).average()
            .getAsDouble();

        statisticsInfoSummary.setnMessageMax(nMessageMax);
        statisticsInfoSummary.setnMessageMin(nMessageMin);
        statisticsInfoSummary.setnMessageAverage(nMessageAverage);
        statisticsInfoSummary.setMbMax(mbMax);
        statisticsInfoSummary.setMbMin(mbMin);
        statisticsInfoSummary.setMbAverage(mbAverage);

        statisticsInfoSummary.setStatisticsInfos(statisticsInfos);

        LOGGER.info("start.time, end.time,total.data.sent.in.MB, MB.sec, "
                    + "total.data.sent.in.nMsg, nMsg.sec");
        LOGGER.info("the value is {}", String.valueOf(statisticsInfoSummary));

    }

}
