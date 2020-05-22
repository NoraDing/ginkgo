/**
 * Bilibili.com Inc.
 * Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.producer.blacklist;

/**
 * kafka blackList存放的类,从discovery获取注册的信息,监听更新,并转换为可用数据结构.
 *
 * @author zhouhuidong
 * @version $Id: BlackListContainer.java, v 0.1 2019-06-12 7:39 PM zhouhuidong Exp $$
 */
public interface BlackListContainer {
    BlackList getBlackList();
}
