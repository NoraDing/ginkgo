package com.nora.ginkgo.core;

import com.google.common.base.Preconditions;
import com.nora.ginkgo.core.client.OptionInit;
import com.nora.ginkgo.core.enums.OperateType;
import com.nora.ginkgo.core.job.KafkaBlackListTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GinkgoApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(GinkgoApplication.class);

    public static void main(String[] args) {

        try {
            Preconditions.checkArgument(args != null && args.length > 0);
            GinkgoContext ginkgoContext = GinkgoContext.getInstance();

            //1.启动参数解析
            OptionInit optionInit = new OptionInit(args);
            optionInit.startUp();

            int operateType = ginkgoContext.getGinkgoConfig().getOperateType();
            KafkaBlackListTask kafkaBlackListTask = new KafkaBlackListTask(ginkgoContext.getGinkgoConfig());
            if (OperateType.REGISTER.getType() == operateType) {

                //注册黑名单
                kafkaBlackListTask.register();
            } else if (OperateType.DELETE.getType() == operateType) {
                //删除黑名单
                kafkaBlackListTask.delete();
            }
            LOGGER.info(" -------------finish------------");
        } catch (Exception e) {
            LOGGER.error("has issue here", e);
            System.exit(-1);
        }
        System.exit(1);
    }
}