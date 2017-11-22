package com.cylinder.contacts.model;

import com.cylinder.shared.model.SimpleEmail;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Ryan Piper
 * The contact's emails.
 */
@Entity
@Table(name = "emails", schema = "contact")
public class Email extends SimpleEmail {
    public Email() {
    }
}
