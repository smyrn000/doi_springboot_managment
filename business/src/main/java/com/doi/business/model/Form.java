package com.doi.business.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "form")
public class Form {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "purpose")
    private String purpose;

    @Column(name = "katastatiko")
    private String katastatiko;

    @Column(name = "HQ")
    private String HQ;
    @Column(name = "members")
    private String members;
    @Column(name = "entry_date")
    private Date entry_date;

    @Column(name = "status")
    private String status;
    @OneToOne
    @JoinColumn(name = "user_id",unique = true)
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getKatastatiko() {
        return katastatiko;
    }

    public void setKatastatiko(String katastatiko) {
        this.katastatiko = katastatiko;
    }

    public String getHQ() {
        return HQ;
    }

    public void setHQ(String HQ) {
        this.HQ = HQ;
    }

    public Date getEntry_date() {
        return entry_date;
    }

    public void setEntry_date(Date entry_date) {
        this.entry_date = entry_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMembers() {
        return members;
    }
    public void setMembers(String members) {
        this.members = members;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
