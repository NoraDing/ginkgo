/**
 * Bilibili.com Inc. Copyright (c) 2009-2019 All Rights Reserved.
 */
package com.bilibili.syringa.core.config;

/**
 * @author dingsainan
 * @version $Id: SyringaSystemConfig.java, v 0.1 2019-01-14 上午11:43 dingsainan Exp $$
 */
public class SyringaSystemConfig {

    private String appId;
    private String appNumber;
    private String reqNumber;
    private String pkgType;
    private String partitionNumber;
    private String totalMessageCount;
    private String packageScale;
    private String appConfig;        //To be determined

    public SyringaSystemConfig() {

    }

    public SyringaSystemConfig(String appId, String appNumber, String reqNumber, String pkgType,
                               String partitionNumber, String totalMessageCount,
                               String packageScale, String appConfig) {
        this.appId = appId;
        this.appNumber = appNumber;
        this.reqNumber = reqNumber;
        this.pkgType = pkgType;
        this.partitionNumber = partitionNumber;
        this.totalMessageCount = totalMessageCount;
        this.packageScale = packageScale;
        this.appConfig = appConfig;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppNumber() {
        return appNumber;
    }

    public void setAppNumber(String appNumber) {
        this.appNumber = appNumber;
    }

    public String getReqNumber() {
        return reqNumber;
    }

    public void setReqNumber(String reqNumber) {
        this.reqNumber = reqNumber;
    }

    public String getPkgType() {
        return pkgType;
    }

    public void setPkgType(String pkgType) {
        this.pkgType = pkgType;
    }

    public String getPartitionNumber() {
        return partitionNumber;
    }

    public void setPartitionNumber(String partitionNumber) {
        this.partitionNumber = partitionNumber;
    }

    public String getTotalMessageCount() {
        return totalMessageCount;
    }

    public void setTotalMessageCount(String totalMessageCount) {
        this.totalMessageCount = totalMessageCount;
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

    @Override
    public String toString() {
        return "SyringaSystemConfig{" + "appId='" + appId + '\'' + ", appNumber='" + appNumber
               + '\'' + ", reqNumber='" + reqNumber + '\'' + ", pkgType='" + pkgType + '\''
               + ", partitionNumber='" + partitionNumber + '\'' + ", totalMessageCount='"
               + totalMessageCount + '\'' + ", packageScale='" + packageScale + '\''
               + ", appConfig='" + appConfig + '\'' + '}';
    }
}