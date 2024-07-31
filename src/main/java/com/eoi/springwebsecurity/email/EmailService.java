package com.eoi.springwebsecurity.email;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * The type Email service.
 */
@Service
@Log4j2
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

/*
    public void sendEmail(String recipientEmail, String link, String cuerpoMensaje)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("cuentadepruebaspringboot@gmail.com", "Soporte EOI");
        helper.setTo(recipientEmail);
        String subject = "Tienes un aviso";
        String content = "<p>Hola,</p>"
                + "<p>Tienes un aviso de la app</p>";
        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
    }*/

    /**
     * Send email.
     *
     * @param recipientEmail the recipient email
     * @param link           the link
     * @param cuerpoMensaje  the cuerpo mensaje
     */
    public void sendEmail(String recipientEmail, String link, String cuerpoMensaje)
    {
        log.info("HEMOS ENVIADO EL MAIL PERO NJO FUNCIONA EL ENVIO!!");
    }

}
