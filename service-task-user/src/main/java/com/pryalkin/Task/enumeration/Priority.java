package com.pryalkin.Task.enumeration;

public enum Priority {

    HIGH("Высокий"),
    AVERAGE("Средний"),
    SHORT("Низкий");

    private String priority;

    Priority(String priority) {
        this.priority = priority;
    }

    public String getPriority() {
        return priority;
    }


}
