package com.contractors.model;

import java.util.ArrayList;
import java.util.List;

public class EmployersDaoImpl implements EmployersDao {
//TODO This is not a real implementation, just stores in memory
    private List<Employer> employers;

    public EmployersDaoImpl() {
        employers = new ArrayList<>();
    }

    @Override
    public List<Employer> findAll() {
        return new ArrayList<>(employers);
    }

    @Override
    public Employer findBySlug(String slug) {
        return employers.stream()
                .filter(idea -> idea.getSlug().equals(slug))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void add(Employer employer) {
        employers.add(employer);
    }
}
