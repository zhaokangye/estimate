package com.kang.estimate.util.design_pattern.observer;

public class ObserverMk1 implements Observer {
    @Override
    public void update(String message) {
        System.out.println("ObserverMk1:"+message);
    }
}
