package com.nora.ginkgo.core.dto;

public class KafkaClusterAddress {
    private String cluster;
    private String bootstrapServer;
    private String zkAddress;

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public String getBootstrapServer() {
        return bootstrapServer;
    }

    public void setBootstrapServer(String bootstrapServer) {
        this.bootstrapServer = bootstrapServer;
    }

    public String getZkAddress() {
        return zkAddress;
    }

    public void setZkAddress(String zkAddress) {
        this.zkAddress = zkAddress;
    }

    @Override
    public String toString() {
        return "KafkaClusterAddress{" +
                "cluster='" + cluster + '\'' +
                ", bootstrapServer='" + bootstrapServer + '\'' +
                ", zkAddress='" + zkAddress + '\'' +
                '}';
    }
}
