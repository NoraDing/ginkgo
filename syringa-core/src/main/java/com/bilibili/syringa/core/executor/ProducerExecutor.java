/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.executor;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bilibili.syringa.core.config.SyringaSystemConfig;
import com.bilibili.syringa.core.config.SyringaTopicConfig;
import com.bilibili.syringa.core.producer.ProducerApp;

/**
 * @author dingsainan
 * @version $Id: ProducerExecutor.java, v 0.1 2019-01-14 下午12:00 dingsainan Exp $$
 */
public class ProducerExecutor implements Executor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerExecutor.class);
    private SyringaSystemConfig syringaSystemConfig;

    public void setSyringaSystemConfig(SyringaSystemConfig syringaSystemConfig) {
        this.syringaSystemConfig = syringaSystemConfig;
    }

    private ProducerApp              producerApp;

    private List<SyringaTopicConfig> syringaTopicConfigs;

    //真正的执行过程
    @Override
    public void execute() {

        int totalMessageCount = 0; //= Integer.valueOf(syringaSystemConfig.getMessages());
        int appNumber = 0; //= Integer.valueOf(syringaSystemConfig.getTopicList());

        //根据topic的个数，选择要进行produce的topic
        String topic = selectTopic();

        //根据app的个数，创建producer客户端
        while (appNumber > 0) {
            sendData(totalMessageCount, topic);
            appNumber--;

        }

        //根据数据包的类型，生成数据

    }

    /**
     * 随机选择一个topic/还是根据partition的个数选择一个topic
     */
    private String selectTopic() {

        //从集群中找到对应的topic的个数，topic的partition个数可选
        return null;

    }

    /**
     * 根据数据包的类型，生成数据
     */

    private void sendData(int totalMessageCount, String topic) {
        KafkaProducer kafkaProducer = producerApp.createProducer("");
        if (CollectionUtils.isEmpty(syringaTopicConfigs)) {
            long startDate = System.currentTimeMillis();
            while (totalMessageCount > 0) {
                totalMessageCount--;
            }
            long endDate = System.currentTimeMillis();
            long timeElapsed = endDate - startDate;

            //生成运行速率的情况（csv或者直接输出在console）

        }

        if (CollectionUtils.isNotEmpty(syringaTopicConfigs)) {
            int finalTotalMessageCount = totalMessageCount;
            syringaTopicConfigs.stream().forEach(syringaTopicConfig -> {

            });

        }

    }

    public static void main(String[] args) {

    }
}
