package com.kang.estimate.module.management.entity;

/**
 * @author 叶兆康
 */
public class PageParam {

    private Integer pn=0;

    private Integer size=10;

    public Integer getPn() {
        return pn;
    }

    public void setPn(Integer pn) {
        this.pn = pn;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
