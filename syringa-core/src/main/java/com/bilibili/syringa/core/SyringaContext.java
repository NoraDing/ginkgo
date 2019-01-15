/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core;

import com.bilibili.syringa.core.client.OptionInit;
import com.bilibili.syringa.core.config.SyringaSystemConfig;

/**
 * @author xuezhaoming
 * @version $Id: SyringaContext.java, v 0.1 2019-01-14 6:30 PM Exp $$
 */
public class SyringaContext {

    private static SyringaContext syringaContext = new SyringaContext();

    private OptionInit            optionInit;

    private boolean               isFinish;

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }

    public OptionInit getOptionInit() {
        return optionInit;
    }

    public void setOptionInit(OptionInit optionInit) {
        this.optionInit = optionInit;
    }

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
