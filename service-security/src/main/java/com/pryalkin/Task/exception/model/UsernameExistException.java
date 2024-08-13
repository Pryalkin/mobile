package com.pryalkin.Task.exception.model;

public class UsernameExistException extends Exception{
    public UsernameExistException(String message) {
        super(message);
    }
}
