package com.contractors.model;

import java.util.List;

public interface EmployersDao {
    public List<Employer> findAll();

    void add(Employer employer);
}
