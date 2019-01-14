/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core;

import com.bilibili.syringa.core.config.SyringaSystemConfig;

/**
 * @author xuezhaoming
 * @version $Id: SyringaContext.java, v 0.1 2019-01-14 6:30 PM Exp $$
 */
public class SyringaContext {

    private static SyringaContext syringaContext = new SyringaContext();

    private SyringaContext() {

    }

    public static SyringaContext getInstance() {
        return syringaContext;
    }

    private SyringaSystemConfig syringaSystemConfig;

    public SyringaSystemConfig getSyringaSystemConfig() {
        return syringaSystemConfig;
    }

    public void setSyringaSystemConfig(SyringaSystemConfig syringaSystemConfig) {
        this.syringaSystemConfig = syringaSystemConfig;
    }
}
