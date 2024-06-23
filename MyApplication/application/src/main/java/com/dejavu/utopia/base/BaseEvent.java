package com.dejavu.utopia.base;

// 事件传递基类
public class BaseEvent<T> {
    private T message;

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }
}