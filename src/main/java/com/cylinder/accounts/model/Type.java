package com.cylinder.accounts.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="type", schema="account")
public class Type {
    @Getter
    @Setter
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
