package com.cylinder.leads.model;

import javax.persistence.*;

@Entity
@Table(name="statuses", schema="lead")
public class Status {
  @Id
  @Column(name="status_id")
  protected Long statusId;
  @Column
  protected String descriptor;

  public Long getStatusId() {
    return this.statusId;
  }

  public String getDescriptor() {
    return this.descriptor;
  }
}
