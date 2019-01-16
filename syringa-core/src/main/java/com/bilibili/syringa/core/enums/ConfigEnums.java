/**
 * Bilibili.com Inc.
 * Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.enums;

/**
 *
 * @author dingsainan
 * @version $Id: ConfigEnums.java, v 0.1 2019-01-15 下午8:20 dingsainan Exp $$
 */
public enum ConfigEnums {

    BOOTSTRAP_SERVERS_CONFIG    (1, "bootstrap.servers"),
    BATCH_SIZE_CONFIG    (2, "batch.size"),
    ACKS_CONFIG    (3,  "acks"),
    TIMEOUT_CONFIG    (4, "timeout.ms"),
    LINGER_MS_CONFIG    (5, "linger.ms"),
    CLIENT_ID_CONFIG    (6, "client.id"),
    MAX_REQUEST_SIZE_CONFIG    (7, "max.request.size"),
    RECONNECT_BACKOFF_MS_CONFIG    (8,"reconnect.backoff.ms"),
    MAX_BLOCK_MS_CONFIG    (9,"max.block.ms"),
    COMPRESSION_TYPE_CONFIG    (10, "compression.type"),
    RETRIES_CONFIG    (11, "retries"),
    KEY_SERIALIZER_CLASS_CONFIG(12,"key.serializer"),
    VALUE_SERIALIZER_CLASS_CONFIG(13,"value.serializer"),
    BUFFER_MEMORY_CONFIG(14,"buffer.memory"),


    //consumer
    GROUP_ID_CONFIG(14,"group.id"),
    ENABLE_AUTO_COMMIT_CONFIG(14,"enable.auto.commit"),
    AUTO_COMMIT_INTERVAL_MS_CONFIG(14,"auto.commit.interval.ms");




    private int type;
    private String field;

    ConfigEnums(int type, String field) {
        this.type = type;
        this.field = field;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public static boolean validConfig(String name) {
        ConfigEnums[] values = ConfigEnums.values();
        for(ConfigEnums config:values){
            if(config.getField().equals(name)){
                return true;
            }
        }
        return false;
    }
}