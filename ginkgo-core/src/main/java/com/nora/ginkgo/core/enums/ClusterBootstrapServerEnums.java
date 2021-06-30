package com.nora.ginkgo.core.enums;

import com.google.common.base.Preconditions;

public enum ClusterBootstrapServerEnums {
    kafka_cluster("clusternane", "xxxxxx", "xxx:");


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
