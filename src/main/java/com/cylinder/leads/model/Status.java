package com.cylinder.leads.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "statuses", schema = "lead")
public class Status {

    /**
     * The id that identifies a status.
     *
     * @param statusId the new status id value.
     * @return the id of the status.
     */
    @Getter
    @Setter
    @Id
    @Column(name = "status_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long statusId;

    /**
     * The descriptor or the name of the status of the lead.
     *
     * @param descriptor the new status descriptor value.
     * @return the status descriptor.
     */
    @Getter
    @Setter
    @Column
    protected String descriptor;
}
