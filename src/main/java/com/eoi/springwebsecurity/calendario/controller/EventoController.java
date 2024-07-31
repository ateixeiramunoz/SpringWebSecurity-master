package com.eoi.springwebsecurity.calendario.controller;

import com.eoi.springwebsecurity.calendario.entities.Evento;
import com.eoi.springwebsecurity.calendario.repositories.EventoRepository;
import com.eoi.springwebsecurity.calendario.repositories.TipoDeEventoRepository;
import com.eoi.springwebsecurity.coreapp.entities.User;
import com.eoi.springwebsecurity.coreapp.repositories.UserRepository;
import com.eoi.springwebsecurity.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The type Evento controller.
 */
@Controller
@RequestMapping("/eventos")
public class EventoController {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private TipoDeEventoRepository tipoDeEventoRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Mostrar formulario creacion string.
     *
     * @param model the model
     *
     * @return the string
     */
    @GetMapping("/crear")
    public String mostrarFormularioCreacion(Model model) {
        Evento evento = new Evento();
        model.addAttribute("evento", evento);
        model.addAttribute("tiposDeEvento", tipoDeEventoRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        return "evento/formulario";
    }

    /**
     * Guardar evento string.
     *
     * @param evento the evento
     * @param model  the model
     *
     * @return the string
     */
    @PostMapping(value={"","/"})
    public String guardarEvento(@ModelAttribute("evento") Evento evento,Model model) {
        eventoRepository.save(evento);
        return "redirect:/calendarios";
    }
}

