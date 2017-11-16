package com.cylinder.product.model;

import javax.persistence.*;

@Entity
@Table(name="categories", schema="product")
public class Categories {
  @Id
  @Column(name="category_id")
  protected Long categoryId;
  @Column
  protected String descriptor;

  public Long getCategoryId() {
    return this.categoryId;
  }

  public String getDescriptor() {
    return this.descriptor;
  }
}
