package com.eoi.springwebsecurity.notificaciones.controller;

import com.eoi.springwebsecurity.coreapp.entities.User;
import com.eoi.springwebsecurity.notificaciones.Notificacion;
import com.eoi.springwebsecurity.notificaciones.repository.NotificacionRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The type Notificaciones controller.
 */
@Log4j2
@Controller
public class NotificacionesController {

    /**
     * The Notificacion repository.
     */
    @Autowired
    NotificacionRepository notificacionRepository;

    /**
     * Obtiene el número de notificaciones pendientes para el usuario actual.
     *
     * @param principal El objeto principal que representa al usuario actual.
     *
     * @return El número de notificaciones pendientes en formato de texto.
     */
    @GetMapping("/numeroNotificaciones")
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    public String contarNotificacionesPendientes(Principal principal) {
        List<Notificacion> listaNotificaciones =
                notificacionRepository.findByUserToAndEstado(
                        principal.getName(),
                        "Pendiente"
                );
        return String.valueOf(listaNotificaciones.size());
    }

    /**
     * Muestra todas las notificaciones y permite filtrar por nombre de destino
     *
     * @param principal El objeto principal que representa al usuario actual.
     * @param model     El modelo utilizado para pasar los datos a la vista.
     * @param page      the page
     * @param size      the size
     * @param queryFrom the query from
     * @param queryTo   the query to
     *
     * @return La vista que muestra la lista de notificaciones pendientes.
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/notificaciones")
    public String mostrarNotificaciones(Principal principal, Model model,
                                        @RequestParam("page") Optional<Integer> page,
                                        @RequestParam("size") Optional<Integer> size,
                                        @RequestParam("queryFrom") Optional<String> queryFrom,
                                        @RequestParam("queryTo") Optional<String> queryTo
    )
    {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        String searchFrom = queryFrom.orElse("");
        String searchTo = queryTo.orElse("");
        Page<Notificacion> pagina =
                notificacionRepository.findByUserFromContainingIgnoreCaseAndUserToContainingIgnoreCaseOrderByFechaDesc(

                searchFrom,
                searchTo,
                PageRequest.of(currentPage - 1, pageSize)
        );
        model.addAttribute("queryFrom", searchFrom);
        model.addAttribute("queryTo", searchTo);
        model.addAttribute("pagina", pagina);
        int totalPages = pagina.getTotalPages();

        //Este código se puede sustituir por el de abajo comentado. Hacen lo mismo
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "notificaciones/list";
    }


    /**
     * Muestra todas las notificaciones y permite filtrar por nombre de destino
     *
     * @param principal El objeto principal que representa al usuario actual.
     * @param model     El modelo utilizado para pasar los datos a la vista.
     * @param page      the page
     * @param size      the size
     * @param name      the name
     *
     * @return La vista que muestra la lista de notificaciones pendientes.
     */
    @GetMapping("/notificacionesUsuario")
    public String mostrarNotificacionesUsuario(Principal principal, Model model,
                                        @RequestParam("page") Optional<Integer> page,
                                        @RequestParam("size") Optional<Integer> size,
                                        @RequestParam("query") Optional<String> name
    )
    {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);
        String searchName = name.orElse("");

        Page<Notificacion> pagina = notificacionRepository.findByUserFromAndUserToLikeIgnoreCaseOrderByFechaDesc(
                principal.getName(),
                searchName,
                PageRequest.of(currentPage - 1, pageSize)
        );
        model.addAttribute("query", searchName);
        model.addAttribute("pagina", pagina);
        int totalPages = pagina.getTotalPages();

        //Este código se puede sustituir por el de abajo comentado. Hacen lo mismo
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "notificaciones/list";
    }


    /**
     * Marca todas las notificaciones pendientes como leídas para el usuario actual.
     *
     * @param principal El objeto principal que representa al usuario actual.
     * @param model     El modelo utilizado para pasar los datos a la vista.
     *
     * @return La vista que muestra la lista de notificaciones pendientes actualizada.
     */
    @GetMapping("/leerNotificaciones")
    public String leerNotificacionesPendientes(Principal principal, Model model) {

        List<Notificacion> listaNotificaciones =
                notificacionRepository.findByUserToAndEstado(
                        principal.getName(),
                        "Pendiente"
                );
        listaNotificaciones.forEach(notificacion -> {
            notificacion.setEstado("READ");
            notificacionRepository.save(notificacion);
            log.debug("La notificación {} ha sido marcada como leída", notificacion.getId());
        });
        model.addAttribute("listaNotificaciones", listaNotificaciones);
        return "notificaciones/list";
    }


    /**
     * Marca una notificación específica como leída para el usuario actual.
     *
     * @param id        El ID de la notificación a marcar como leída.
     * @param principal El objeto principal que representa al usuario actual.
     * @param model     El modelo utilizado para pasar los datos a la vista.
     *
     * @return La redirección a la página de notificaciones.
     */
    @GetMapping("/leerNotificacion/{id}")
    public String leerNotificacion(@PathVariable("id") String id, Principal principal, Model model) {
        Optional<Notificacion> notificacion = notificacionRepository.findById(id);

        if (notificacion.isPresent()) {
            log.debug("La notificación {} ha sido marcada como leída",id);
            notificacion.get().setEstado("READ");
            notificacionRepository.save(notificacion.get());
        }

        return "redirect:/notificaciones";
    }

}
