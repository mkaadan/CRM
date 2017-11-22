package com.cylinder.shared.model;

import org.hibernate.validator.constraints.Email;

import javax.persistence.*;

@MappedSuperclass
abstract public class SimpleEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email_id")
    protected Long emailId;
    @Column(name = "email")
    @Email
    protected String email;

    public Long getEmailId() {
        return this.emailId;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmailId(Long id) {
        this.emailId = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
