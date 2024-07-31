package com.eoi.springwebsecurity.coreapp.controllers;

import com.eoi.springwebsecurity.coreapp.dto.UserDto;
import com.eoi.springwebsecurity.coreapp.services.DefaultService;
import com.eoi.springwebsecurity.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.thymeleaf.extras.springsecurity6.auth.Authorization;


import java.security.Principal;
import java.util.List;

/**
 * The type Main controller.
 */
@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private DefaultService defaultService;


    /**
     * Index string.
     *
     * @param principal the principal
     *
     * @return the string
     */
    @GetMapping("/")
    String index(Principal principal) {
        return principal != null ? "home/homeSignedIn" : "home/homeNotSignedIn";
    }

    /**
     * Show admin page string.
     *
     * @return the string
     */
    @GetMapping("/admin")
    public String showAdminPage() {
        return "admin";
    }

    /**
     * Show user page string.
     *
     * @return the string
     */
    @GetMapping("/user")
    public String showUserPage() {
        return "user";
    }

    /**
     * Users string.
     *
     * @param model the model
     *
     * @return the string
     */
    @GetMapping("/users")
    public String users(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    /**
     * Chat string.
     *
     * @param principal the principal
     * @param model     the model
     *
     * @return the string
     */
    @GetMapping("/chat")
    public String chat(Principal principal, Model model){

        String userID = principal.getName();
        model.addAttribute("userID", userID);

        return "chat";
    }


    /**
     * Test string.
     *
     * @param id             the id
     * @param principal      the principal
     * @param model          the model
     * @param authentication the authentication
     *
     * @return the string
     */
//Queremos que a esta pagina solo accedan los usuarios cuando el ID Es el de su propio usuario
    @GetMapping("/test/{id}")
    @PreAuthorize("authentication.principal.userID == #id")
    public String test(@PathVariable("id") Integer id, Principal principal, Model model, Authentication authentication){

        String userID = principal.getName();
        model.addAttribute("user", userService.findUserByEmail(userID));
        model.addAttribute("userID", userID);
        return "test";
    }


    /**
     * Test string.
     *
     * @param principal     the principal
     * @param authorization the authorization
     * @param model         the model
     *
     * @return the string
     */
    @GetMapping("/security")
    public String test(Principal principal, Authorization authorization,Model model){
        model.addAttribute("principal", principal);
        model.addAttribute("authorization", authorization);
        return "security";
    }


    /**
     * Estructura string.
     *
     * @return the string
     */
    @GetMapping("/estructura")
    public String estructura(){
        return "estructura";
    }













}
