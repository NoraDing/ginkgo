package com.bilibili.syringa.core.dto;

public class KafkaTopicDTO {
    private String cluster;
    private String topic;
    private String type;
    private String username;
    private long userId;
    private String departmentName;
    private int partition;
    private int retentionDay;

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getPartition() {
        return partition;
    }

    public void setPartition(int partition) {
        this.partition = partition;
    }

    public int getRetentionDay() {
        return retentionDay;
    }

    public void setRetentionDay(int retentionDay) {
        this.retentionDay = retentionDay;
    }

    @Override
    public String toString() {
        return "KafkaTopicDTO{" +
                "cluster='" + cluster + '\'' +
                ", topic='" + topic + '\'' +
                ", type='" + type + '\'' +
                ", username='" + username + '\'' +
                ", userId=" + userId +
                ", departmentName='" + departmentName + '\'' +
                ", partition=" + partition +
                ", retentionDay=" + retentionDay +
                '}';
    }
}
