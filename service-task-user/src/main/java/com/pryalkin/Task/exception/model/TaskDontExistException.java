package com.pryalkin.Task.exception.model;

public class TaskDontExistException extends Exception{

    public TaskDontExistException(String message) {
        super(message);
    }

}
