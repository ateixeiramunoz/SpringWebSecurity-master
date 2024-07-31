package com.eoi.springwebsecurity.security.controllers;

import com.eoi.springwebsecurity.coreapp.entities.User;
import com.eoi.springwebsecurity.security.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import java.io.UnsupportedEncodingException;

/**
 * The type Forgot password controller.
 */
@Controller
public class ForgotPasswordController {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserService userService;

    /**
     * Show forgot password form string.
     *
     * @return the string
     */
    @GetMapping("/forgot_password")
    public String showForgotPasswordForm() {
        return "forgot_password_form";
    }

    /**
     * Process forgot password string.
     *
     * @param request the request
     * @param model   the model
     *
     * @return the string
     */
    @PostMapping("/forgot_password")
    public String processForgotPassword(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");
        String token  = RandomStringUtils.randomAlphanumeric(30);

        try {
            userService.updateResetPasswordToken(token, email);
            String resetPasswordLink =  "http://localhost:8080" + "/reset_password?token=" + token;
            sendEmail(email, resetPasswordLink);
            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");

        } catch (UsernameNotFoundException ex) {
            model.addAttribute("error", ex.getMessage());
        } catch (UnsupportedEncodingException | MessagingException e) {
            model.addAttribute("error", "Error while sending email");
        }

        return "forgot_password_form";
    }

    /**
     * Send email.
     *
     * @param recipientEmail the recipient email
     * @param link           the link
     *
     * @throws MessagingException           the messaging exception
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    public void sendEmail(String recipientEmail, String link)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("cuentadepruebaspringboot@gmail.com", "Soporte EOI");
        helper.setTo(recipientEmail);
        String subject = "Link para reseteo de Password";
        String content = "<p>Hola,</p>"
                + "<p>Has solicitado un reseteo de tu password.</p>"
                + "<p>Pulsa en el siguiente link para cambiarla :</p>"
                + "<p><a href=\"" + link + "\">Cambiar Password</a></p>"
                + "<br>"
                + "<p>Si no has solicitado un reseteo de password, ignora este mensaje.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }


    /**
     * Show reset password form string.
     *
     * @param token the token
     * @param model the model
     *
     * @return the string
     */
    @GetMapping("/reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model) {
        User user = userService.getByResetPasswordToken(token);
        model.addAttribute("token", token);

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        }

        return "reset_password_form";
    }

    /**
     * Process reset password string.
     *
     * @param request the request
     * @param model   the model
     *
     * @return the string
     */
    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        User user = userService.getByResetPasswordToken(token);
        model.addAttribute("title", "Reset your password");

        if (user == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        } else {
            userService.updatePassword(user, password);

            model.addAttribute("message", "You have successfully changed your password.");
        }

        return "message";
    }
}