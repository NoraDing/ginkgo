/**
 * Bilibili.com Inc.
 * Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.job;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.bilibili.syringa.core.BaseTest;

/**
 *
 * @author dingsainan
 * @version $Id: MessageGeneratorTest.java, v 0.1 2019-01-15 下午2:50 dingsainan Exp $$
 */
public class MessageGeneratorTest extends BaseTest {

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


    }
}