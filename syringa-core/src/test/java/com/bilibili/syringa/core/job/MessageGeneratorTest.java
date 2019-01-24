/**
 * Bilibili.com Inc.
 * Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.job;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bilibili.syringa.core.BaseTest;

/**
 *
 * @author dingsainan
 * @version $Id: MessageGeneratorTest.java, v 0.1 2019-01-15 下午2:50 dingsainan Exp $$
 */
public class MessageGeneratorTest extends BaseTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageGeneratorTest.class);

    @Test
    public void getMessageTest() {

        List<JobMessageConfig> jobMessageConfigList = new ArrayList<>();
        JobMessageConfig jobMessageConfig = new JobMessageConfig();
        jobMessageConfig.setPercent(10);
        jobMessageConfig.setSize(1024);

        JobMessageConfig jobMessageConfig2 = new JobMessageConfig();
        jobMessageConfig2.setPercent(20);
        jobMessageConfig2.setSize(2048);

        JobMessageConfig jobMessageConfig3 = new JobMessageConfig();
        jobMessageConfig3.setPercent(30);
        jobMessageConfig3.setSize(4096);

        JobMessageConfig jobMessageConfig4 = new JobMessageConfig();
        jobMessageConfig4.setPercent(40);
        jobMessageConfig4.setSize(10240);

        jobMessageConfigList.add(jobMessageConfig);
        jobMessageConfigList.add(jobMessageConfig2);
        jobMessageConfigList.add(jobMessageConfig3);
        jobMessageConfigList.add(jobMessageConfig4);
        MessageGenerator messageGenerator = new MessageGenerator(jobMessageConfigList);
        messageGenerator.startAsync().awaitRunning();
        Map<JobMessageConfig, byte[]> messages = messageGenerator.getMessages();
        Iterator<Map.Entry<JobMessageConfig, byte[]>> iterator = messages.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<JobMessageConfig, byte[]> next = iterator.next();
            LOGGER.info("the next key is {}", next.getKey());
            LOGGER.info("the next value is {}", next.getValue().length);
        }

        byte[] message = messageGenerator.getMessage();
        LOGGER.info("the next value is {}", message);


    }
}