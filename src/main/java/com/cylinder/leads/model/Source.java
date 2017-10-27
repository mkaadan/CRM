package com.cylinder.leads;

import com.cylinder.global.Industry;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="sources", schema="lead")
public class Source {
  @Id
  @Column(name="source_id")
  protected Long sourceId;
  @Column
  protected String descriptor;
}
