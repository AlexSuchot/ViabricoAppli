package com.speleize.alexl.viabrico;

public class Fournisseur {
    private String name;
    private String description;
    private String address;
    private Integer phone;
    private String email;
    private Integer id;

    public Fournisseur(String name, String description, String address, Integer phone, String email, Integer id) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        address = address;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        phone = phone;
    }

    public String getMail() {
        return email;
    }

    public void setMail(String email) {
        email = email;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
