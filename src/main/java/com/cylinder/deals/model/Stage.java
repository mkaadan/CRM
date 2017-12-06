package com.cylinder.deals.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Ryan Piper
 * Manage the stage of the deal, that is, was the deal won or lost and so forth.
 */
@Entity
@Table(name = "stages", schema = "deal")
public class Stage {

    /**
     * The stage descriptor.
     *
     * @param descriptor the plain english value of the stage.
     * @return the deal's stage descriptor.
     */
    @Getter
    @Setter
    @Column
    protected String descriptor;
    /**
     * The identifier for a deal's stage.
     *
     * @param stageId the new lead id value.
     * @return the id of the deal's stage.
     */
    @Getter
    @Setter
    @Id
    @Column(name = "stage_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stageId;

}
