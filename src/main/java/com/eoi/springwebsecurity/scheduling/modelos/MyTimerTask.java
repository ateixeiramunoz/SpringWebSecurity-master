package com.eoi.springwebsecurity.scheduling.modelos;

import com.eoi.springwebsecurity.notificaciones.Notificacion;
import com.eoi.springwebsecurity.websockets.messages.PrivateMessage;
import com.eoi.springwebsecurity.websockets.service.MessagingService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.TimerTask;


/**
 * The type My timer task.
 */
@Log4j2
public class MyTimerTask extends TimerTask  {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private MessagingService messagingService;

    private String message;
    private String destinatario;
    private String remitente;

    /**
     * The action to be performed by this timer task.
     */
    @Override
    public void run() {

        PrivateMessage privateMessage = new PrivateMessage();
        privateMessage.setFrom(remitente);
        privateMessage.setTo(destinatario);
        privateMessage.setText(message);
        // Creo mi notificación en la base de datos para poder controlar el estado de los mensajes
        //Notificacion notificacion = messagingService.crearNotificacion(privateMessage);
        // Cuando ya tenemos el ID de la notificación, lo informamos en nuestro objeto PrivateMessage creado ad-hoc
        //privateMessage.setNotificationID(notificacion.getId());

        // Componemos un nuevo mensaje STOMP con nuestro PrivateMessage
        //simpMessagingTemplate.convertAndSendToUser(
        //        privateMessage.getTo(),
         //       "/specific",
         //       message,
         //       createHeaders(privateMessage.getTo(),
         //               String.valueOf(notificacion.getId()))
        //);
        log.info("Mensaje enviado a: " + privateMessage.getTo());
      //  log.info("Notificación creada con ID: " + notificacion.getId());
    }

    private MessageHeaders createHeaders(String recipient, String notificationID) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.addNativeHeader("notificationID", notificationID);
        return headerAccessor.getMessageHeaders();
    }


}

