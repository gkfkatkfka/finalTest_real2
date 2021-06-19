package com.inhatc.finaltest;

import java.util.HashMap;
import java.util.Map;

public class User {
    String email;
    String password;
    String name;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    // 값을 추가할 때 쓰임
    public User(String email, String password, String name){
        this.email=email;
        this.password=password;
        this.name=name;
    }

}
