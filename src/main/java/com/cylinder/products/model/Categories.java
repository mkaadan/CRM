package com.cylinder.products.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "categories", schema = "product")
public class Categories {
    @Id
    @Column(name = "category_id")
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
