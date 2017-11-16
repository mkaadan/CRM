package com.cylinder.leads.model;

import com.cylinder.global.Industry;
import java.util.List;

import javax.persistence.*;

import lombok.*;

@Entity
@Table(name="sources", schema="lead")
public class Source {

  /**
  * The id that identifies the source of the lead.
  *
  * @param sourceId the new source id value.
  * @return the id of the soruce.
  */
  @Getter
  @Setter
  @Id
  @Column(name="source_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected Long sourceId;

  /**
  * The descriptor or the name of the source of the lead.
  *
  * @param descriptor the new source descriptor value.
  * @return the source descriptor.
  */
  @Getter
  @Setter
  @Column
  protected String descriptor;
}
