package com.pryalkin.Task.enumeration;

public enum Status {

    IN_WAITING("В ожидании"),
    IN_PROGRESS("В процессе"),
    COMPLETED("Завершено");

    private String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }


}
