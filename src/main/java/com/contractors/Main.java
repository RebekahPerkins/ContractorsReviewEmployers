package com.contractors;

import com.contractors.model.Employer;
import com.contractors.model.EmployersDao;
import com.contractors.model.EmployersDaoImpl;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        staticFileLocation("/public");

        EmployersDao dao = new EmployersDaoImpl();

        before((req, res) -> {
            if (req.cookie("username") != null) {
                req.attribute("username", req.cookie("username"));
            }
        });

        before("/employers", (req, res) -> {
            if (req.attribute("username") == null) {
                res.redirect("/");
                halt();
            }
        });
        get("/", (req, res) -> {
            Map<String, String> model = new HashMap<>();
            model.put("username", req.attribute("username"));
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());
        post("/sign-in", (req, res) -> {
            res.cookie("username", req.queryParams("username"));
            res.redirect("/");
            return null;
        });
        get("/employers", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("employers", dao.findAll());
            return new ModelAndView(model, "employers.hbs");
        }, new HandlebarsTemplateEngine());
        post("/employers", (req, res) -> {
            String employerName = req.queryParams("employerName");
            String createdBy = req.attribute("username");
            Employer employer = new Employer(createdBy, employerName);
            dao.add(employer);
            res.redirect("/employers");
            return null;
        });
        get("/employers/:slug", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("employer", dao.findBySlug(req.params("slug")));
            return new ModelAndView(model, "employer.hbs");
        }, new HandlebarsTemplateEngine());
        post("/employers/:slug/vote", (req, res) -> null);
    }
}