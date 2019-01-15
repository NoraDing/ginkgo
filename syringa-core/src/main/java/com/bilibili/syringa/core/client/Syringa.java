/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.client;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bilibili.syringa.core.SyringaContext;
import com.bilibili.syringa.core.config.SyringaSystemConfig;
import com.bilibili.syringa.core.config.SyringaTopicConfig;
import com.bilibili.syringa.core.enums.ApplicationEnums;
import com.bilibili.syringa.core.executor.ConsumerExecutor;
import com.bilibili.syringa.core.executor.Executor;
import com.bilibili.syringa.core.executor.ProducerExecutor;
import com.bilibili.syringa.core.job.JobConfig;
import com.bilibili.syringa.core.tool.CalculateUtils;

/**
 * @author dingsainan
 * @version $Id: Syringa.java, v 0.1 2019-01-14 上午11:21 dingsainan Exp $$
 */
public class Syringa {
    private static final Logger LOGGER = LoggerFactory.getLogger(Syringa.class);

    public static void main(String[] args) {

        //1.参数解析 args
        try {
            Options options = generateOptions();
            CommandLineParser parser = new BasicParser();

            SyringaSystemConfig syringaSystemConfig = new SyringaSystemConfig();

            CommandLine cli = parser.parse(options, args);
            String appType = cli.getOptionValue("appType");
            String requestTimes = cli.getOptionValue("requestTimes");
            String nMessage = cli.getOptionValue("nMessage");
            String packageScale = cli.getOptionValue("packageScale");
            String topicNumber = cli.getOptionValue("topicNumber");

            syringaSystemConfig.setAppType(appType);
            syringaSystemConfig.setPackageScale(packageScale);
            syringaSystemConfig.setReqTimes(requestTimes);
            syringaSystemConfig.setnMessage(nMessage);
            syringaSystemConfig.setTopicNumber(topicNumber);

            SyringaContext.getInstance().setSyringaSystemConfig(syringaSystemConfig);

            //2.初始化job ,转化成jobConfig
            JobConfig jobConfig = generateJobConfig(syringaSystemConfig);

            //3.作业运行

            //4.收集统计信息

            //register and start dispatcher
            //        SyringaSystemConfig syringaSystemConfig = new SyringaSystemConfig(appId, appNumber,
            //            reqNumber, pkgType, partitionNumber, nMessage, packageScale, appConfig);
            //        registerAndStartDispatcher(appType, syringaSystemConfig);

        } catch (ParseException e) {
            LOGGER.error("has issue here", e);
        }

    }

    private static JobConfig generateJobConfig(SyringaSystemConfig syringaSystemConfig) {

        JobConfig jobConfig = new JobConfig();
        String nMessage = syringaSystemConfig.getnMessage();
        String packageScale = syringaSystemConfig.getPackageScale();
        if (packageScale != null) {
            List<SyringaTopicConfig> syringaTopicConfigs = parsePackageScale(
                Integer.valueOf(nMessage), packageScale);
            jobConfig.setSyringaTopicConfigs(syringaTopicConfigs);

        }

        jobConfig.setAppType(Integer.valueOf(syringaSystemConfig.getAppType()));
        jobConfig.setTopicNumber(Integer.valueOf(syringaSystemConfig.getTopicNumber()));
        jobConfig.setnMessage(Integer.valueOf(nMessage));
        jobConfig.setAppNumber(Integer.valueOf(syringaSystemConfig.getAppNumber()));

        return null;

    }

    private static List<SyringaTopicConfig> parsePackageScale(int nMessage, String value) {

        List<SyringaTopicConfig> syringaTopicConfigs = new ArrayList<>();

        // sample="1:1;2:1;3:2"
        String[] splits = value.split(";");
        if (splits == null || splits.length == 0) {
            return null;
        }
        for (String split : splits) {
            String[] kv = split.split(":");
            if (kv == null || kv.length != 2) {
                continue;
            }

            int scale = Integer.valueOf(kv[0]);
            int dataSize = Integer.valueOf(kv[1]);
            int sharedCount = CalculateUtils.mutiple(nMessage, scale);

            syringaTopicConfigs.add(new SyringaTopicConfig(scale, dataSize, sharedCount));

        }

        return syringaTopicConfigs;

    }

    private static Options generateOptions() {
        Options options = new Options();

        options.addOption("appType", "appType", true, "type of the application");
        options.addOption("requestTimes", "requestTimes", true, "times of the request");
        options.addOption("nMessage", "nMessage", true, "numbers of the sent message");
        options.addOption("packageScale", "packageScale", true, "scale per package");
        options.addOption("topicNumber", "topicNumber", true, "number of the topic ");

        return options;
    }

    private static void registerAndStartDispatcher(String appType,
                                                   SyringaSystemConfig syringaSystemConfig) {

        LinkedBlockingDeque<Executor> executors = new LinkedBlockingDeque<>();

        if (ApplicationEnums.PRODUCER.getType() == Integer.valueOf(appType)) {
            executors.add(new ConsumerExecutor());

        }

        if (ApplicationEnums.CONSUMER.getType() == Integer.valueOf(appType)) {
            executors.add(new ProducerExecutor());

        }
        //        JobDispatcher appDispatcher = new JobDispatcher(syringaSystemConfig, executors);

        //        appDispatcher.serviceStart();

    }

}