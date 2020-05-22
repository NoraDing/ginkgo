/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.job;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bilibili.syringa.core.job.task.ProducerTask;
import com.bilibili.syringa.core.statistics.StatisticsInfo;

/**
 * @author xuezhaoming
 * @version $Id: ProduceJob.java, v 0.1 2019-01-15 2:29 PM Exp $$
 */
public class ProduceJob extends AbstractJob {

    private static final int SCALE = 1024;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProduceJob.class);
    private List<StatisticsInfo> statisticsInfos;
    private List<String> topicList;
    protected long messageCounter;

    public void setStatisticsInfos(List<StatisticsInfo> statisticsInfos) {
        this.statisticsInfos = statisticsInfos;
    }

    public ProduceJob(long messageCounter) {
        this.messageCounter = messageCounter;
        this.topicList = syringaContext.getSyringaSystemConfig().getTopicList();
    }

    @Override
    public void call() {

        for (String topic : topicList) {
            ProducerTask producerTask = new ProducerTask(topic,
                    messageCounter);
            threadPoolExecutor.submit(producerTask);
        }
    }

    public void startUp() throws Exception {

        StatisticsInfo statisticsInfo = new StatisticsInfo();
//        for (Future<RunResult> future : runResults) {
//
//            RunResult runResult = future.get();
//            boolean success = runResult.isSuccess();
//            if (!success) {
//                continue;
//            }
//
//            double message = runResult.getMessage();
//            double totalSize = runResult.getTotalSize();
//
//            double totalSizeMb = BigDecimal.valueOf(totalSize)
//                    .divide(BigDecimal.valueOf(SCALE), 5, BigDecimal.ROUND_HALF_UP)
//                    .divide(BigDecimal.valueOf(SCALE), 5, BigDecimal.ROUND_HALF_UP).doubleValue();
//
//            LocalDateTime startDate = runResult.getStartDate();
//            LocalDateTime finishDate = runResult.getFinishDate();
//            if (startDate == null) {
//                LOGGER.warn("the startDate is null");
//                continue;
//            }
//            if (finishDate == null) {
//                LOGGER.warn("the finishDate is null");
//                continue;
//            }
//            long duration = Duration.between(startDate, finishDate).toMillis();
//            if (duration == 0) {
//                LOGGER.warn("no need catch this record");
//                continue;
//            }
//            double mbSec = BigDecimal.valueOf(totalSizeMb).multiply(BigDecimal.valueOf(1000))
//                    .divide(BigDecimal.valueOf(duration), 5, BigDecimal.ROUND_HALF_UP).doubleValue();
//            double nMessageSec = BigDecimal.valueOf(message).multiply(BigDecimal.valueOf(1000))
//                    .divide(BigDecimal.valueOf(duration), 5, BigDecimal.ROUND_HALF_UP).doubleValue();
//            statisticsInfo.setTopic(runResult.getTopicName());
//            statisticsInfo.setStartDate(startDate);
//            statisticsInfo.setFinishDate(finishDate);
//            statisticsInfo.setMessage(message);
//            statisticsInfo.setTotalSize(totalSizeMb);
//            statisticsInfo.setMbSecs(mbSec);
//            statisticsInfo.setnMessageSecs(nMessageSec);
//            statisticsInfos.add(statisticsInfo);
//        }
    }
}
