package com.doi.business.model;

public class RegistrationDTO {
    private int id;
    private String username;
    private String password;
    private String role;
    private  String full_name;
    private int afm;
    private String email;
    private String address;
    private String phone_num;
    private Form form;

    public RegistrationDTO(){
        super();
    }

    public RegistrationDTO(String username, String password, String role, String full_name, int afm, String email, String address, String phone_num) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.full_name = full_name;
        this.afm = afm;
        this.email = email;
        this.address = address;
        this.phone_num = phone_num;
    }

    // constructor to pass user data

    public RegistrationDTO(String full_name, int afm, String email, String address, String phone_num) {
        this.full_name = full_name;
        this.afm = afm;
        this.email = email;
        this.address = address;
        this.phone_num = phone_num;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public int getAfm() {
        return afm;
    }

    public void setAfm(int afm) {
        this.afm = afm;
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

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
