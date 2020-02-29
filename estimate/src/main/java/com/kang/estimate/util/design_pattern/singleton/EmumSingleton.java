package com.kang.estimate.util.design_pattern.singleton;

public enum  EmumSingleton {
    // 实例
    INSTANCE;

    private Object data;

    public void set(Object data){
        this.data=data;
    }
    public Object get(){
        return this.data;
    }
    public static EmumSingleton getInstance(){
        return INSTANCE;
    }
}
