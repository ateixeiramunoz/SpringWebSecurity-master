package com.eoi.springwebsecurity.paginacion;

import com.eoi.springwebsecurity.coreapp.entities.User;
import com.eoi.springwebsecurity.coreapp.repositories.UserPagingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * The type Paginated user list controller.
 */
@Controller
public class PaginatedUserListController {

    /*
     *  https://www.bezkoder.com/thymeleaf-pagination-and-sorting-example/
        https://www.baeldung.com/spring-thymeleaf-pagination
     *
     */

    @Autowired
    private UserPagingRepository userPagingRepository;

    /**
     * Find all users string.
     *
     * @param model the model
     * @param page  the page
     * @param size  the size
     *
     * @return the string
     */
    @GetMapping("/paginacion")
    public String findAllUsers(Model model,
                               @RequestParam("page") Optional<Integer> page,
                               @RequestParam("size") Optional<Integer> size
                               )
    {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);

        Page<User> pagina = userPagingRepository.findAll(PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("pagina", pagina);

        int totalPages = pagina.getTotalPages();

        //Este código se puede sustituir por el de abajo comentado. Hacen lo mismo
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        /*
            El código anterior, se puede sustituir por esta forma más tradicional de construir el array, si bien Java
            ahora dispone de las clases de Stream, por lo que se presupone que deberíamos utilizarlas.
                if (totalPages > 0) {
                    List<Integer> pageNumbers = new ArrayList<>();
                    for (int i = 1; i <= totalPages; i++) {
                        Integer integer = i;
                        pageNumbers.add(integer);
                    }
                    model.addAttribute("pageNumbers", pageNumbers);
                }
        */
        return "/paginacion/lista";
    }

}
