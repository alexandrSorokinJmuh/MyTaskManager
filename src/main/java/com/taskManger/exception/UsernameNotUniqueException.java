package com.taskManger.exception;

public class UsernameNotUniqueException extends Exception{
    public UsernameNotUniqueException() {
        super();
    }

    public UsernameNotUniqueException(String message) {
        super(message);
    }
}
