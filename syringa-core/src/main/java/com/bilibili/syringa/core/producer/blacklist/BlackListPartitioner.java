/**
 * Bilibili.com Inc.
 * Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.producer.blacklist;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zhouhuidong
 * @version $Id: BlackListPartitioner.java, v 0.1 2019-06-10 11:05 AM zhouhuidong Exp $$
 */
public class BlackListPartitioner implements Partitioner {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(BlackListPartitioner.class);


    private final ConcurrentMap<String, AtomicInteger> topicCounterMap = new ConcurrentHashMap<>();

    private BlackListContainer blackListContainer ;

    @Override
    public void configure(Map<String, ?> configs) {
        blackListContainer = BlackListContainerZookeeper.getInstance();
    }

    /**
     * Compute the partition for the given record.
     *
     * @param topic      The topic name
     * @param key        The key to partition on (or null if no key)
     * @param keyBytes   serialized key to partition on (or null if no key)
     * @param value      The value to partition on or null
     * @param valueBytes serialized value to partition on or null
     * @param cluster    The current cluster metadata
     */
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes,
                         Cluster cluster) {
        List<PartitionInfo> partitions = cluster.partitionsForTopic(topic);
        int numPartitions = partitions.size();
        if (keyBytes == null) {
            int nextValue = nextValue(topic);
            List<PartitionInfo> availablePartitions = cluster.availablePartitionsForTopic(topic);
            availablePartitions = getAvailablePartitionsWithoutBlackList(availablePartitions,
                    topic);
            if (availablePartitions.size() > 0) {
                int part = Utils.toPositive(nextValue) % availablePartitions.size();
                return availablePartitions.get(part).partition();
            } else {
                // no partitions are available, give a non-available partition
                return Utils.toPositive(nextValue) % numPartitions;
            }
        } else {
            // hash the keyBytes to choose a partition
            return Utils.toPositive(Utils.murmur2(keyBytes)) % numPartitions;
        }
    }

    private int nextValue(String topic) {
        AtomicInteger counter = topicCounterMap.get(topic);
        if (null == counter) {
            counter = new AtomicInteger(ThreadLocalRandom.current().nextInt());
            AtomicInteger currentCounter = topicCounterMap.putIfAbsent(topic, counter);
            if (currentCounter != null) {
                counter = currentCounter;
            }
        }
        return counter.getAndIncrement();
    }

    /**
     * 剔除tp黑名单后的可用partition列表
     *
     * @param availablePartitions
     * @param topic
     * @return
     */
    private List<PartitionInfo> getAvailablePartitionsWithoutBlackList(List<PartitionInfo> availablePartitions,
                                                                       String topic) {
        if (blackListContainer == null) {
            return availablePartitions;
        }
        BlackList blackList = blackListContainer.getBlackList();
        if (blackList == null || blackList.getBlackList() == null
                || !blackList.getBlackList().containsKey(topic)) {
            return availablePartitions;
        }

        List<PartitionInfo> newAvailablePartitions = new ArrayList<>();
        Map<Integer, String> partitions = blackList.getBlackList().get(topic);
        for (PartitionInfo p : availablePartitions) {
            if (!p.leader().host().equals(partitions.get(p.partition()))) {
                newAvailablePartitions.add(p);
            }
        }
        return newAvailablePartitions;
    }

    @Override
    public void close() {
    }
}