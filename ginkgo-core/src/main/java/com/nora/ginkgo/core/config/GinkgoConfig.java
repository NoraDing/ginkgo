package com.nora.ginkgo.core.config;

public class GinkgoConfig {
    public static final String KAFKA_BLACKLIST_ZOOKEEPER_ADDRESS = "kafka.blacklist.zookeeper.address";

    private int operateType;
    private String cluster;
    private String host;
    private String topic;
    private Integer partition;

    public int getOperateType() {
        return operateType;
    }

    public void setOperateType(int operateType) {
        this.operateType = operateType;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Integer getPartition() {
        return partition;
    }

    public void setPartition(Integer partition) {
        this.partition = partition;
    }

    @Override
    public String toString() {
        return "GinkgoConfig{" +
                "operateType=" + operateType +
                ", cluster='" + cluster + '\'' +
                ", host='" + host + '\'' +
                ", topic='" + topic + '\'' +
                ", partition=" + partition +
                '}';
    }
}