package com.bilibili.syringa.core.service;

import com.bapis.datacenter.service.keeper.*;
import com.bilibili.syringa.core.dto.KafkaTopicDTO;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pleiades.venus.starter.rpc.client.RPCClient;

import java.util.Collections;

@Service
public class KeeperGrpcService {
    private static final Logger LOGGER = LoggerFactory.getLogger(KeeperGrpcService.class);


    public static final String KEEPER_DISCOVERY_ID = "datacenter.keeper.keeper";
    private static String DEFAULT_DB_ID = "e53136f53ecd45649b8d679dde6cf2e1";

    @RPCClient(KEEPER_DISCOVERY_ID)
    private KeeperMultiTableGrpc.KeeperMultiTableBlockingStub stub;
    @RPCClient(KEEPER_DISCOVERY_ID)
    private KeeperMultiDatasourceGrpc.KeeperMultiDatasourceBlockingStub sourceStub;

    private static boolean WITHOUT_CREATE_TABLE = true;

    public void grpcAddTable(KafkaTopicDTO kafkaInfo) {

        LOGGER.info("start grpc to keeper");

        DatasourceConfig datasourceConfig = DatasourceConfig.newBuilder().setDsName(kafkaInfo.getCluster()).build();
        BasicModule basicModule = BasicModule.newBuilder()
                .setDsId("1714")
                .setDsName(kafkaInfo.getCluster())
                .setDbId(DEFAULT_DB_ID)
                .setDbName("default")
                .setTabId("")
                .setTabName(kafkaInfo.getTopic())
                .setUserId(kafkaInfo.getUserId())
                .setDataDuration(kafkaInfo.getRetentionDay())
                .build();
        BusinessModule businessModule = BusinessModule.newBuilder().setDepartmentId(1111).setDepartmentName(kafkaInfo.getDepartmentName()).build();
        ConfigurationModule configurationModule = ConfigurationModule.newBuilder().setColSeparator("\\/u0001").setPartitionNumber(kafkaInfo.getPartition()).build();
        ContentModule contentModule = ContentModule.newBuilder().setDataLevel("B").build();
        ModelModule modelModule = ModelModule.newBuilder().setModelLevel(1).build();
        BaseTable baseTable = BaseTable.newBuilder().setDsType(DbType.Kafka).setBasicModule(basicModule).setBusinessModule(businessModule)
                .setContentModule(contentModule)
                .setInitDataWithoutBuildTable(WITHOUT_CREATE_TABLE)
                .setModelModule(modelModule)
                .setConfigurationModule(configurationModule)
                .addAllCols(Collections.EMPTY_LIST).build();
        stub.createTable(baseTable);
        LOGGER.info("add table done:{}", kafkaInfo.getTopic());
    }
}
