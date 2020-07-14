package com.bilibili.syringa.core.producer.blacklist;

import com.alibaba.fastjson.JSON;
import com.bilibili.syringa.core.SyringaContext;
import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BlackListContainerZookeeper implements BlackListContainer, Watcher {
    private ZooKeeper zk;
    private BlackList blackList = new BlackList();

    private int brokersUpperLimist;

    private static final Logger LOGGER = LoggerFactory
            .getLogger(BlackListContainerZookeeper.class);

    public final static String blacklistPath = "/lancer/kafka-blacklist";
    public final static String zookeeperList = "kafka.producer.blacklist.zookeeper.list";
    public final static String zookeepersessionTimeout = "kafka.producer.blacklist.zookeeper.sessiontimeout";
    public final static String blacklistBrobersUpperLimit = "kafka.producer.blacklist.brokers.upperlimit";


    private static class Singleton {

        private static BlackListContainerZookeeper instance;

        static {
            try {
                Properties properties = new Properties();
                properties.put(zookeeperList, SyringaContext.getInstance().getSyringaSystemConfig().getBlacklistZkPath());
                instance = new BlackListContainerZookeeper(properties);
            } catch (IOException e) {
                LOGGER.error("init the black list error", e);
            }
        }
    }


    public static BlackListContainerZookeeper getInstance() {
        return Singleton.instance;
    }

    public BlackListContainerZookeeper(Properties properties) throws IOException {


        LOGGER.info("BlackListContainerZookeeper config, zk list: {}, session timeout: {}",
                properties.getProperty(zookeeperList),
                properties.getProperty(zookeepersessionTimeout, "20000"));


        try {
            zk = new ZooKeeper(properties.getProperty(zookeeperList),
                    Integer.valueOf(properties.getProperty(zookeepersessionTimeout, "20000")),
                    this);
        } catch (IOException e) {
            LOGGER.error("Failed to connect to zookeeper {}", e);
            throw e;
        }

        brokersUpperLimist = Integer.valueOf(properties.getProperty(blacklistBrobersUpperLimit, "5"));
        try {
            //Create the znode if it doesn't exist, with the following code:
            if (zk.exists(blacklistPath, this) == null) {
                zk.create(blacklistPath, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (KeeperException | InterruptedException e) {
            LOGGER.warn("Failed to create path {} on zookeeper: {}", blacklistPath, e);
        }

        LOGGER.info("BlackListContainerZookeeper init success");
        updateBlackList();
    }


    @Override
    public void process(WatchedEvent event) {
        LOGGER.info("Event Reveived from kafka: {}", event.toString());
        if (event.getType() == Event.EventType.NodeDataChanged || event.getType() == Event.EventType.NodeChildrenChanged) {
            updateBlackList();
        }
    }

    private synchronized void updateBlackList() {
        BlackList newBlackList = new BlackList();
        List<String> clusters = new ArrayList<String>();
        try {
            clusters = zk.getChildren(blacklistPath, this);
        } catch (KeeperException | InterruptedException e) {
            LOGGER.error("Failed to get Children of {}: {}", blacklistPath, e);
            return;
        }

        for (String cluster : clusters) {
            List<String> brokers = new ArrayList<String>();
            try {
                brokers = zk.getChildren(Paths.get(blacklistPath, cluster).toString(), this);
            } catch (KeeperException | InterruptedException e) {
                LOGGER.error("Failed to get Children of {}: {}", blacklistPath, e);
                return;
            }

            if (brokers.size() > brokersUpperLimist) {
                LOGGER.warn("brokers num is above upper limit {} ignored : {}", brokersUpperLimist, brokers);
                return;
            }

            for (String brokerIP : brokers) {
                byte[] value;
                try {
                    value = zk.getData(Paths.get(blacklistPath, cluster, brokerIP).toString(), this, null);
                } catch (KeeperException | InterruptedException e) {
                    LOGGER.error("Failed to get data of {}: {}", Paths.get(blacklistPath, cluster, brokerIP).toString(), e);
                    return;
                }
                List<BlackList.BlackListZKItem> items;
                try {
                    items = JSON.parseArray(new String(value), BlackList.BlackListZKItem.class);
                } catch (Exception e) {
                    LOGGER.error("json parse error for value {}: {}", new String(value), e);
                    return;
                }
                if (items == null) {
                    continue;
                }
                for (BlackList.BlackListZKItem item : items) {
                    try {
                        newBlackList.add(item.topic, item.partition, brokerIP);
                    } catch (Exception e) {
                        LOGGER.error("add value {} to BlackListZKItem error: {}", new String(value), e);
                        return;
                    }
                }
            }
        }

        LOGGER.info("kafka blacklist updated to {}", newBlackList);
        blackList = newBlackList;
    }

    @Override
    public final BlackList getBlackList() {
        return blackList;
    }
}
