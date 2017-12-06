package com.cylinder.deals.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Ryan Piper
 * Manges deal's type that is a new deal or is it a existing deal been going on
 * before it entry.
 */
@Entity
@Table(name = "types", schema = "deal")
public class Type {

    /**
     * The deal's type descriptor.
     *
     * @param descriptor the plain english value of the deal type.
     * @return the deal's type descriptor.
     */
    @Getter
    @Setter
    @Column
    protected String descriptor;
    /**
     * The identifier for a deal's type.
     *
     * @param typeId the new type id value.
     * @return the id of the deal's type.
     */
    @Getter
    @Setter
    @Id
    @Column(name = "type_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long typeId;
}
