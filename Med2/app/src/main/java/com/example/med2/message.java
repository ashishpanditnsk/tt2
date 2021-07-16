package com.example.med2;

public class message {
    private String message_text;
    private String sender;
    private Boolean sent_flag;
    private String group_flag;
    private String title;
    public message(String message_text) {
        this.message_text = message_text;
    }
    public message(String message_text,String sender) { this.message_text = message_text;this.sender = sender; }
    public message(String message_text,String sender, Boolean sent_flag) { this.message_text = message_text;this.sender = sender;this.sent_flag=sent_flag;}
    public message(String message_text,String sender, Boolean sent_flag, String group_flag, String title) { this.message_text = message_text;this.sender = sender;this.sent_flag=sent_flag;this.group_flag=group_flag;this.title=title;}


    public String getMessage_text() {
        return message_text;
    }
    public String getTitle() {
        return title;
    }

    public void setMessage_text(String message_text) {
        this.message_text = message_text;
    }
    public String getsender() {
        return sender;
    }
    public Boolean getsent_flag() {
        return sent_flag;
    }
    public String getgroup_flag() {
        return group_flag;
    }

    public void setsender(String sender) {
        this.sender = sender;
    }


    public message() {}
}
