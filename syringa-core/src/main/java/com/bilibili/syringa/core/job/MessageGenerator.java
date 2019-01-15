/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.job;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author xuezhaoming
 * @version $Id: MessageGenerator.java, v 0.1 2019-01-15 2:07 PM Exp $$
 */
public class MessageGenerator {

    private static final Logger           LOGGER = LoggerFactory.getLogger(MessageGenerator.class);

    private List<JobMessageConfig>        jobMessageConfigList;
    private Map<JobMessageConfig, byte[]> messages;

    public void setJobMessageConfigList(List<JobMessageConfig> jobMessageConfigList) {
        this.jobMessageConfigList = jobMessageConfigList;
    }

    private AtomicBoolean init = new AtomicBoolean(false);

    public static MessageGenerator instance() {
        return new MessageGenerator();
    }

    public MessageGenerator messageConfig(List<JobMessageConfig> jobMessageConfigs) {
        this.jobMessageConfigList = jobMessageConfigs;
        return this;
    }

    public MessageGenerator build() {
        init.compareAndSet(false, true);

        init();
        return this;
    }

    private void init() {

        int size = jobMessageConfigList.size();

        messages = new HashMap<>(size);
        calDistribution();
    }

    /**
     * 返回构造的消息
     * @return
     */
    public String getMessage() {

        if (!init.get()) {
            LOGGER.warn("should init first !");
            return null;
        }
        int nextRandom = ThreadLocalRandom.current().nextInt(10);
        locateAndAdd(nextRandom);
        return null;
    }

    private void locateAndAdd(int nextRandom) {

        for (JobMessageConfig jobMessageConfig : jobMessageConfigList) {
            int startScale = jobMessageConfig.getStartScale();
            int endScale = jobMessageConfig.getEndScale();
            if (nextRandom >= startScale && nextRandom <= endScale) {
                long size = jobMessageConfig.getSize();
                byte[] data = generateData(size);
                messages.put(jobMessageConfig, data);
                return;
            }

        }
    }

    private void calDistribution() {

        int basePercent = 0;
        for (JobMessageConfig jobMessageConfig : jobMessageConfigList) {

            double percent = jobMessageConfig.getPercent();
            int intValue = BigDecimal.valueOf(percent).intValue();
            jobMessageConfig.setStartScale(basePercent);
            jobMessageConfig.setEndScale(intValue);
            basePercent = +intValue;
        }

    }

    private byte[] generateData(long dataSize) {

        byte[] bytes = new byte[Math.toIntExact(dataSize)];
        Random random = new Random();

        LOGGER.info("the start date is {}", LocalDateTime.now());
        for (int i = 0; i < bytes.length; ++i) {
            bytes[i] = (byte) (random.nextInt() + 65);
        }
        LOGGER.info("the end date is {}", LocalDateTime.now());

        return bytes;

    }

}
