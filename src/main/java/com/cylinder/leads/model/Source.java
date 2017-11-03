package com.cylinder.leads.model;

import com.cylinder.global.Industry;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="source", schema="lead")
public class Source {
  @Id
  @Column(name="source_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long sourceId;
  @Column
  protected String descriptor;

  public Long getSourceId() {
    return this.sourceId;
  }

  public String getDescriptor() {
    return this.descriptor;
  }
}
