package com.bilibili.syringa.core.service;

import com.bapis.datacenter.service.keeper.*;
import com.bilibili.syringa.core.dto.KafkaTopicDTO;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pleiades.venus.starter.rpc.client.RPCClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class KeeperGrpcService {
    private static final Logger LOGGER = LoggerFactory.getLogger(KeeperGrpcService.class);


    public static final String KEEPER_DISCOVERY_ID = "datacenter.keeper.keeper";

    @RPCClient(KEEPER_DISCOVERY_ID)
    private KeeperMultiTableGrpc.KeeperMultiTableBlockingStub stub;
    @RPCClient(KEEPER_DISCOVERY_ID)
    private KeeperMultiDatasourceGrpc.KeeperMultiDatasourceBlockingStub sourceStub;

    private static boolean WITHOUT_CREATE_TABLE = true;

    public void grpcAddTable(KafkaTopicDTO kafkaInfo) {

        LOGGER.info("start grpc to keeper");

        BasicModule basicModule = BasicModule.newBuilder()
                .setDsName("Kafka_"+kafkaInfo.getCluster())
                .setDbName("default")
                .setTabName(kafkaInfo.getTopic())
                .setUserId(kafkaInfo.getUserId())
                .setDataDuration(kafkaInfo.getRetentionDay())
                .build();
        BusinessModule businessModule = BusinessModule.newBuilder()
                .setDepartmentName(kafkaInfo.getDepartmentName()).build();
        ConfigurationModule configurationModule = ConfigurationModule.newBuilder().setColSeparator("\u0001").setPartitionNumber(kafkaInfo.getPartition()).build();
        ContentModule contentModule = ContentModule.newBuilder()
                .build();
        ModelModule modelModule = ModelModule.newBuilder().setModelLevel(1).build();
        List<Column> columns = new ArrayList<>();
        Column column = Column.newBuilder().setColIndex(0).setColName("json").setColType("STRING")
                .setPrivilegeLevel("").setIsPartition(false).build();
        columns.add(column);
        BaseTable baseTable = BaseTable.newBuilder().setDsType(DbType.Kafka).setBasicModule(basicModule).setBusinessModule(businessModule)
                .setContentModule(contentModule)
                .setInitDataWithoutBuildTable(WITHOUT_CREATE_TABLE)
                .setModelModule(modelModule)
                .setConfigurationModule(configurationModule)
                .addAllCols(columns).build();
        stub.createTable(baseTable);
        LOGGER.info("add table done:{}", kafkaInfo.getTopic());
    }
}
