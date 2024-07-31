package com.eoi.springwebsecurity.calendario.repositories;

import com.eoi.springwebsecurity.calendario.entities.Calendario;
import com.eoi.springwebsecurity.calendario.entities.Evento;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * The interface Evento repository.
 */
public interface EventoRepository extends JpaRepository<Evento, Long> {


}