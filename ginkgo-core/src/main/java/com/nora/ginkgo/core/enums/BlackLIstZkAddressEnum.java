package com.nora.ginkgo.core.enums;

import com.google.common.base.Preconditions;

public enum BlackLIstZkAddressEnum {
//    KAFKA_BLACKLIST_ZOOKEEPER_ADDRESS("kafka.blacklist.zookeeper.address", "10.70.141.19:2181,10.70.141.20:2181,10.70.141.21:2181");
    KAFKA_BLACKLIST_ZOOKEEPER_ADDRESS("kafka.blacklist.zookeeper.address", "10.69.99.13:2181,10.69.99.14:2181,10.69.99.15:2181,10.69.99.16:2181,10.69.99.17:2181");
    private String type;
    private String zkAddress;

    BlackLIstZkAddressEnum(String type, String zkAddress) {
        this.type = type;
        this.zkAddress = zkAddress;
    }

    public String getType() {
        return type;
    }

    public String getZkAddress() {
        return zkAddress;
    }

    public static String getZkAddressByClusterName(String type) {
        Preconditions.checkArgument(type != null);

        BlackLIstZkAddressEnum[] values = BlackLIstZkAddressEnum.values();
        for (BlackLIstZkAddressEnum value : values) {
            if (type.equals(value.getType())) {
                return value.getZkAddress();
            }
        }
        return null;
    }
}
