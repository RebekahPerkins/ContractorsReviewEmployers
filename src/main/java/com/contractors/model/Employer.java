package com.contractors.model;

import java.util.HashSet;
import com.github.slugify.Slugify;
import java.util.Set;

public class Employer {

    private String createdBy;
    private String employerName;
    private String slug;
    private Set<String> voters;

    public Employer(String createdBy, String employerName) {
        this.createdBy = createdBy;
        this.employerName = employerName;
        Slugify slugify = new Slugify();
        slug = slugify.slugify(employerName);
        voters = new HashSet<>();
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

    public boolean addVoter(String voter){
        return voters.add(voter);
    }

    public int getVoteCount(){
        return voters.size();
    }

    public String getEmployerName() {
        return employerName;
    }
}
