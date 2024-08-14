package com.pryalkin.Task.exception.model;

public class UserDontExistException extends Exception{
    public UserDontExistException(String message) {
        super(message);
    }
}
