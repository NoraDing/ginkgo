package com.nora.ginkgo.core.enums;

import com.google.common.base.Preconditions;

public enum ClusterBootstrapServerEnums {
    HW_SH_DC_LANCER_KAFKA_EXT("hw-sh-dc-lancer-kafka-ext", "10.221.51.174:9092,10.221.50.145:9092,10.221.50.131:9092", "10.221.51.174:2181,10.221.50.145:2181,10.221.50.131:2181/hw-sh-dc-lancer-kafka-ext"),
    JSSZ_EPHEMERAL_KAFKA("jssz-ephemeral-kafka", "10.70.141.19:9092,10.70.141.20:9092,10.70.141.21:9092","10.70.141.19:2181,10.70.141.20:2181,10.70.141.21:2181/jssz-ephemeral-kafka"),
    JSSZ_LOWLEVEL_AKAFKA("jssz-lowlevel-akafka", "10.69.88.21:9092,10.69.88.22:9092,10.69.88.23:9092,10.69.88.24:9092,10.69.88.25:9092,10.69.88.26:9092,10.69.88.27:9092,10.69.88.28:9092,10.69.88.29:9092","10.69.88.21:2181,10.69.88.22:2181,10.69.88.23:2181,10.69.88.24:2181,10.69.88.25:2181,10.69.88.26:2181,10.69.88.27:2181,10.69.88.28:2181,10.69.88.29:2181/jssz-lowlevel-akafka"),
    JSSZ_HIGHLEVEL_AKAFKA("jssz-highlevel-akafka", "10.69.90.20:9092,10.69.90.21:9092,10.69.90.22:9092,10.69.91.11:9092,10.69.91.12:9092,10.69.91.13:9092,10.69.91.14:9092","10.69.90.20:2181,10.69.90.21:2181,10.69.90.22:2181,10.69.91.11:2181,10.69.91.12:2181,10.69.91.13:2181,10.69.91.14:2181/jssz-highlevel-akafka"),
    JSSZ_LOWLEVEL_PKAFKA("jssz-lowlevel-pakafka", "10.69.142.34:9092,10.69.176.29:9092,10.69.176.30:9092,10.69.176.31:9092,10.69.176.32:9092,10.69.176.33:9092,10.69.176.34:9092","10.69.142.34:2181,10.69.176.29:2181,10.69.176.30:2181,10.69.176.31:2181,10.69.176.32:2181,10.69.176.33:2181,10.69.176.34:2181/jssz-lowlevel-pakafka"),
    JSSZ_MIDDLE_PKAFKA("jssz-middle-pakafka", "10.70.141.27:9092,10.70.141.28:9092,10.70.141.29:9092,10.70.141.30:9092,10.70.141.31:9092","10.70.141.27:2181,10.70.141.28:2181,10.70.141.29:2181,10.70.141.30:2181,10.70.141.31:2181/jssz-middle-pakafka"),
    JSSZ_HIGHLEVEL_PKAFKA("jssz-highlevel-pakafka", "10.70.141.32:9092,10.70.141.33:9092,10.70.141.34:9092,10.70.142.11:9092,10.70.142.12:9092","10.70.141.32:2181,10.70.141.33:2181,10.70.141.34:2181,10.70.142.11:2181,10.70.142.12:2181/jssz-highlevel-pakafka"),
    JSSZ_LOWLEVEL_CKAFKA("jssz-lowlevel-ckafka", "10.69.88.31:9092,10.69.88.32:9092,10.69.88.33:9092,10.69.88.34:9092,10.69.89.16:9092,10.69.89.17:9092,10.69.89.18:9092,10.69.89.19:9092,10.69.89.20:9092","10.69.88.31:2181,10.69.88.32:2181,10.69.88.33:2181,10.69.88.34:2181,10.69.89.16:2181,10.69.89.17:2181,10.69.89.18:2181,10.69.89.19:2181,10.69.89.20:2181/jssz-lowlevel-ckafka"),
    JSSZ_HIGHELEVEL_CKAFKA("jssz-highlevel-ckafka", "10.69.90.32:9092,10.69.90.33:9092,10.69.90.34:9092,10.69.91.15:9092,10.69.91.16:9092,10.69.91.17:9092,10.69.91.18:9092","10.69.90.32:2181,10.69.90.33:2181,10.69.90.34:2181,10.69.91.15:2181,10.69.91.16:2181,10.69.91.17:2181,10.69.91.18:2181/jssz-highlevel-ckafka"),
    LANCER_FAILOVER_KAFKA("lancer_failover_kafka", "10.69.99.13:9092,10.69.99.14:9092,10.69.99.15:9092,10.69.99.16:9092,10.69.99.17:9092","10.69.99.13:2181,10.69.99.14:2181,10.69.99.15:2181,10.69.99.16:2181,10.69.99.17:2181/lancer_failover_kafka"),
    JSSZ_KAFKA_VIDEO("jssz-kafka-video", "10.70.232.22:9092,10.70.232.20:9092,10.70.232.19:9092,10.70.232.18:9092,10.70.232.21:9092","10.70.232.22:2181,10.70.232.20:2181,10.70.232.19:2181,10.70.232.18:2181,10.70.232.21:2181/jssz-kafka-video"),
    JSSZ_KAFKA_SYCPB("jssz-kafka-sycpb", "10.70.231.19:9092,10.70.231.21:9092,10.70.231.23:9092,10.70.231.22:9092,10.70.231.20:9092","10.70.231.19:2181,10.70.231.21:2181,10.70.231.23:2181,10.70.231.22:2181,10.70.231.20:2181/jssz-kafka-sycpb");


    private String clusterName;
    private String bootstrap;
    private String zkAddress;

    ClusterBootstrapServerEnums(String clusterName, String bootstrap, String zkAddress) {
        this.clusterName = clusterName;
        this.bootstrap = bootstrap;
        this.zkAddress = zkAddress;
    }

    public String getClusterName() {
        return clusterName;
    }

    public String getBootstrap() {
        return bootstrap;
    }

    public String getZkAddress() {
        return zkAddress;
    }

    public static String getBootstrapByClusterName(String clustername) {
        Preconditions.checkArgument(clustername != null);

        ClusterBootstrapServerEnums[] values = ClusterBootstrapServerEnums.values();
        for (ClusterBootstrapServerEnums value : values) {
            if (clustername.equals(value.getClusterName())) {
                return value.getBootstrap();
            }
        }
        return null;
    }


    public static String getZkAddressByClusterName(String clustername) {
        Preconditions.checkArgument(clustername != null);

        ClusterBootstrapServerEnums[] values = ClusterBootstrapServerEnums.values();
        for (ClusterBootstrapServerEnums value : values) {
            if (clustername.equals(value.getClusterName())) {
                return value.getZkAddress();
            }
        }
        return null;
    }
}
