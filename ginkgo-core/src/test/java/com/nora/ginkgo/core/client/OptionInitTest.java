/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.nora.ginkgo.core.client;

import com.nora.ginkgo.core.GinkgoContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nora.ginkgo.core.BaseTest;

/**
 *
 * @author xuezhaoming
 * @version $Id: OptionInitTest.java, v 0.1 2019-01-15 2:12 PM Exp $$
 */
public class OptionInitTest extends BaseTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(OptionInitTest.class);

    public void initTest() {

        String[] args = new String[] { "-type", "1", "-message", "1000", "-concurrency", "5",
                                       "-size", "10=4k,20=1k,30=6k,40=7k", "-topics",
                                       "topic1,topic2", "-bootstrap.servers", "localhost:9092",
                                       "-configPath",
                                       "/Users/dingsainan/soft/kafka_2.11-2.1.0/config/producer.properties" };
        OptionInit optionInit = new OptionInit(args);

        String s = GinkgoContext.getInstance().getGinkgoConfig().toString();
        System.out.println(s);
    }

}
