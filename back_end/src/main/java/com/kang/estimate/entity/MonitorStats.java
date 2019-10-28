package com.kang.estimate.entity;

public class MonitorStats {
    private String hostURL;
    private String codeName;
    private String time;
    private String userUsage;
    private String niceUsage;
    private String systemUsage;
    private String iowaitUsage;
    private String stealUsage;
    private String idleUsage;

    public String getHostURL() {
        return hostURL;
    }

    public void setHostURL(String hostURL) {
        this.hostURL = hostURL;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserUsage() {
        return userUsage;
    }

    public void setUserUsage(String userUsage) {
        this.userUsage = userUsage;
    }

    public String getNiceUsage() {
        return niceUsage;
    }

    public void setNiceUsage(String niceUsage) {
        this.niceUsage = niceUsage;
    }

    public String getSystemUsage() {
        return systemUsage;
    }

    public void setSystemUsage(String systemUsage) {
        this.systemUsage = systemUsage;
    }

    public String getIowaitUsage() {
        return iowaitUsage;
    }

    public void setIowaitUsage(String iowaitUsage) {
        this.iowaitUsage = iowaitUsage;
    }

    public String getStealUsage() {
        return stealUsage;
    }

    public void setStealUsage(String stealUsage) {
        this.stealUsage = stealUsage;
    }

    public String getIdleUsage() {
        return idleUsage;
    }

    public void setIdleUsage(String idleUsage) {
        this.idleUsage = idleUsage;
    }
}
