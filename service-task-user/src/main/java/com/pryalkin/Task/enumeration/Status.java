package com.pryalkin.Task.enumeration;

public enum Status {

    IN_WAITING("В ОЖИДАНИИ"),
    IN_PROGRESS("В ПРОЦЕССЕ"),
    COMPLETED("ЗАВЕРШЕНО");

    private String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }


}
