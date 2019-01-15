/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.client;

import com.bilibili.syringa.core.BaseTest;
import com.bilibili.syringa.core.SyringaContext;
import com.bilibili.syringa.core.config.SyringaSystemConfig;

import org.junit.Test;

/**
 *
 * @author xuezhaoming
 * @version $Id: OptionInitTest.java, v 0.1 2019-01-15 2:12 PM Exp $$
 */
public class OptionInitTest extends BaseTest {

    @Test
    public void initTest() {
        String[] args = new String[] { "" };
        OptionInit optionInit = new OptionInit(args);
        optionInit.startAsync().awaitRunning();

        String s = SyringaContext.getInstance().getSyringaSystemConfig().toString();
        System.out.println(s);
    }
}
