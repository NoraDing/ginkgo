package com.nora.ginkgo.core;

import com.nora.ginkgo.core.config.GinkgoConfig;

public class GinkgoContext {

    private static GinkgoContext ginkgoContext = new GinkgoContext();
    private GinkgoConfig ginkgoConfig;

    public static GinkgoContext getInstance() {
        return ginkgoContext;
    }

    public GinkgoConfig getGinkgoConfig() {
        return ginkgoConfig;
    }

    public void setGinkgoConfig(GinkgoConfig ginkgoConfig) {
        this.ginkgoConfig = ginkgoConfig;
    }
}
