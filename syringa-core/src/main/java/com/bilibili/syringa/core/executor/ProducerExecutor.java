/**
 * Bilibili.com Inc.
 * Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.executor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bilibili.syringa.core.dto.LoaderConfigDTO;
import com.bilibili.syringa.core.dto.PackageScaleDTO;
import com.bilibili.syringa.core.enums.DataSizeEnums;
import com.bilibili.syringa.core.enums.ScalePercentEnums;
import com.bilibili.syringa.core.producer.ProducerApp;
import com.bilibili.syringa.core.tool.CalculateUtils;

/**
 *
 * @author dingsainan
 * @version $Id: ProducerExecutor.java, v 0.1 2019-01-14 下午12:00 dingsainan Exp $$
 */
@Component
public class ProducerExecutor implements Executor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerExecutor.class);
    private LoaderConfigDTO     loaderConfigDTO;

    public void setLoaderConfigDTO(LoaderConfigDTO loaderConfigDTO) {
        this.loaderConfigDTO = loaderConfigDTO;
    }

    @Autowired
    private ProducerApp           producerApp;

    private List<PackageScaleDTO> packageScaleDTOS;

    //真正的执行过程
    @Override
    public void execute() {

        int totalMessageCount = Integer.valueOf(loaderConfigDTO.getTotalMessageCount());
        int appNumber = Integer.valueOf(loaderConfigDTO.getAppNumber());
        packageScaleDTOS = parsePackageScale(loaderConfigDTO.getPackageScale());

        //根据topic的个数，选择要进行produce的topic
        String topic = selectTopic();

        //根据app的个数，创建producer客户端
        while (appNumber > 0) {
            sendData(totalMessageCount, topic);
            appNumber--;

        }

        //根据数据包的类型，生成数据

    }

    private List<PackageScaleDTO> parsePackageScale(String value) {

        List<PackageScaleDTO> packageScaleDTOS = new ArrayList<>();

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

            int scaleType = Integer.valueOf(kv[0]);
            int packageSizeType = Integer.valueOf(kv[1]);


            packageScaleDTOS.add(new PackageScaleDTO(scaleType, packageSizeType));

        }

        return packageScaleDTOS;

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
    private byte[] generateProducerData(int dataSize) {

        byte[] bytes = new byte[dataSize];
        Random random = new Random();
        LOGGER.info("the start date is {}", LocalDateTime.now());
        for (int i = 0; i < bytes.length; ++i) {
            bytes[i] = (byte) random.nextInt();
        }
        LOGGER.info("the end date is {}", LocalDateTime.now());

        return bytes;

    }

    private void sendData(int totalMessageCount, String topic) {
        KafkaProducer kafkaProducer = producerApp.createProducer();
        if (CollectionUtils.isEmpty(packageScaleDTOS)) {
            long startDate = System.currentTimeMillis();
            while (totalMessageCount > 0) {
                byte[] generateSize = generateProducerData(DataSizeEnums.OneM.getValue());
                kafkaProducer.send(new ProducerRecord<>(topic, String.valueOf(generateSize)));
                totalMessageCount--;
            }
            long endDate = System.currentTimeMillis();
            long timeElapsed = endDate - startDate;

            //生成运行速率的情况（csv或者直接输出在console）

        }

        if (CollectionUtils.isNotEmpty(packageScaleDTOS)) {
            int finalTotalMessageCount = totalMessageCount;
            packageScaleDTOS.stream().forEach(packageScaleDTO -> {
                int scaleType = packageScaleDTO.getScaleType();
                int dataSizeType = packageScaleDTO.getDataSizeType();
                int scale = ScalePercentEnums.getValueByType(scaleType);
                int dataSize = DataSizeEnums.getValueByType(dataSizeType);
                int sharedCount = CalculateUtils.mutiple(finalTotalMessageCount, scale);

            });

        }

    }

    public static void main(String[] args) {

    }
}
