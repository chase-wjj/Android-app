package org.tsinghua.todoapp;

public class Friend {
    private String name;
    private String phoneNumber;

    private int picture;

    public Friend(String name, String phoneNumber, int picture) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int picture() {return picture;}
}
