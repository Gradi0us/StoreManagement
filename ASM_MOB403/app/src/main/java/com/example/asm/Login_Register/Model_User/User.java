package com.example.asm.Login_Register.Model_User;

public class User {
    private String id;
    private String username;
    private String password;
    private String quests;

    public User(String id, String username, String password, String quests) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.quests = quests;
    }

    public String getId() {
        return id;
    }

    public User() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getQuests() {
        return quests;
    }

    public void setQuests(String quests) {
        this.quests = quests;
    }
}
