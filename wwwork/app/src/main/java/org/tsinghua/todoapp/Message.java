package org.tsinghua.todoapp;

public class Message {
    private String content;

    private String username;

    private String id;

    private String time;

    public Message(String content, String username, String id, String time) {
        this.content = content;
        this.username = username;
        this.time = time;
        this.id = id;
    }

    public String getContent() {
        return content;
    }
    public String getUsername() { return username; }
}
