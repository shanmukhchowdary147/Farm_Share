package com.example.farm_share.Model;

public class Users {
    private String name,phone,password,email;

    public Users()
    {

    }

    public Users(String name, String phone, String password, String email) {
        this.name = name;
        this.phone = phone;
        this.email=email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
