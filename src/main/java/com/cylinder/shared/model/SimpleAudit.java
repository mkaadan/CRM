package com.cylinder.shared.model;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;

import com.cylinder.crmusers.model.CrmUser;

import lombok.*;

@MappedSuperclass
public abstract class SimpleAudit {
  /**
  * Get the user that last created this lead.
  *
  * @param createdBy the creator of the lead.
  * @return the creator of the lead.
  */
  @Getter
  @Setter
  @JsonIgnore
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name="created_by", referencedColumnName = "account_id", updatable=false)
  protected CrmUser createdBy;

  /**
  * Get the user that last modified this lead.
  *
  * @param owner the new owner of the lead.
  * @return the owner of the lead.
  */
  @Getter
  @Setter
  @JsonIgnore
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name="last_modified_by_id", referencedColumnName = "account_id")
  protected CrmUser lastModifiedBy;

  /**
  * The time that this record was created.
  *
  * @return the timestamp of when this lead was added.
  */
  @Getter
  @JsonIgnore
  @CreationTimestamp
  @Column(updatable=false)
  protected Timestamp created;

  /**
  * The time that this record was last modified.
  *
  * @return the timestamp of when this lead was last modified.
  */
  @Getter
  @JsonIgnore
  @UpdateTimestamp
  @Column(name = "last_modified")
  protected Timestamp lastModified;
}
