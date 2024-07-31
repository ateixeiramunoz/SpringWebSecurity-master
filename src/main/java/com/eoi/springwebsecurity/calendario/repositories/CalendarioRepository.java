package com.eoi.springwebsecurity.calendario.repositories;

import com.eoi.springwebsecurity.calendario.entities.Calendario;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * The interface Calendario repository.
 */
public interface CalendarioRepository extends JpaRepository<Calendario, Integer> {



}