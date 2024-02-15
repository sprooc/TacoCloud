package org.example.tacocloud.controller;

import org.example.tacocloud.config.EmailProperties;
import org.example.tacocloud.va.FileWriterGateway;
import org.example.tacocloud.va.UpperCaseGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @Autowired
    private FileWriterGateway fileWriterGateway;
    @Autowired
    private UpperCaseGateway upperCaseGateway;
    @Autowired
    private EmailProperties emailProperties;
    @GetMapping("/")
    public String home() {
        fileWriterGateway.writeToFile("log","in home");
        System.out.println(upperCaseGateway.upperCase("wayway"));
        System.out.println(emailProperties.getImapUrl());
        return "home";
    }

    @GetMapping("/admin")
    public String showAdmin() {
        return "admin";
    }
}
