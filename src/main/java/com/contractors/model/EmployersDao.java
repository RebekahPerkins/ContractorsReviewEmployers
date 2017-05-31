package com.contractors.model;

import java.util.List;

public interface EmployersDao {
    List<Employer> findAll();

    Employer findBySlug(String slug);

    void add(Employer employer);
}
