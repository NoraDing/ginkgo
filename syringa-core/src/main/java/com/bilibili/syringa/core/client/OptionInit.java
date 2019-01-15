/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.client;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bilibili.syringa.core.SyringaContext;
import com.bilibili.syringa.core.config.SyringaSystemConfig;
import com.bilibili.syringa.core.enums.TypeEnums;
import com.bilibili.syringa.core.job.JobMessageConfig;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.util.concurrent.AbstractIdleService;

/**
 * @author xuezhaoming
 * @version $Id: OptionInit.java, v 0.1 2019-01-15 10:18 AM Exp $$
 */
public class OptionInit extends AbstractIdleService {

    private static final Logger   LOGGER              = LoggerFactory.getLogger(OptionInit.class);
    private static final int      SCALE               = 1024;

    private SyringaSystemConfig   syringaSystemConfig = new SyringaSystemConfig();

    private static final Splitter EQUAL_SPLITTER      = Splitter.on("=").omitEmptyStrings();

    private static final Splitter COMMA_SPLITTER      = Splitter.on(",").omitEmptyStrings();

    private String                args[];

    public OptionInit(String[] args) {
        this.args = args;
    }

    @Override
    protected void startUp() {
        CommandLineParser parser = new BasicParser();

        Options options = generateOptions();
        CommandLine cli = null;
        try {

            cli = parser.parse(options, args);//-type 1 
            String type = cli.getOptionValue("type");//1.生产者 2.消费者
            String messages = cli.getOptionValue("message");//每个批次条数
            String concurrency = cli.getOptionValue("concurrency");//并发度
            String size = cli.getOptionValue("size");//请求大小分布  
            String topics = cli.getOptionValue("topics");//请求的主题

            Preconditions.checkNotNull(type, "type is null");
            Preconditions.checkNotNull(messages, "messages is null");
            Preconditions.checkNotNull(concurrency, "concurrency is null");
            Preconditions.checkNotNull(size, "size is null");
            Preconditions.checkNotNull(topics, "topics is null");

            //1.请求类型有效性检查  example -type 1 or 2 
            typeCheck(type);

            //2.每个批次条数检查  example -message 1000 
            messagesCheck(messages);

            //3.并发度检查 example -concurrency 10 
            concurrencyCheck(concurrency);

            //4.请求分布检查 example  -size 10=4k,20=1k   目前只接受k单位的数值，前面是百分比
            sizeCheck(size);

            //5.topics 检查 example -topics t1,t2,t3
            List<String> topicLists = COMMA_SPLITTER.splitToList(topics);
            Preconditions.checkArgument(CollectionUtils.isNotEmpty(topicLists), "topic is empty");
            syringaSystemConfig.setTopicList(topicLists);

            SyringaContext.getInstance().setSyringaSystemConfig(syringaSystemConfig);
        } catch (Exception e) {
            LOGGER.error("init args error", e);
            System.exit(-1);
        }

    }

    private void sizeCheck(String size) {

        Iterable<String> split = COMMA_SPLITTER.split(size);
        List<JobMessageConfig> jobMessageConfigs = new ArrayList<>();
        for (String sizePercent : split) {
            List<String> strings = EQUAL_SPLITTER.splitToList(sizePercent);
            if (CollectionUtils.isEmpty(strings)) {
                continue;
            }

            JobMessageConfig jobMessageConfig = new JobMessageConfig();
            String percentStr = strings.get(0);
            BigDecimal bigDecimal = new BigDecimal(percentStr);
            double percentDouble = bigDecimal.divide(new BigDecimal(100), BigDecimal.ROUND_DOWN, 2)
                .doubleValue();
            jobMessageConfig.setPercent(percentDouble);

            String messageSizeStr = strings.get(1);
            Long messageSizeLong = parseMessageSizeStr(messageSizeStr.toLowerCase());

            Preconditions.checkNotNull(messageSizeLong != null, "messageSizeLong can not be null");
            jobMessageConfig.setSize(messageSizeLong);
            jobMessageConfigs.add(jobMessageConfig);

        }

        double totalPercent = jobMessageConfigs.stream().mapToDouble(JobMessageConfig::getPercent)
            .sum();

        Preconditions.checkArgument(totalPercent == 1, "percent is sum should be 1");

        syringaSystemConfig.setJobMessageConfigList(jobMessageConfigs);
    }

    //for test
    public Long parseMessageSizeStr(String messageSizeStr) {
        int b = messageSizeStr.indexOf("b");
        int k = messageSizeStr.indexOf("k");
        int m = messageSizeStr.indexOf("m");

        if (b > 0 && k < 0 && m < 0) {
            long size = Long.valueOf(messageSizeStr.substring(0, b));
            return size;
        }

        if (k > 0 && b < 0 && m < 0) {
            long size = Long.valueOf(messageSizeStr.substring(0, k)) * SCALE;
            return size;
        }

        if (m > 0 && b < 0 && k < 0) {
            long size = Long.valueOf(messageSizeStr.substring(0, m)) * SCALE * SCALE;
            return size;
        }

        return null;

    }

    private void concurrencyCheck(String concurrency) {

        Integer concurrencyInt = Integer.valueOf(concurrency);
        if (concurrencyInt < 0) {
            LOGGER.error("invalid concurrency {}  muster bigger than zero !", concurrency);
            System.exit(-1);
        }

        syringaSystemConfig.setConcurrency(concurrencyInt);

    }

    private void messagesCheck(String messages) {

        Long messagesLong = Long.valueOf(messages);
        if (messagesLong < 0) {
            LOGGER.error("invalid messages {}  muster bigger than zero !", messages);
            System.exit(-1);
        }
        syringaSystemConfig.setMessages(messagesLong);
    }

    private void typeCheck(String type) {

        TypeEnums typeEnums = TypeEnums.isValid(Integer.valueOf(type));
        if (typeEnums == null) {
            LOGGER.error("invalid type {}", type);
            System.exit(-1);
        }
        syringaSystemConfig.setType(typeEnums);
    }

    private Options generateOptions() {
        Options options = new Options();

        options.addOption("t", "type", true, "start type");
        options.addOption("m", "message", true, "total messages");
        options.addOption("c", "concurrency", true, "sender concurrency");
        options.addOption("s", "size", true, "message size");
        options.addOption("ts", "topics", true, "number of the topic ");

        return options;
    }

    @Override
    protected void shutDown() throws Exception {

    }

}
