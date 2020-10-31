package com.example.farm_share.Model;

public class Users {
    private String name,phone,passwod;

    public Users()
    {

    }

    public Users(String name, String phone, String passwod) {
        this.name = name;
        this.phone = phone;
        this.passwod = passwod;
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

    public String getPasswod() {
        return passwod;
    }

    public void setPasswod(String passwod) {
        this.passwod = passwod;
    }

}
