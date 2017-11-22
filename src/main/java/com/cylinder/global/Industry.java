package com.cylinder.global;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "industries", schema = "global")
public class Industry {

    @Id
    @Column(name = "industry_id")
    protected Long industryId;
    @Column(name = "industry_name")
    protected String industryName;

    public Industry() {
    }

    public Long getIndustryId() {
        return this.industryId;
    }

    public String getIndustryName() {
        return this.industryName;
    }

    public void setIndustryId(Long id) {
        this.industryId = id;
    }

    public void setIndustryName(String name) {
        this.industryName = name;
    }

    @Override
    public String toString() {
        return this.industryName;
    }


}
