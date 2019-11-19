package com.kang.estimate.util.design_pattern.observer;


public interface ObjectAction {
    void add(Observer observer);

    void delete(Observer observer);

    void notify(String message);
}
