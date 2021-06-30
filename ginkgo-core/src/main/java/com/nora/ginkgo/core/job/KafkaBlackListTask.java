package com.nora.ginkgo.core.job;


import com.alibaba.fastjson.JSON;
import com.nora.ginkgo.core.config.GinkgoConfig;
import com.nora.ginkgo.core.dto.BlackList;
import com.nora.ginkgo.core.enums.BlackLIstZkAddressEnum;
import com.nora.ginkgo.core.enums.ClusterBootstrapServerEnums;
import com.sun.javafx.binding.StringFormatter;

import kafka.api.LeaderAndIsr;
import kafka.cluster.BrokerEndPoint;
import kafka.utils.ZkUtils;
import org.apache.commons.collections.CollectionUtils;

import org.apache.kafka.common.Node;
import org.apache.kafka.common.protocol.SecurityProtocol;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.Option;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;


public class KafkaBlackListTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaBlackListTask.class);
    private static final String BLACKLIST_ZK_PATH = "/lancer/kafka-blacklist";
    private static final String BLACKLIST_ZK_MAIN_PATH = "/lancer";
    private static final int ZK_SESSION_TIMEOUT = 20000;
    private ZooKeeper zkClient;
    private String cluster;
    private String bootstrapServer;
    private String topic;
    private Integer partition;
    private String host;


    public KafkaBlackListTask(GinkgoConfig ginkgoConfig) {
        this.cluster = ginkgoConfig.getCluster();
        this.bootstrapServer = ClusterBootstrapServerEnums.getBootstrapByClusterName(cluster);
        this.topic = ginkgoConfig.getTopic();
        this.partition = ginkgoConfig.getPartition();
        this.host = ginkgoConfig.getHost();
        try {
            if (zkClient == null) {
                zkClient = getZkClient();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void register() throws Exception {
        //generate info
        LOGGER.info("register blacklist task cluster:{} topic:{} partition:{}，host:{} with bootstrap:{}", cluster, topic, partition, host, bootstrapServer);
        String ip = getIp(topic, partition);
        if (null == ip || !host.equals(ip)) {
            LOGGER.warn("ip not match. cluster:{},topic:{},partition:{},settled-host:{},ip:{} ", cluster, topic, partition, host, ip);
            return;
        }
        LOGGER.info("ip:{}", ip);
        List<BlackList.BlackListZKItem> zkContent = getZkExistContent(cluster, ip);
        if (CollectionUtils.isEmpty(zkContent)) {
            BlackList.BlackListZKItem blackListZKItem = new BlackList.BlackListZKItem(topic, partition);
            zkContent = new ArrayList<>();
            zkContent.add(blackListZKItem);
        } else {
            BlackList.BlackListZKItem blackListZKItem = new BlackList.BlackListZKItem(topic, partition);

            AtomicBoolean exist = new AtomicBoolean(false);
            for (BlackList.BlackListZKItem zkItem : zkContent) {
                if (topic.equals(zkItem.topic) && partition == zkItem.partition) {
                    exist.set(true);
                    break;
                }
            }
            if (!exist.get()) {
                zkContent.add(blackListZKItem);
            }
        }
        updateOrRegisterToZk(cluster, ip, generateTps(zkContent));
    }

    public void delete() throws Exception {
        LOGGER.info("delete blacklist task cluster:{} topic:{} partition:{} with bootstrap:{}", cluster, topic, partition, bootstrapServer);
        String ip = getIp(topic, partition);
        if (null == ip || !host.equals(ip)) {
            LOGGER.warn("ip not match. cluster:{},topic:{},partition:{},settled-host:{},ip:{} ", cluster, topic, partition, host, ip);
            return;
        }
        List<BlackList.BlackListZKItem> zkContent = getZkExistContent(cluster, ip);
        if (zkContent == null) {
            LOGGER.info("no data found in zk ip:{} ,cluster:{},topic:{} partition:{} ", ip, cluster, topic, partition);
            return;
        }
        List<BlackList.BlackListZKItem> collect = zkContent.stream()
                .filter(con -> (con.partition != partition || !topic.equals(con.topic))).collect(Collectors.toList());
        updateOrRegisterToZk(cluster, ip, generateTps(collect));
    }

    private JSONArray generateTps(List<BlackList.BlackListZKItem> zkContent) {

        JSONArray tps = new JSONArray();
        for (BlackList.BlackListZKItem blackListDTO : zkContent) {
            String topic = blackListDTO.getTopic();
            Integer partition = blackListDTO.getPartition();

            //形成新的数据内容
            Map<String, Object> map = new HashMap<>();
            map.put("topic", topic);
            map.put("partition", String.valueOf(partition));
            JSONObject reassignment = new JSONObject(map);
            tps.put(reassignment);
        }
        LOGGER.info("generated tps :{}", tps.toString());
        return tps;
    }

    private List<BlackList.BlackListZKItem> getZkExistContent(String cluster, String ip) throws Exception {
        String clusterBlacklistPath = checkZKParentPath(zkClient, cluster);
        String clusterIpCurrentPath = StringFormatter.format("%s/%s", clusterBlacklistPath, ip)
                .getValue();
        LOGGER.info("the clusterIpCurrentPath is {}", clusterIpCurrentPath);
        Stat exists = zkClient.exists(clusterIpCurrentPath, false);
        if (exists == null) {
            return Collections.EMPTY_LIST;
        }
        List<String> children = zkClient.getChildren(clusterIpCurrentPath, null);
        if (CollectionUtils.isNotEmpty(children)) {
            LOGGER.error("invalid path :{}", clusterIpCurrentPath);
            return null;
        }
        byte[] value;
        try {
            value = zkClient.getData(clusterIpCurrentPath, false, null);
        } catch (KeeperException | InterruptedException e) {
            LOGGER.error("Failed to get data of {}: {}", clusterIpCurrentPath, e);
            return null;
        }
        if (value == null) {
            LOGGER.info("no data in path :{}", clusterIpCurrentPath);
            return Collections.EMPTY_LIST;
        }
        List<BlackList.BlackListZKItem> items;
        try {
            items = JSON.parseArray(new String(value), BlackList.BlackListZKItem.class);
        } catch (Exception e) {
            LOGGER.error("json parse error for value {}: {}", new String(value), e);
            return null;
        }
        LOGGER.info("zk exist data :{}", items);
        return items;
    }


    private String getIp(String topic, Integer partition) {
        ZkUtils zkUtils = getZkUtils(cluster);
        Option<LeaderAndIsr> leaderAndIsrForPartition = zkUtils.getLeaderAndIsrForPartition(topic, partition);
        int leader = leaderAndIsrForPartition.get().leader();
        BrokerEndPoint brokerEndPoint = zkUtils.getBrokerInfo(leader).get().getBrokerEndPoint(SecurityProtocol.PLAINTEXT);
        return brokerEndPoint.host();
    }


//    private String getIp(String topic, Integer partition) {
//        AdminClient adminClient = getAdminClient();
//        try {
//            Map<String, TopicDescription> topicDescriptionMap = adminClient.describeTopics(Collections.singleton(topic)).all().get();
//            TopicDescription topicDescription = topicDescriptionMap.get(topic);
//            List<TopicPartitionInfo> partitions = topicDescription.partitions();
//
//            for (TopicPartitionInfo topicPartitionInfo : partitions) {
//                if (partition != topicPartitionInfo.partition()) {
//                    continue;
//                }
//                Node leader = topicPartitionInfo.leader();
//                return leader.host();
//            }
//        } catch (InterruptedException | ExecutionException e) {
//            LOGGER.error("get ip error", e);
//        }
//        return null;
//    }

    public void updateOrRegisterToZk(String clusterName, String ip, JSONArray tps) {
        try {
            String clusterBlacklistPath = checkZKParentPath(zkClient, clusterName);
            String clusterIpCurrentPath = StringFormatter.format("%s/%s", clusterBlacklistPath, ip)
                    .getValue();
            LOGGER.info("the clusterCurrentPath is {}", clusterIpCurrentPath);

            // 注册新的节点，或者更新老的节点
            Stat currPathExists = zkClient.exists(clusterIpCurrentPath, false);
            if (0 == tps.length() && currPathExists != null) {
                zkClient.delete(clusterIpCurrentPath, currPathExists.getVersion());
                return;
            }
            if (currPathExists != null) {
                Stat stat = zkClient.setData(clusterIpCurrentPath, tps.toString().getBytes(),
                        currPathExists.getVersion());
                LOGGER.info("set the data for result {}", stat.getVersion());
                return;
            }
            zkClient.create(clusterIpCurrentPath, tps.toString().getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.PERSISTENT);
        } catch (Exception e) {
            LOGGER.error("modify the zk path has issue", e);
        }
    }

    //检查主目录是否存在
    public String checkZKParentPath(ZooKeeper zooKeeper, String clusterName) throws Exception {
        Stat mainPathExist = zooKeeper.exists(BLACKLIST_ZK_MAIN_PATH, false);
        if (mainPathExist == null) {
            zooKeeper.create(BLACKLIST_ZK_MAIN_PATH, "".getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        Stat parentPathExist = zooKeeper.exists(BLACKLIST_ZK_PATH, false);
        if (parentPathExist == null) {
            zooKeeper.create(BLACKLIST_ZK_PATH, "".getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        String clusterBlacklistPath = String.format("%s/%s", BLACKLIST_ZK_PATH,
                clusterName);
        Stat clusterBlacklistPathExists = zooKeeper.exists(clusterBlacklistPath, false);
        if (clusterBlacklistPathExists == null) {
            zooKeeper.create(clusterBlacklistPath, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.PERSISTENT);
        }
        return clusterBlacklistPath;
    }


    public ZooKeeper getZkClient() throws IOException {
        return new ZooKeeper(BlackLIstZkAddressEnum.getZkAddressByClusterName(GinkgoConfig.KAFKA_BLACKLIST_ZOOKEEPER_ADDRESS), ZK_SESSION_TIMEOUT, null);
    }

//    private AdminClient getAdminClient() {
//        Properties props = new Properties();
//        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, ClusterBootstrapServerEnums.getBootstrapByClusterName(cluster));
//        AdminClient adminClient = AdminClient.create(props);
//        return adminClient;
//    }


    private ZkUtils getZkUtils(String cluster) {
        return ZkUtils.apply(ClusterBootstrapServerEnums.getZkAddressByClusterName(cluster), ZK_SESSION_TIMEOUT,
                ZK_SESSION_TIMEOUT, false);
    }

}
