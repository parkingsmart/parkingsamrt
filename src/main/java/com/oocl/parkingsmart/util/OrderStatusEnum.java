package com.oocl.parkingsmart.util;

public enum OrderStatusEnum {

    NO_HANDLE(0,"无人处理"),
    SAVE_AND_FETCH(1,"存取中"),
    ACCOMPLISH(2,"已完成");

    private int status;
    private String statusInfo;

    OrderStatusEnum(int status, String statusInfo) {
        this.status = status;
        this.statusInfo = statusInfo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public String getStatusInfo() {
        return statusInfo;
    }

    public void setStatusInfo(String statusInfo) {
        this.statusInfo = statusInfo;
    }
}
