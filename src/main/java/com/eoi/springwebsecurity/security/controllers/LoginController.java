package com.eoi.springwebsecurity.security.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * The type Login controller.
 */
@Controller
public class LoginController {


    /**
     * Login string.
     *
     * @return the string
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }


    /**
     * Login error string.
     *
     * @param model the model
     *
     * @return the string
     */
// Login form with error
    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }
}