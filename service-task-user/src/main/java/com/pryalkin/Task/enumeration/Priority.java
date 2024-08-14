package com.pryalkin.Task.enumeration;

public enum Priority {

    HIGH("ВЫСОКИЙ"),
    AVERAGE("СРЕДНИЙ"),
    SHORT("НИЗКИЙ");

    private String priority;

    Priority(String priority) {
        this.priority = priority;
    }

    public String getPriority() {
        return priority;
    }


}
