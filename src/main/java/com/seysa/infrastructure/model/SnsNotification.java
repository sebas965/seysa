package com.seysa.infrastructure.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class SnsNotification {

    @SerializedName("Type")
    private String type;
    @SerializedName("MessageId")
    private String messageId;
    @SerializedName("TopicArn")
    private String topicArn;
    @SerializedName("Message")
    private String message;
    @SerializedName("Timestamp")
    private Date timestamp;

    public SnsNotification() {
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessageId() {
        return this.messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getTopicArn() {
        return this.topicArn;
    }

    public void setTopicArn(String topicArn) {
        this.topicArn = topicArn;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return new Date(this.timestamp.getTime());
    }

    public String toString() {
        return "SNSNotificationMessage [type=" + this.type + ", messageId=" + this.messageId + ", topicArn=" + this.topicArn + ", message=" + this.message + ", timestamp=" + this.timestamp + "]";
    }
}