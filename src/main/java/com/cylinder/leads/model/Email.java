package com.cylinder.leads.model;


import com.cylinder.shared.model.SimpleEmail;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Ryan Piper
 * The table mapping for a lead's email .
 */
@Entity
@Table(name = "emails", schema = "lead")
public class Email extends SimpleEmail {
    public Email() {
    }
}
