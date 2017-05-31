package com.contractors;

import com.contractors.model.Employer;
import com.contractors.model.EmployersDao;
import com.contractors.model.EmployersDaoImpl;
import com.contractors.model.NotFoundException;
import spark.ModelAndView;
import spark.Request;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class Main {

    private static final String FLASH_MESSAGE_KEY = "flash_message";

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
                setFlashMessage(req, "Please sign in. Redirecting to login...");
                res.redirect("/");
                halt();
            }
        });

        get("/", (req, res) -> {
            Map<String, String> model = new HashMap<>();
            model.put("flashMessage", captureFlashMessage(req));
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
            model.put("flashMessage", captureFlashMessage(req));
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
        post("/employers/:slug/vote", (req, res) -> {
            Employer employer = dao.findBySlug(req.params("slug"));
            boolean success = employer.addVoter(req.attribute("username"));
            String flashMessage = success ? "Voted successfully" : "Could not add vote";
            setFlashMessage(req, flashMessage);
            res.redirect("/employers");
            return null;
        });

        exception(NotFoundException.class, (exc, req, res) -> {
            res.status(404);
            HandlebarsTemplateEngine engine = new HandlebarsTemplateEngine();
            String html = engine.render(
                    new ModelAndView(null, "not-found.hbs"));
            res.body(html);
        });
    }

    private static void setFlashMessage(Request req, String message) {
        req.session().attribute(FLASH_MESSAGE_KEY, message);
    }

    private static String getFlashMessage(Request req) {
        if (req.session(false) == null) {
            return null;
        }
        if (!req.session().attributes().contains(FLASH_MESSAGE_KEY)) {
            return null;
        }
        return (String) req.session().attribute(FLASH_MESSAGE_KEY);
    }

    private static String captureFlashMessage(Request req) {
        String message = getFlashMessage(req);
        if (message != null) {
            req.session().removeAttribute(FLASH_MESSAGE_KEY);
        }
        return message;
    }
}