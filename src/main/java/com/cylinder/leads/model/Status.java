package com.cylinder.leads;

import javax.persistence.*;

@Entity
@Table(name="statuses", schema="lead")
public class Status {
  @Id
  @Column(name="status_id")
  protected Long statusId;
  @Column
  protected String descriptor;
}
