package com.bilibili.syringa.core.service;

import com.bapis.datacenter.shielder.shielder.RequestStr;
import com.bapis.datacenter.shielder.shielder.ShielderUserGrpc;
import com.bapis.datacenter.shielder.shielder.UserDTO;
import com.bilibili.syringa.core.dto.KafkaTopicDTO;
import org.springframework.stereotype.Service;
import pleiades.venus.starter.rpc.client.RPCClient;

@Service
public class ShielderGrpcService {
    public static final String SHIELDER_DISCOVERY_ID = "datacenter.shielder.shielder";

    @RPCClient(SHIELDER_DISCOVERY_ID)
    private ShielderUserGrpc.ShielderUserBlockingStub stub;

    public UserDTO getUserId(KafkaTopicDTO kafkaTopicDTO) {

        RequestStr request = RequestStr.newBuilder()
                .setReqStr(kafkaTopicDTO.getUsername())
                .build();
        UserDTO userDTO = stub.findByUsername(request);
        return userDTO;

    }

}
