package com.tohedul.myapplication;

public class User {
    private String name,email,address,phone;
    private int id;
    public User(int id,String name, String email, String address,String phone){
        this.setId(id);
        this.setName(name);
        this.setEmail(email);
        this.setAddress(address);
        this.setPhone(phone);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
