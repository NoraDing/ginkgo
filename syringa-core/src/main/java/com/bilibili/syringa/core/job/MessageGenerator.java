/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.job;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

/**
 *
 * @author xuezhaoming
 * @version $Id: MessageGenerator.java, v 0.1 2019-01-15 2:07 PM Exp $$
 */
public class MessageGenerator {

    private static final Logger           LOGGER = LoggerFactory.getLogger(MessageGenerator.class);

    private List<JobMessageConfig>        jobMessageConfigList;
    private Map<JobMessageConfig, String> messages;

    private AtomicBoolean                 init   = new AtomicBoolean(false);

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

        //
        getMessage();
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

        Preconditions.checkNotNull(CollectionUtils.isEmpty(jobMessageConfigList),
            "jobMessageConfigList can not be nul");
        for (JobMessageConfig jobMessageConfig : jobMessageConfigList) {

            long size = jobMessageConfig.getSize();
            byte[] data = generateProducerData(size);
            messages.put(jobMessageConfig, String.valueOf(data));

        }

        return null;
    }

    private byte[] generateProducerData(long dataSize) {

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
