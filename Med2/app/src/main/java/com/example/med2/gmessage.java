package com.example.med2;

public class gmessage extends message{
    private String sender;
    public gmessage(String message_text, String sender) {
        super(message_text);
        //message(this.message_text);
        this.sender = sender;
    }
    public String getsender() {
        return sender;
    }

    public void setsender(String sender) {
        this.sender = sender;
    }
}
