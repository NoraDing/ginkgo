package com.bilibili.syringa.core.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationConfig {

    @Value("${bootstrap.servers}")
    private String bootstrapServer;

    @Value("${cluster}")
    private String cluster;

    @Value("${file.path}")
    private String filePath;

    public String getBootstrapServer() {
        return bootstrapServer;
    }

    public String getCluster() {
        return cluster;
    }

    public String getFilePath() {
        return filePath;
    }
}
