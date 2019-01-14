/**
 * Bilibili.com Inc.
 * Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.executor;

/**
 *
 * @author dingsainan
 * @version $Id: Application.java, v 0.1 2019-01-14 下午12:13 dingsainan Exp $$
 */
public interface Application<TYPE extends Enum<TYPE>> {

    TYPE getType();

    String toString();

}