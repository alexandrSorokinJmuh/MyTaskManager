package com.taskManger.exception;

public class UUIDIsNotUniqueException extends Exception{
    public UUIDIsNotUniqueException() {
        super();
    }

    public UUIDIsNotUniqueException(String message) {
        super(message);
    }
}
