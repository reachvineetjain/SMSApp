package com.nehvin.smsapp;

/**
 * Created by Neha on 8/29/2017.
 */

public class TextMessage {

    private String senderDetails;
    private String message;
    private String datetime;
    private String flag;

    public TextMessage(String senderDetails, String message, String datetime, String flag) {
        this.senderDetails = senderDetails;
        this.message = message;
        this.datetime = datetime;
        this.flag = flag;
    }

    public String getSenderDetails() {
        return senderDetails;
    }

    public void setSenderDetails(String senderDetails) {
        this.senderDetails = senderDetails;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}