package com.eoi.springwebsecurity.websockets.service;

import com.eoi.springwebsecurity.notificaciones.Notificacion;
import com.eoi.springwebsecurity.notificaciones.repository.NotificacionRepository;
import com.eoi.springwebsecurity.websockets.messages.PrivateMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


// 1.- Crear un objeto notificación en Base de datos
// 2.- Obtener el ID De la notificación para adjuntarlo al mensaje
// 3.- Al recibir el mensaje, procesaremos el contenido y obtendremos el ID
// 4.- En el metodo callback de la recepción, enviaremos el ID Del mensaje para indicar que se ha recibido.
// 5.- Desde el servidor, al recibir la confirmación de dicho mensaje, marcaremos la notificación a recibida.

/**
 * The type Messaging service.
 */
@Service
@Log4j2
public class MessagingService {

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;



    /**
     * Enviar mensaje stomp.
     *
     * @param message the message
     */
    public void EnviarMensajeSTOMPDeNotificacion(PrivateMessage message, Notificacion notificacion)
    {
        simpMessagingTemplate.convertAndSendToUser(
                message.getTo(),
                "/specific",
                message,
                createHeaders(message.getTo(),
                        String.valueOf(notificacion.getId()))
        );
        log.info("Mensaje enviado a: " + message.getTo());
    }



    /**
     * Crea una nueva notificación y la guarda en la base de datos.
     *
     * @param userTo   El usuario al que se enviará la notificación.
     * @param userFrom El usuario desde el cual se envía la notificación.
     * @param mensaje  El contenido del mensaje de la notificación.
     * @return La notificación creada y guardada.
     */
    public Notificacion crearNotificacion(String userTo, String userFrom, String mensaje) {
        Notificacion notificacion = new Notificacion();
        notificacion.setEstado("Pendiente");
        notificacion.setFecha(LocalDateTime.now());
        notificacion.setUserTo(userTo);
        notificacion.setUserFrom(userFrom);
        notificacion.setText(mensaje);
        notificacionRepository.save(notificacion);
        return notificacion;
    }


    /**
     * Crea una notificación y un mensaje privado cuando ocurre algo y envía el mensaje.
     *
     * @param queOcurre       La descripción de lo que ha ocurrido.
     * @param aQuienLeOcurre  El destinatario de la notificación y el mensaje.
     */
    public void crearMensajeYNotificacionDeAdminCuandoOcurreAlgoYEnviarElMensaje(String queOcurre, String aQuienLeOcurre) {
        // Primero creamos la notificación de algo que ocurre en el sistema
        Notificacion notificacion = crearNotificacion(aQuienLeOcurre, "ADMIN", queOcurre);

        // Después preparamos el objeto mensaje que será enviado por la cola de mensajes STOMP en base a la
        // notificación de que ha ocurrido algo
        // El mensaje toma el ID de la notificación que hemos creado en BD para controlar su recepción cuando un
        // cliente conectado lo recibe.
        PrivateMessage privateMessage = new PrivateMessage(
                notificacion.getId(),
                notificacion.getText(),
                notificacion.getUserTo(),
                notificacion.getUserFrom()
        );

        enviarMensajePrivado(privateMessage);
    }



    /**
     * Envía un mensaje privado a través de la cola de mensajes STOMP.
     *
     * @param privateMessage El mensaje privado a enviar.
     */
    public void enviarMensajePrivado(PrivateMessage privateMessage) {
        simpMessagingTemplate.convertAndSendToUser(
                privateMessage.getTo(),
                "/specific",
                privateMessage,
                createHeaders(privateMessage.getTo(), String.valueOf(privateMessage.getNotificationID()))
        );
        log.info("Mensaje enviado a: " + privateMessage.getTo());
    }


    /**
     * Crea los encabezados del mensaje con el destinatario y el ID de notificación.
     *
     * @param recipient     El destinatario del mensaje.
     * @param notificationID El ID de la notificación.
     * @return Los encabezados del mensaje.
     */
    public MessageHeaders createHeaders(String recipient, String notificationID) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.addNativeHeader("notificationID", notificationID);
        return headerAccessor.getMessageHeaders();
    }




}
