/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.nora.ginkgo.core.client;

import com.google.common.base.Splitter;
import com.nora.ginkgo.core.GinkgoContext;
import com.nora.ginkgo.core.config.GinkgoConfig;
import com.nora.ginkgo.core.dto.KafkaClusterAddress;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.AbstractIdleService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author xuezhaoming
 * @version $Id: OptionInit.java, v 0.1 2019-01-15 10:18 AM Exp $$
 */
public class OptionInit {

    private static final Logger LOGGER = LoggerFactory.getLogger(OptionInit.class);
    private GinkgoConfig ginkgoConfig = new GinkgoConfig();
    private String args[];

    public OptionInit(String[] args) {
        this.args = args;
    }

    public void startUp() {
        CommandLineParser parser = new BasicParser();

        Options options = generateOptions();
        CommandLine cli;
        try {

            cli = parser.parse(options, args);//-type 1
            String cluster = cli.getOptionValue("cluster");
            String topic = cli.getOptionValue("topic");
            String partition = cli.getOptionValue("partition");
            String operate = cli.getOptionValue("operate");
            String host = cli.getOptionValue("host");


            Preconditions.checkNotNull(cluster, "cluster is null");
            Preconditions.checkNotNull(topic, "topic is null");
            Preconditions.checkNotNull(partition, "partition is null");
            Preconditions.checkNotNull(operate, "operate is null");
            Preconditions.checkNotNull(operate, "host is null");

            ginkgoConfig.setOperateType(Integer.valueOf(operate));
            ginkgoConfig.setCluster(cluster);
            ginkgoConfig.setTopic(topic);
            ginkgoConfig.setPartition(Integer.valueOf(partition));
            ginkgoConfig.setHost(host);
            GinkgoContext.getInstance().setGinkgoConfig(ginkgoConfig);
        } catch (Exception e) {
            LOGGER.error("init args error", e);
            System.exit(-1);
        }
    }


    private Options generateOptions() {
        Options options = new Options();

        options.addOption("o", "operate", true, "operate");
        options.addOption("c", "cluster", true, "cluster");
        options.addOption("h", "host", true, "host");
        options.addOption("t", "topic", true, "topic");
        options.addOption("p", "partition", true, "partition");
        return options;
    }

}
