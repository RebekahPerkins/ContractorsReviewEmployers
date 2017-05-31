package com.contractors.model;

import java.util.Set;

public class Employer {

    private String createdBy;
    private String employerName;
    private String slug;
    private Set<String> voters;

    public Employer(String createdBy, String employerName) {
        this.createdBy = createdBy;
        this.employerName = employerName;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getSlug() {
        return slug;
    }

    public Set<String> getVoters() {
        return voters;
    }

    public String getEmployerName() {
        return employerName;
    }
}
