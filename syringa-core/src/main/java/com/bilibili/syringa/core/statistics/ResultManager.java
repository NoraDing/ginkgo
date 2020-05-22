/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.statistics;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.bilibili.syringa.core.SyringaContext;
import com.google.common.util.concurrent.AbstractIdleService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xuezhaoming
 * @version $Id: ResultManager.java, v 0.1 2019-01-15 3:35 PM Exp $$
 */
public class ResultManager extends AbstractIdleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResultManager.class);
    private List<StatisticsInfo> statisticsInfos;

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

    }

    @Override
    protected void startUp() throws Exception {

        while (true) {
            TimeUnit.SECONDS.sleep(5);
        }
    }

    @Override
    protected void shutDown() throws Exception {

    }
}
