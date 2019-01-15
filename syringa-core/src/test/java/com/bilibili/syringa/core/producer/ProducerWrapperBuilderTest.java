/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.producer;

/**
 *
 * @author xuezhaoming
 * @version $Id: ProducerWrapperBuilderTest.java, v 0.1 2019-01-15 4:03 PM Exp $$
 */
public class ProducerWrapperBuilderTest {

    public void builder(){
        String servers = "";
        ProducerWrapper producerWrapper =  ProducerWrapperBuilder.instance(servers, topic);
    }
}
