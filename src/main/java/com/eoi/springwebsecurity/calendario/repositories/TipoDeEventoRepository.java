package com.eoi.springwebsecurity.calendario.repositories;

import com.eoi.springwebsecurity.calendario.entities.TipoDeEvento;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Tipo de evento repository.
 */
public interface TipoDeEventoRepository extends JpaRepository<TipoDeEvento, Integer> {
}