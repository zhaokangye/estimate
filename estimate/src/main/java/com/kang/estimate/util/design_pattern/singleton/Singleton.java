package com.kang.estimate.util.design_pattern.singleton;

public class Singleton {

    private static volatile Singleton singleton;

    private Singleton(){};

    public Singleton getSingleton(){
        if(singleton==null){
            synchronized (Singleton.class){
                if(singleton==null){
                    singleton=new Singleton();
                }
            }
        }
        return singleton;
    }
}
