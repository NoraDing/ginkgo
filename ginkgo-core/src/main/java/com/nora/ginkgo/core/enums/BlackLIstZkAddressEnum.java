package com.nora.ginkgo.core.enums;

import com.google.common.base.Preconditions;

public enum BlackLIstZkAddressEnum {
     KAFKA_BLACKLIST_ZOOKEEPER_ADDRESS("kafka.blacklist.zookeeper.address", "XXXX");
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
