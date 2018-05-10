package com.example.administrator.threelistdemo.model;

public class SelectTimeAndRole {
    private String time;
    private String role;
    private String state;

    public SelectTimeAndRole(String time, String role, String state) {
        this.time = time;
        this.role = role;
        this.state = state;
    }public SelectTimeAndRole() {

    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "SelectTimeAndRole{" +
                "time='" + time + '\'' +
                ", role='" + role + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
