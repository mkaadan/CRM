package com.cylinder.contacts.model;

import javax.persistence.*;
import com.cylinder.shared.model.SimpleEmail;

/**
* @author Ryan Piper
* The contact's emails. 
*/
@Entity
@Table(name="emails", schema="contact")
public class Email extends SimpleEmail {
    public Email(){}
}
