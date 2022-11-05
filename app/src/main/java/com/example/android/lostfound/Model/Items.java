package com.example.android.lostfound.Model;

public class Items {
    private String bio,category,date,description,image,pid,time,phone;
    public Items(){

    }

    public Items(String bio, String category, String date, String description, String image, String pid, String time,String phone) {
        this.phone=phone;
        this.bio = bio;
        this.category = category;
        this.date = date;
        this.description = description;
        this.image = image;
        this.pid = pid;
        this.time = time;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
