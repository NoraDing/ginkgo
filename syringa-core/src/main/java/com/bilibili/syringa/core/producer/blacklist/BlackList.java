/**
 * Bilibili.com Inc.
 * Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.producer.blacklist;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author zhouhuidong
 * @version $Id: BlackList.java, v 0.1 2019-06-13 4:10 PM zhouhuidong Exp $$
 */
public class BlackList {
    private Map<String, Map<Integer, String>> blackList;

    public BlackList() {
        blackList = new HashMap<>();
    }

    public static class BlackListZKItem {
        public String topic;
        public Integer partition;
        public BlackListZKItem(String topic, Integer partition) {
            this.topic = topic;
            this.partition = partition;
        }
    }

    public void add(BlackList blackList) {
        if (blackList.getBlackList() == null) {
            return;
        }
        for (Map.Entry<String, Map<Integer, String>> tp : blackList.getBlackList().entrySet()) {
            if (!this.blackList.containsKey(tp.getKey())) {
                this.blackList.put(tp.getKey(), tp.getValue());
            } else {
                this.blackList.get(tp.getKey()).putAll(tp.getValue());
            }
        }
    }

    public void add(String topic, int partition, String node) {
        if (!blackList.containsKey(topic)) {
            Map<Integer, String> partitionInfo = new HashMap<>();
            partitionInfo.put(partition, node);
            blackList.put(topic, partitionInfo);
        } else {
            Map<Integer, String> partitionInfo = blackList.get(topic);
            partitionInfo.put(partition, node);
        }
    }

    /**
     * Getter method for property blackList.
     *
     * @return property value of blackList
     */
    public Map<String, Map<Integer, String>> getBlackList() {
        return blackList;
    }

    @Override
    public String toString() {
        return "BlackList{" + "blackList=" + blackList + '}';
    }
}
