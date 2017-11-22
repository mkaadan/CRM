package com.cylinder.accounts.model;


import com.cylinder.shared.model.SimpleEmail;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Ehtasham Munib
 * The table mapping for an account's email .
 */
@Entity
@Table(name = "emails", schema = "account")
public class Email extends SimpleEmail {
    public Email() {
    }
}
