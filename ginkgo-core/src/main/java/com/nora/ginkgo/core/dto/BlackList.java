package com.nora.ginkgo.core.dto;

import java.util.HashMap;
import java.util.Map;

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

        public String getTopic() {
            return topic;
        }

        public Integer getPartition() {
            return partition;
        }

        @Override
        public String toString() {
            return "BlackListZKItem{" +
                    "topic='" + topic + '\'' +
                    ", partition=" + partition +
                    '}';
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
