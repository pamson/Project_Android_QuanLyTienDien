package com.example.project.modle;


import java.io.Serializable;

public class User implements Serializable
{
    private String userID, passWord, Name, Role;

    public User() {
    }

    public User(String userID, String passWord, String name, String role) {
        this.userID = userID;
        this.passWord = passWord;
        Name = name;
        Role = role;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }
}
