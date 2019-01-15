/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.job;

import com.bilibili.syringa.core.client.OptionInit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

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

        return null;
    }

}
