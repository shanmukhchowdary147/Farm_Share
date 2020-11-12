package com.example.farm_share;

public class Equipments {
    private String title,days,cost,additionalInfo,contactNumber;

    public Equipments() {
    }

    public Equipments(String title, String days, String cost, String additionalInfo, String contactNumber) {
        this.title = title;
        this.days = days;
        this.cost = cost;
        this.additionalInfo = additionalInfo;
        this.contactNumber = contactNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = "Available Days :- "+days;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = "Cost Per Day :- "+cost;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = "AdditionalInfo :- "+additionalInfo;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = "Contact Number :- "+contactNumber;
    }
}
