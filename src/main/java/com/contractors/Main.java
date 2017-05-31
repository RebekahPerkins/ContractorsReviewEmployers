package com.contractors;

import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;

public class Main {
    public static void main(String[] args) {
        get("/", (req, res) -> {
            Map<String, String> model = new HashMap<>();
            model.put("username", req.cookie("username"));
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());
        post("/sign-in", (req, res) -> {
            res.cookie("username", req.queryParams("username"));
            res.redirect("/");
            return null;
        });
        get("/employers", (req, res) -> null);
        post("/employers", (req, res) -> null);
        get("/employers/:slug", (req, res) -> null);
        post("/employers/:slug/vote", (req, res) -> null);
    }
}