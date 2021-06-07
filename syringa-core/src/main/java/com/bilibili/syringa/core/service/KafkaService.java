package com.bilibili.syringa.core.service;

import com.bilibili.syringa.core.dto.KafkaTopicDTO;
import kafka.zk.AdminZkClient;
import kafka.zk.KafkaZkClient;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.config.ConfigResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;


public class KafkaService {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaService.class);
    private static String RETENTION_MS = "retention.ms";
    private KafkaAdminClient kafkaAdminClient;
    private KafkaZkClient kafkaZkClient;
    private AdminZkClient adminZkClient;


    public KafkaService(String bootstrap) {
        if (kafkaAdminClient == null) {
            kafkaAdminClient = getKafkaAdminClient(bootstrap);
        }
    }

    private static long ONE_DAY_MS = 1000 * 60 * 60 * 24;
    private static long DEFAULT_DAY = 3;

    public void acquireKafkaTopicConfigInfo(KafkaTopicDTO kafkaTopicDTO) {
        String topic = kafkaTopicDTO.getTopic();
        try {
            //根据获取的topic的名字获取对应的分区数
            TopicDescription topicDescription = kafkaAdminClient.describeTopics(Collections.singleton(topic)).all().get().getOrDefault(topic, null);
            ConfigResource configResource = new ConfigResource(ConfigResource.Type.TOPIC, topic);
            DescribeConfigsResult describeConfigsResult = kafkaAdminClient.describeConfigs(Collections.singleton(configResource));
            if (describeConfigsResult == null || describeConfigsResult.all() == null || describeConfigsResult.all().get() == null) {
                LOGGER.warn("no any config for this topic:{}", topic);
                return;
            }
            AtomicLong retentionMs = new AtomicLong(0l);
            Map<ConfigResource, Config> configResourceConfigMap = describeConfigsResult.all().get();

            configResourceConfigMap.entrySet().stream().forEach(configMap -> {
                ConfigResource key = configMap.getKey();
                String name = key.name();
                Config value = configMap.getValue();
                Collection<ConfigEntry> entries = value.entries();
                entries.stream().forEach(configEntry -> {
                    String configName = configEntry.name();
                    String configValue = configEntry.value();
                    if (RETENTION_MS.equals(configName) && name.equals(topic)) {
                        retentionMs.set(Long.parseLong(configValue));
                    }
                });
            });

            long retentionDay = retentionMs.get() == 0 ? DEFAULT_DAY : retentionMs.get() / ONE_DAY_MS;
            kafkaTopicDTO.setPartition(topicDescription.partitions().size());
            kafkaTopicDTO.setRetentionDay((int) retentionDay);
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.error("get kafka topic detail error", e);
        }
    }


    private KafkaAdminClient getKafkaAdminClient(String bootstrap) {

        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);
        return (KafkaAdminClient) KafkaAdminClient.create(props);

    }
}
