package com.cylinder.accounts.model;


import javax.persistence.*;

@Entity
@Table(name="type", schema="account")
public class Type {
    @Id
    @Column(name="type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long typdId;
    @Column
    protected String descriptor;

    public Long getTypeId() {
        return this.typdId;
    }

    public String getDescriptor() {
        return this.descriptor;
    }
}
