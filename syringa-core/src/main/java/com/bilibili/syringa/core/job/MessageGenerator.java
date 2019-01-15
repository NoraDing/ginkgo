/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.job;

import com.google.common.util.concurrent.AbstractIdleService;

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
public class MessageGenerator extends AbstractIdleService {

    private static final Logger           LOGGER = LoggerFactory.getLogger(MessageGenerator.class);

    private List<JobMessageConfig>        jobMessageConfigList;
    private Map<JobMessageConfig, byte[]> messages;

    public MessageGenerator(List<JobMessageConfig> jobMessageConfigList) {
        this.jobMessageConfigList = jobMessageConfigList;
    }

    /**
     * 返回构造的消息
     * @return
     */
    public byte[] getMessage() {

        int nextRandom = ThreadLocalRandom.current().nextInt(100);
        LOGGER.info("the nextRandom is {}", nextRandom);
        byte[] locateValue = locate(nextRandom);
        return locateValue;
    }

    private byte[] locate(int nextRandom) {

        for (JobMessageConfig jobMessageConfig : jobMessageConfigList) {
            int startScale = jobMessageConfig.getStartScale();
            int endScale = jobMessageConfig.getEndScale();
            if (nextRandom >= startScale && nextRandom <= endScale) {

                long size = jobMessageConfig.getSize();
                LOGGER.info("the percent is {} and the size is {}", jobMessageConfig.getPercent(),
                    jobMessageConfig.getSize());
                byte[] data = generateData(size);
                messages.put(jobMessageConfig, data);
                return data;
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

    @Override
    protected void startUp() throws Exception {

        int size = jobMessageConfigList.size();

        messages = new HashMap<>(size);
        calDistribution();
    }

    @Override
    protected void shutDown() throws Exception {

    }
}
