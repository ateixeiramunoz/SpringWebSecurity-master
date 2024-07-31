package com.eoi.springwebsecurity.security.controllers;

import com.eoi.springwebsecurity.coreapp.dto.UserDto;
import com.eoi.springwebsecurity.coreapp.entities.User;
import com.eoi.springwebsecurity.security.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * The type Register controller.
 */
@Controller
public class RegisterController {


    @Autowired
    private UserService userService;


    /**
     * Home string.
     *
     * @return the string
     */
// handler method to handle home page request
    @GetMapping("/index")
    public String home(){
        return "index";
    }

    /**
     * Show registration form string.
     *
     * @param model the model
     *
     * @return the string
     */
// handler method to handle user registration form request
    @GetMapping(value={"/register","/signup"})
    public String showRegistrationForm(Model model){
        // create model object to store form data
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    /**
     * Registration string.
     *
     * @param userDto the user dto
     * @param result  the result
     * @param model   the model
     *
     * @return the string
     */
// handler method to handle user registration form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model){
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "/register";
        }

        userService.saveUser(userDto);
        return "redirect:/register?success";
    }

}