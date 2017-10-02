package com.cylinder.leads;

class Lead {

    /** The full name of the lead. */
    public String name;
    /** The name of the company where the lead represents. */
    public String company;
    /** The email of the lead. */
    public String email;
    /** Where did the lead originate from? A Trade show? Cold Call? */
    public String leadSource;
    /** Whose is responible for this lead? */
    public String leadOwner;

    /**
    * Construct a new instance of a lead.
    * @param name the full name of the lead.
    * @param company the name of the company where the lead represents.
    * @param email the email of the lead.
    * @param leadSource where did the lead originate from? A Trade show? Cold Call?
    * @param leadOwner whose is responible for this lead?
    */
    public Lead(String name,
                String company,
                String email,
                String leadSource,
                String leadOwner) {
        this.name = name;
        this.company = company;
        this.email = email;
        this.leadSource = leadSource;
        this.leadOwner = leadOwner;
    }

    /**
    * Returns the full name of the lead.
    * @return the name of the lead.
    */
    public String getName() {
      return this.name;
    }

    /**
    * Returns the full name of the lead.
    * @return the name of the lead.
    */
    public String getCompany() {
      return this.company;
    }

    /**
    * Returns the full name of the lead.
    * @return the name of the lead.
    */
    public String getEmail() {
      return this.email;
    }

    /**
    * Returns the full name of the lead.
    * @return the name of the lead.
    */
    public String getLeadSource() {
      return this.leadSource;
    }

    /**
    * Returns the full name of the lead.
    * @return the name of the lead.
    */
    public String getLeadOwner() {
      return this.leadOwner;
    }
}
