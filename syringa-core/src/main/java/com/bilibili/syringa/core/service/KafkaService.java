package com.bilibili.syringa.core.service;

import com.bilibili.syringa.core.dto.KafkaTopicDTO;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.config.ConfigResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;

public class KafkaService {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaService.class);
    private static String RETENTION_MS = "retention.ms";
    private static String INTERNAL_TOPIC_PREFIX = "__";
    private static long ONE_DAY_MS = 1000 * 60 * 60 * 24;
    private static long DEFAULT_DAY = 3;

    private KafkaAdminClient kafkaAdminClient;

    public KafkaService(String bootstrap) {
        if (kafkaAdminClient == null) {
            kafkaAdminClient = getKafkaAdminClient(bootstrap);
        }
    }


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

    /**
     * Lancer-Log-HighLevel
     * Lancer-Log-HighLevel-test
     * Lancer-Log-RealTime-HighLevel。（highc有流量）
     * Lancer-Log-RealTime-HighLevel-test
     * Lancer-Log-RealTime-new
     * LancerCache-Log-UnRealTime
     * <p>
     * rtd_flow_app/ datacenter_polaris  玄天
     * datacenter_fawkes***liko
     *
     * @param cluster
     * @param existsTopics
     * @return
     */
    public List<KafkaTopicDTO> loadAllTopics(String cluster, List<String> existsTopics) {
        List<KafkaTopicDTO> kafkaTopicDTOS = new ArrayList<>();
        ListTopicsResult listTopicsResult = kafkaAdminClient.listTopics();
        try {
            Set<String> topics = listTopicsResult.names().get();
            topics.stream().forEach(topic -> {
                if (!topic.startsWith(INTERNAL_TOPIC_PREFIX) && topic.startsWith("datacenter_polaris") && !existsTopics.contains(topic)) {
                    KafkaTopicDTO kafkaTopicDTO = new KafkaTopicDTO();
                    kafkaTopicDTO.setCluster(cluster);
                    kafkaTopicDTO.setTopic(topic);
                    kafkaTopicDTO.setUsername("dongziping");
                    kafkaTopicDTO.setDepartmentName("数据平台部");
                    kafkaTopicDTO.setType("saber_write_kafka");
                    kafkaTopicDTOS.add(kafkaTopicDTO);
                }
            });
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.error("load kafka info error", e);
        }
        return kafkaTopicDTOS;
    }

    private KafkaAdminClient getKafkaAdminClient(String bootstrap) {

        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap);
        return (KafkaAdminClient) KafkaAdminClient.create(props);

    }
}
