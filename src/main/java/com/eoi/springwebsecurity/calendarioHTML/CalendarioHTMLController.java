package com.eoi.springwebsecurity.calendarioHTML;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The type Calendario html controller.
 */
@Controller
@Log4j2
public class CalendarioHTMLController {


    //https://stackoverflow.com/questions/36747620/conditionally-closing-tag-in-thymeleaf

    /**
     * Gets calendario html.
     *
     * @param model the model
     * @param day   the day
     * @param month the month
     * @param year  the year
     *
     * @return the calendario html
     */
    @GetMapping("/calendarioHTML")
    public String getCalendarioHTML(Model model,
                                    @RequestParam("day") Optional<Integer> day,
                                    @RequestParam("month") Optional<Integer> month,
                                    @RequestParam("year") Optional<Integer> year)
    {

        int firstDayOfWeek=0;

        int numeroColumnas = 7;
        int mes=1;
        int agno=2023;
        int dia=1;
        int totalDias;

        mes=month.orElse(mes);
        agno=year.orElse(agno);

        //Creo un array MULTIDIMENSONAL que guardará LAS SEMANAS Y LOS días que quiero mostrar
        List<List<String>> dias = new ArrayList<>();

        //Creo un objeto de fecha para los calculos de nombres, numero de dias, etc.
        LocalDate objetoFecha = LocalDate.of(agno, mes, dia);

        //Calculo el total de dias del mes solicitado
        totalDias = objetoFecha.lengthOfMonth();

        //Relleno el array con los dias que tiene el mes
        int fila=-1;
        for(int i = 1; i<= totalDias; i++)
        {
            LocalDate fechaEnUso = LocalDate.of(agno, mes, i);
            if( fechaEnUso.getDayOfWeek().ordinal() == 0 || fila==-1)
            {
                fila+=1;
                List<String> semana = new ArrayList<>();
                dias.add(semana);
                if(fila==0)
                {
                    for (int j=0;j<fechaEnUso.getDayOfWeek().ordinal(); j++)
                    {
                        dias.get(fila).add("");
                    }
                }
            }
            dias.get(fila).add(String.valueOf(i));
        }

        model.addAttribute("numeroColumnas",numeroColumnas);
        model.addAttribute("dias",dias);

        return "calendarioOK/calendario";
    }


}
