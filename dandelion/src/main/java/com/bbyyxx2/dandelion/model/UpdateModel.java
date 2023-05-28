package com.bbyyxx2.dandelion.model;

public class UpdateModel {

    /**
     * code : 0
     * message :
     * data : {"buildBuildVersion":"1","forceUpdateVersion":"","forceUpdateVersionNo":"","needForceUpdate":false,"downloadURL":"https://www.pgyer.com/app/installUpdate/9836dd43ebad7f76282c30207ce063d1?sig=%2FKFYc1TJHFOxxqMJy9nqHpQUCZAsxnCw7FcBrz%2BlQ6BileceSk%2FU%2BB0V1ygzvKzv&forceHttps=","buildHaveNewVersion":false,"buildVersionNo":"3","buildVersion":"1.2","buildDescription":"bob的扫码和更新测试","buildUpdateDescription":"首次发布","appKey":"5372e3bf7e1026b91aec511b9a4fc958","buildKey":"9836dd43ebad7f76282c30207ce063d1","buildName":"扫一扫","buildIcon":"https://cdn-app-icon.pgyer.com/c/d/9/7/f/cd97ffc6fee4a3470bffd74f82518b5b?x-oss-process=image/resize,m_lfit,h_120,w_120/format,jpg","buildFileKey":"7d4824499beb28791b3818f206a40028.apk","buildFileSize":"8476983"}
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

    public static class DataBean {
        /**
         * buildBuildVersion : 1
         * forceUpdateVersion :
         * forceUpdateVersionNo :
         * needForceUpdate : false
         * downloadURL : https://www.pgyer.com/app/installUpdate/9836dd43ebad7f76282c30207ce063d1?sig=%2FKFYc1TJHFOxxqMJy9nqHpQUCZAsxnCw7FcBrz%2BlQ6BileceSk%2FU%2BB0V1ygzvKzv&forceHttps=
         * buildHaveNewVersion : false
         * buildVersionNo : 3
         * buildVersion : 1.2
         * buildDescription : bob的扫码和更新测试
         * buildUpdateDescription : 首次发布
         * appKey : 5372e3bf7e1026b91aec511b9a4fc958
         * buildKey : 9836dd43ebad7f76282c30207ce063d1
         * buildName : 扫一扫
         * buildIcon : https://cdn-app-icon.pgyer.com/c/d/9/7/f/cd97ffc6fee4a3470bffd74f82518b5b?x-oss-process=image/resize,m_lfit,h_120,w_120/format,jpg
         * buildFileKey : 7d4824499beb28791b3818f206a40028.apk
         * buildFileSize : 8476983
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

