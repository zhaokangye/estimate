package com.kang.estimate.util.design_pattern.observer;

import java.util.ArrayList;
import java.util.List;

public class MainObject implements ObjectAction {

    List<Observer> observers=new ArrayList<>();

    @Override
    public void add(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void delete(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notify(String message) {
        for(Observer observer:observers){
            observer.update(message);
        }
    }

    public static void main(String[] args) {
        MainObject mainObject=new MainObject();
        Observer mk1=new ObserverMk1();
        Observer mk2=new ObserverMk2();
        mainObject.add(mk1);
        mainObject.add(mk2);
        mainObject.notify("this is a message");
    }
}
