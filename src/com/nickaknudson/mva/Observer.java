package com.nickaknudson.mva;

public interface Observer<T extends Observable> {

    void update(T observable, Object data);
}
