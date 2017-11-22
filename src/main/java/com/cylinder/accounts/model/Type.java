package com.cylinder.accounts.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Ehtasham Munib
 * The table mapping for an account's type .
 */
@Entity
@Table(name="type", schema="account")
public class Type {
    @Getter
    @Setter
    @Id
    @Column(name="type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long typeId;

    @Getter
    @Setter
    @Column
    protected String descriptor;
}
