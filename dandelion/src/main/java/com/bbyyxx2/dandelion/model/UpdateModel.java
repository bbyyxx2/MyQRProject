package com.bbyyxx2.dandelion.model;

import java.io.Serializable;

public class UpdateModel implements Serializable{


    /**
     * code : 0
     * message :
     * data : {"buildBuildVersion":"8","forceUpdateVersion":"","forceUpdateVersionNo":"","needForceUpdate":false,"downloadURL":"https://www.pgyer.com/app/installUpdate/424433327e6050889f19130e9735bccb?sig=8QXQ5AH4KhKxZQViR8M2NiHECaIj0Khc%2BxVRN%2FFKuPEQ64I6Ki4tPwp7tzBaSu4p&forceHttps=","buildHaveNewVersion":true,"buildVersionNo":"8","buildVersion":"1.5.2","buildDescription":"","buildUpdateDescription":"新增了历史记录功能，开启保留功能后，每次记录将保留进历史记录中。","buildShortcutUrl":"https://www.pgyer.com/MyQRProject","appURl":"https://www.pgyer.com/424433327e6050889f19130e9735bccb","appKey":"192b1f7aa331090c61bccfae3f3723f5","buildKey":"424433327e6050889f19130e9735bccb","buildName":"扫码小生","buildIcon":"https://cdn-app-icon.pgyer.com/b/0/7/e/3/b07e3c58b0e733fad74b75f2afe0d065?x-oss-process=image/resize,m_lfit,h_120,w_120/format,jpg","buildFileKey":"7edf8fd06bb36fb3f7b9b3cd99462739.apk","buildFileSize":"10178498"}
     */

    private int code;
    private String message;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * buildBuildVersion : 8
         * forceUpdateVersion :
         * forceUpdateVersionNo :
         * needForceUpdate : false
         * downloadURL : https://www.pgyer.com/app/installUpdate/424433327e6050889f19130e9735bccb?sig=8QXQ5AH4KhKxZQViR8M2NiHECaIj0Khc%2BxVRN%2FFKuPEQ64I6Ki4tPwp7tzBaSu4p&forceHttps=
         * buildHaveNewVersion : true
         * buildVersionNo : 8
         * buildVersion : 1.5.2
         * buildDescription :
         * buildUpdateDescription : 新增了历史记录功能，开启保留功能后，每次记录将保留进历史记录中。
         * buildShortcutUrl : https://www.pgyer.com/MyQRProject
         * appURl : https://www.pgyer.com/424433327e6050889f19130e9735bccb
         * appKey : 192b1f7aa331090c61bccfae3f3723f5
         * buildKey : 424433327e6050889f19130e9735bccb
         * buildName : 扫码小生
         * buildIcon : https://cdn-app-icon.pgyer.com/b/0/7/e/3/b07e3c58b0e733fad74b75f2afe0d065?x-oss-process=image/resize,m_lfit,h_120,w_120/format,jpg
         * buildFileKey : 7edf8fd06bb36fb3f7b9b3cd99462739.apk
         * buildFileSize : 10178498
         */

        private String buildBuildVersion;
        private String forceUpdateVersion;
        private String forceUpdateVersionNo;
        private boolean needForceUpdate;
        private String downloadURL;
        private boolean buildHaveNewVersion;
        private String buildVersionNo;
        private String buildVersion;
        private String buildDescription;
        private String buildUpdateDescription;
        private String buildShortcutUrl;
        private String appURl;
        private String appKey;
        private String buildKey;
        private String buildName;
        private String buildIcon;
        private String buildFileKey;
        private String buildFileSize;

        public String getBuildBuildVersion() {
            return buildBuildVersion;
        }

        public void setBuildBuildVersion(String buildBuildVersion) {
            this.buildBuildVersion = buildBuildVersion;
        }

        public String getForceUpdateVersion() {
            return forceUpdateVersion;
        }

        public void setForceUpdateVersion(String forceUpdateVersion) {
            this.forceUpdateVersion = forceUpdateVersion;
        }

        public String getForceUpdateVersionNo() {
            return forceUpdateVersionNo;
        }

        public void setForceUpdateVersionNo(String forceUpdateVersionNo) {
            this.forceUpdateVersionNo = forceUpdateVersionNo;
        }

        public boolean isNeedForceUpdate() {
            return needForceUpdate;
        }

        public void setNeedForceUpdate(boolean needForceUpdate) {
            this.needForceUpdate = needForceUpdate;
        }

        public String getDownloadURL() {
            return downloadURL;
        }

        public void setDownloadURL(String downloadURL) {
            this.downloadURL = downloadURL;
        }

        public boolean isBuildHaveNewVersion() {
            return buildHaveNewVersion;
        }

        public void setBuildHaveNewVersion(boolean buildHaveNewVersion) {
            this.buildHaveNewVersion = buildHaveNewVersion;
        }

        public String getBuildVersionNo() {
            return buildVersionNo;
        }

        public void setBuildVersionNo(String buildVersionNo) {
            this.buildVersionNo = buildVersionNo;
        }

        public String getBuildVersion() {
            return buildVersion;
        }

        public void setBuildVersion(String buildVersion) {
            this.buildVersion = buildVersion;
        }

        public String getBuildDescription() {
            return buildDescription;
        }

        public void setBuildDescription(String buildDescription) {
            this.buildDescription = buildDescription;
        }

        public String getBuildUpdateDescription() {
            return buildUpdateDescription;
        }

        public void setBuildUpdateDescription(String buildUpdateDescription) {
            this.buildUpdateDescription = buildUpdateDescription;
        }

        public String getBuildShortcutUrl() {
            return buildShortcutUrl;
        }

        public void setBuildShortcutUrl(String buildShortcutUrl) {
            this.buildShortcutUrl = buildShortcutUrl;
        }

        public String getAppURl() {
            return appURl;
        }

        public void setAppURl(String appURl) {
            this.appURl = appURl;
        }

        public String getAppKey() {
            return appKey;
        }

        public void setAppKey(String appKey) {
            this.appKey = appKey;
        }

        public String getBuildKey() {
            return buildKey;
        }

        public void setBuildKey(String buildKey) {
            this.buildKey = buildKey;
        }

        public String getBuildName() {
            return buildName;
        }

        public void setBuildName(String buildName) {
            this.buildName = buildName;
        }

        public String getBuildIcon() {
            return buildIcon;
        }

        public void setBuildIcon(String buildIcon) {
            this.buildIcon = buildIcon;
        }

        public String getBuildFileKey() {
            return buildFileKey;
        }

        public void setBuildFileKey(String buildFileKey) {
            this.buildFileKey = buildFileKey;
        }

        public String getBuildFileSize() {
            return buildFileSize;
        }

        public void setBuildFileSize(String buildFileSize) {
            this.buildFileSize = buildFileSize;
        }
    }
}

