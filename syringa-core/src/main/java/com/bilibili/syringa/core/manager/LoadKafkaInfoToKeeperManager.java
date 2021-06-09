package com.bilibili.syringa.core.manager;

import com.bapis.datacenter.shielder.shielder.UserDTO;
import com.bilibili.syringa.core.configuration.ApplicationConfig;
import com.bilibili.syringa.core.dto.KafkaTopicDTO;
import com.bilibili.syringa.core.service.ExcelService;
import com.bilibili.syringa.core.service.KafkaService;
import com.bilibili.syringa.core.service.KeeperGrpcService;
import com.bilibili.syringa.core.service.ShielderGrpcService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class LoadKafkaInfoToKeeperManager implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoadKafkaInfoToKeeperManager.class);
    private static String INTERNAL = "internal";

    @Autowired
    private KeeperGrpcService keeperGrpcService;
    @Autowired
    private ShielderGrpcService shielderGrpcService;
    @Autowired
    private ApplicationConfig applicationConfig;

    /**
     *
     */
    @PostConstruct
    public void init() {
        run();
    }

    @Override
    public void run() {
        LOGGER.info(" refresh the cache start at {} ", LocalDateTime.now());
        refreshAllInfo();
        LOGGER.info(" refresh the cache end at {} ", LocalDateTime.now());
    }


    private void refreshAllInfo() {
        String bootstrapServer = applicationConfig.getBootstrapServer();
        String cluster = applicationConfig.getCluster();//集群的名字
        String filePath = applicationConfig.getFilePath();//excel文件的路径
        String zookeeperAddr = applicationConfig.getZookeeperAddr();
        String clusterAttr = applicationConfig.getClusterAttr();
        LOGGER.info("the bootstrapServer:{},cluster:{},filePath:{},zookeeperAddress:{},clusterAttr:{}"
                , bootstrapServer, cluster, filePath, zookeeperAddr, clusterAttr);

        //excel读到的数据来获取partition(2.4.1.3版本)
        KafkaService kafkaService = new KafkaService(bootstrapServer);
        List<KafkaTopicDTO> kafkaTopicDTOS;
        if (INTERNAL.equals(clusterAttr)) {
            //内部通过api来读取
            List<String> existsTopics = ExcelService.readTopicExcel(filePath);
            LOGGER.info("all topics in keeper");
            kafkaTopicDTOS = kafkaService.loadAllTopics(cluster, existsTopics);
        } else {
            //外部从excel读数据
            kafkaTopicDTOS = ExcelService.readExcel(filePath);
        }
        if (CollectionUtils.isEmpty(kafkaTopicDTOS)) {
            LOGGER.info("no info in excel ");
            return;
        }
        LOGGER.info("loaded topic info :{}", kafkaTopicDTOS.toString());
        for (KafkaTopicDTO kafkaTopicDTO : kafkaTopicDTOS) {
            if (!cluster.equals(kafkaTopicDTO.getCluster())) {
                continue;
            }
            kafkaService.acquireKafkaTopicConfigInfo(kafkaTopicDTO);
            UserDTO userDTO = shielderGrpcService.getUserId(kafkaTopicDTO);
            LOGGER.info("the userDTO:{}", userDTO.getId());
            kafkaTopicDTO.setUserId(userDTO.getId());
            LOGGER.info("kafkaTopicDTO :{}", kafkaTopicDTO.toString());
            keeperGrpcService.grpcAddTable(kafkaTopicDTO);
        }
        LOGGER.info("insert done ");
    }
}
