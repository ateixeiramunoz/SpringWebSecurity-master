package com.eoi.springwebsecurity.calendario.entities;

import com.eoi.springwebsecurity.coreapp.entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The type Evento.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private LocalDateTime inicioEvento;
    private LocalDateTime finEvento;
    private String mensaje;

    @ManyToOne(fetch = FetchType.EAGER)
    private TipoDeEvento tipoDeEvento;

    @ManyToMany(mappedBy="eventos", cascade = CascadeType.PERSIST)
    private List<User> users;

    @ManyToMany(mappedBy="eventos", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Calendario> calendarios;

}
