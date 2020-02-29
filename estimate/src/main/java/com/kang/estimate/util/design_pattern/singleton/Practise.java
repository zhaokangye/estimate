package com.kang.estimate.util.design_pattern.singleton;

public class Practise {
    private volatile Practise uniqueInstance;
    private Practise(){};
    public Practise getUniqueInstance(){
        if(uniqueInstance==null){
            synchronized (Practise.class){
                if(uniqueInstance==null){
                    uniqueInstance=new Practise();
                }
            }
        }
        return uniqueInstance;
    }
}
