package com.example.Reyada.crm.tasks.data;


public enum TaskStatus {
    STATE_NEW(1),
    STATE_PENDING(2),
    STATE_IN_PROGRESS(3),
    STATE_SUPPOSEDLY_COMPLETED(4),
    STATE_COMPLETED(5),
    STATE_DEFERRED(6),
    STATE_DECLINED(7);

    private final int code;

    TaskStatus(int code) { this.code = code; }

    public int getCode() { return code; }

    public static TaskStatus fromCode(int code) {
        for (TaskStatus s : values()) {
            if (s.code == code) return s;
        }
        throw new IllegalArgumentException("Unknown TaskStatus code: " + code);
    }
}

