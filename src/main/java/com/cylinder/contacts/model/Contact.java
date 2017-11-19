package com.cylinder.contacts.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import javax.validation.constraints.*;

@MappedSuperclass
abstract public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    protected Long personId;
    @Column(name = "title_id")
    protected Long titleId;
    @Column(name = "company_id")
    protected Long companyId;
    @Column(name = "phone_id")
    protected Long phoneId;
    @Column(name = "address_id")
    protected Long addressId;
    @Column(name = "comments_id")
    protected Long commentsId;
    @Column(name = "contacts_id")
    protected Long contactsId;
    @Column(name = "user_id")
    protected Long userId;

    public Long getPersonId() {
        return this.personId;
    }

    public Long getTitleId() {
        return this.titleId;
    }

    public Long getCompanyId() {
        return this.companyId;
    }

    public Long getPhoneId() {
        return this.phoneId;
    }

    public Long getAddressId() {
        return this.addressId;
    }

    public Long getCommentsId() {
        return this.commentsId;
    }

    public Long getContactsId() {
        return this.contactsId;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public void setTitleId(Long titleId) {
        this.titleId = titleId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public void setPhoneId(Long phoneId) {
        this.phoneId = phoneId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public void setCommentsId(Long commentsId) {
        this.commentsId = commentsId;
    }

    public void setContactsId(Long contactsId) {
        this.contactsId = contactsId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }




}
