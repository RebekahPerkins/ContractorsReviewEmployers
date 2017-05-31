package com.contractors.model;

import java.util.ArrayList;
import java.util.List;

public class EmployersDaoImpl implements EmployersDao {
    private List<Employer> employers;

    public EmployersDaoImpl() {
        employers = new ArrayList<>();
    }

    @Override
    public List<Employer> findAll() {
        return new ArrayList<>(employers);
    }

    @Override
    public void add(Employer employer) {
        employers.add(employer);
    }
}
