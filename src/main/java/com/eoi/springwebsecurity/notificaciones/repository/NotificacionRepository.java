package com.eoi.springwebsecurity.notificaciones.repository;

import com.eoi.springwebsecurity.notificaciones.Notificacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Notificacion repository.
 */
@Repository
public interface NotificacionRepository extends PagingAndSortingRepository<Notificacion, String>,JpaRepository<Notificacion, String> {

    /**
     * Find by user to and estado list.
     *
     * @param userFrom the user from
     * @param estado   the estado
     *
     * @return the list
     */
    public List<Notificacion> findByUserToAndEstado(String userFrom, String estado);

    /**
     * Find by user from containing ignore case and user to containing ignore case order by fecha desc page.
     *
     * @param userFrom the user from
     * @param userTo   the user to
     * @param pageable the pageable
     *
     * @return the page
     */
    public Page<Notificacion> findByUserFromContainingIgnoreCaseAndUserToContainingIgnoreCaseOrderByFechaDesc(String userFrom,String userTo, Pageable pageable);

    /**
     * Find by user from and user to like ignore case order by fecha desc page.
     *
     * @param userFrom the user from
     * @param userTo   the user to
     * @param pageable the pageable
     *
     * @return the page
     */
    public Page<Notificacion>  findByUserFromAndUserToLikeIgnoreCaseOrderByFechaDesc(String userFrom,String userTo, Pageable pageable);

    /**
     * Find by user to order by fecha desc page.
     *
     * @param userTo   the user to
     * @param pageable the pageable
     *
     * @return the page
     */
    public Page<Notificacion> findByUserToOrderByFechaDesc(String userTo, Pageable pageable);

    /**
     * Find by user to containing ignore case order by fecha desc page.
     *
     * @param userLike the user like
     * @param pageable the pageable
     *
     * @return the page
     */
    public Page<Notificacion> findByUserToContainingIgnoreCaseOrderByFechaDesc (String userLike, Pageable pageable);




}