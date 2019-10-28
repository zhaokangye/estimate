package com.kang.estimate.entity;

public class CPU {
    private String time;
    private String user;
    private String nice;
    private String system;
    private String iowait;
    private String steal;
    private String idle;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getNice() {
        return nice;
    }

    public void setNice(String nice) {
        this.nice = nice;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getIowait() {
        return iowait;
    }

    public void setIowait(String iowait) {
        this.iowait = iowait;
    }

    public String getSteal() {
        return steal;
    }

    public void setSteal(String steal) {
        this.steal = steal;
    }

    public String getIdle() {
        return idle;
    }

    public void setIdle(String idle) {
        this.idle = idle;
    }
}
