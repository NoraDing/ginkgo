/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.config;

/**
 * @author dingsainan
 * @version $Id: SyringaSystemConfig.java, v 0.1 2019-01-14 上午11:43 dingsainan Exp $$
 */
public class SyringaSystemConfig {

    private String appType;
    private String appNumber;
    private String reqTimes;
    private String pkgType;
    private String topicNumber;
    private String nMessage;
    private String packageScale;
    private String appConfig;

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getAppNumber() {
        return appNumber;
    }

    public void setAppNumber(String appNumber) {
        this.appNumber = appNumber;
    }

    public String getReqTimes() {
        return reqTimes;
    }

    public void setReqTimes(String reqTimes) {
        this.reqTimes = reqTimes;
    }

    public String getPkgType() {
        return pkgType;
    }

    public void setPkgType(String pkgType) {
        this.pkgType = pkgType;
    }

    public String getTopicNumber() {
        return topicNumber;
    }

    public void setTopicNumber(String topicNumber) {
        this.topicNumber = topicNumber;
    }

    public String getnMessage() {
        return nMessage;
    }

    public void setnMessage(String nMessage) {
        this.nMessage = nMessage;
    }

    public String getPackageScale() {
        return packageScale;
    }

    public void setPackageScale(String packageScale) {
        this.packageScale = packageScale;
    }

    public String getAppConfig() {
        return appConfig;
    }

    public void setAppConfig(String appConfig) {
        this.appConfig = appConfig;
    }
}