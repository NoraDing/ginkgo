/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.job;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import com.bilibili.syringa.core.util.RandomCharacter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bilibili.syringa.core.SyringaContext;
import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.AbstractIdleService;

/**
 * @author xuezhaoming
 * @version $Id: MessageGenerator.java, v 0.1 2019-01-15 2:07 PM Exp $$
 */
public class MessageGenerator extends AbstractIdleService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(MessageGenerator.class);

    private List<JobMessageConfig> jobMessageConfigList;
    private Map<JobMessageConfig, String> messages;

    private Integer minRecordSize;
    private Integer maxRecordSize;
    private Integer minBatchSize;
    private Integer maxBatchSize;

    public Map<JobMessageConfig, String> getMessages() {
        return messages;
    }

    public MessageGenerator(SyringaContext syringaContext) {
        this.jobMessageConfigList = syringaContext.getSyringaSystemConfig()
                .getJobMessageConfigList();
        this.minRecordSize = syringaContext.getSyringaSystemConfig().getMinRecordSize();
        this.maxRecordSize = syringaContext.getSyringaSystemConfig().getMaxRecordSize();
        this.minBatchSize = syringaContext.getSyringaSystemConfig().getMinBatchSize();
        this.maxBatchSize = syringaContext.getSyringaSystemConfig().getMaxBatchSize();
    }

    /**
     * 返回构造的消息
     *
     * @return
     */
    public String getMessage() {

        int nextRandom = ThreadLocalRandom.current().nextInt(1, 100);
        String locateValue = locate(nextRandom);
        return locateValue;
    }

    private String locate(int nextRandom) {

        Iterator<Map.Entry<JobMessageConfig, String>> iterator = messages.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<JobMessageConfig, String> next = iterator.next();
            JobMessageConfig key = next.getKey();
            String value = next.getValue();
            int startScale = key.getStartScale();
            int endScale = key.getEndScale();
            if (nextRandom >= startScale && nextRandom <= endScale) {
                return value;

            }
        }
        return null;
    }

    private void calDistribution() {

        int basePercent = 1;
        for (JobMessageConfig jobMessageConfig : jobMessageConfigList) {

            double percent = jobMessageConfig.getPercent();
            int intValue = basePercent + BigDecimal.valueOf(percent).intValue();
            int end = basePercent + BigDecimal.valueOf(percent).intValue() - 1;
            jobMessageConfig.setStartScale(basePercent);
            jobMessageConfig.setEndScale(end);
            basePercent = intValue;
        }
        LOGGER.info("the calculate value  is {}", jobMessageConfigList.toString());
    }

    private void generateData() {
        Preconditions.checkNotNull(jobMessageConfigList, "jobMessageConfigList can not be empty");

        for (JobMessageConfig jobMessageConfig : jobMessageConfigList) {
            long dataSize = jobMessageConfig.getSize();
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < dataSize; j++) {
                builder.append(RandomCharacter.getRandomLowerCaseLetter());
            }
            messages.put(jobMessageConfig, builder.toString());
        }

        LOGGER.info("the end date is {}", LocalDateTime.now());

    }

    @Override
    protected void startUp() throws Exception {

        int size = jobMessageConfigList.size();

        messages = new HashMap<>(size);

        //根据百分比进行分类
        calDistribution();

        //缓存数据
        generateData();
    }

    @Override
    protected void shutDown() throws Exception {

    }
}
