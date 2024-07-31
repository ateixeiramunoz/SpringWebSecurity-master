package com.eoi.springwebsecurity.scheduling.entities;

import com.eoi.springwebsecurity.coreapp.entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * The type Cita.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime fecha;
    private String nombre;

    //Configuracion de aviso/notificacion
    private Boolean notificar;
    private Boolean notificarTutor;
    private Integer numeroDias;
    private Integer numeroMinutos;
    private Integer numeroHoras;

    private Boolean enviarEmail;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user",referencedColumnName = "id")
    private User usuario;

}

