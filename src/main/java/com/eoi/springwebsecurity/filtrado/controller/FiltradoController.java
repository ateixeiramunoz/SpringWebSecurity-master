package com.eoi.springwebsecurity.filtrado.controller;

import com.eoi.springwebsecurity.coreapp.entities.User;
import com.eoi.springwebsecurity.filtrado.objects.SearchFormData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.extras.springsecurity6.auth.Authorization;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


/**
 * The type Filtrado controller.
 */
@Controller
public class FiltradoController {

    /**
     * Filtrado string.
     *
     * @param principal     the principal
     * @param authorization the authorization
     * @param model         the model
     *
     * @return the string
     */
    @GetMapping("/filtrado")
    public String filtrado(Principal principal, Authorization authorization, Model model){

        SearchFormData searchFormData = new SearchFormData();
        List<User> listaDatos = new ArrayList<>();
        User a = new User();
        a.setName("pepe");
        User b = new User();
        b.setName("javier");
        User c = new User();
        c.setName("Zacarias");
        listaDatos.add(a);
        listaDatos.add(b);
        listaDatos.add(c);

        String filtroLista = "";
        model.addAttribute("filtroLista", filtroLista);

        model.addAttribute("searchFormData", searchFormData);
        model.addAttribute("listaDatos", listaDatos);
        return "filtrado/index";
    }

    /**
     * Filtrado string.
     *
     * @param model          the model
     * @param searchFormData the search form data
     *
     * @return the string
     */
//https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#expressions-collection-selection
    @PostMapping("/filtrado")
    public String filtrado(Model model, @ModelAttribute("searchFormData") SearchFormData searchFormData){
        List<User> listaDatos = new ArrayList<>();
        User a = new User();
        a.setName("pepe");
        User b = new User();
        b.setName("javier");
        User c = new User();
        c.setName("Zacarias");

        listaDatos.add(a);
        listaDatos.add(b);
        listaDatos.add(c);
        model.addAttribute("searchFormData", searchFormData);
        model.addAttribute("filtroLista", searchFormData.getSearchText());
        model.addAttribute("listaDatos", listaDatos);
        return "filtrado/index";
    }
}
