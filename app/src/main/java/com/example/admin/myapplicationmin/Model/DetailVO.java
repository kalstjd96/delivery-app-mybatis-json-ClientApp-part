package com.example.admin.myapplicationmin.Model;

import java.io.Serializable;

public class DetailVO implements Serializable {
    String id;
    String pwd;
    String numbers;
    String name;
    String address;
    String phone;
    String idx;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    @Override
    public String toString() {
        return "ListVO{" +
                "id='" + id + '\'' +
                ", pwd='" + pwd + '\'' +
                ", numbers='" + numbers + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", idx='" + idx + '\'' +
                '}';
    }
}
