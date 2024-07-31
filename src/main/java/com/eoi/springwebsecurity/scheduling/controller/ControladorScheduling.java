package com.eoi.springwebsecurity.scheduling.controller;

import com.eoi.springwebsecurity.scheduling.entities.Cita;
import com.eoi.springwebsecurity.scheduling.modelos.CustomTaskInfo;
import com.eoi.springwebsecurity.scheduling.modelos.NotificacionAsincrona;
import com.eoi.springwebsecurity.scheduling.service.SchedulingService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.Task;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * The type Controlador scheduling.
 */
@Controller
@Log4j2
public class ControladorScheduling {


    /**
     * The Scheduling service.
     */
    @Autowired
    SchedulingService schedulingService;
    /**
     * The Thread pool task scheduler.
     */
    @Autowired
    ThreadPoolTaskScheduler threadPoolTaskScheduler;

    /**
     * Listar tareas string.
     *
     * @param model the model
     *
     * @return the string
     */
    @GetMapping("/scheduling")
    public String listarTareas(Model model) {
        BlockingQueue<Runnable> colaTareasProgramadas = threadPoolTaskScheduler.getScheduledThreadPoolExecutor().getQueue();
        List<CustomTaskInfo> listaTareas = new ArrayList<>();



        for (Runnable tarea : colaTareasProgramadas) {
            if (tarea instanceof FutureTask) {
                // Obtén la información de la tarea programada según tus necesidades
                // Por ejemplo, puedes obtener el destinatario, asunto, etc.

                //((FutureTask<?>) tarea).cancel();
                // Crea un objeto CustomTaskInfo con la información relevante
                CustomTaskInfo customTaskInfo = new CustomTaskInfo(false,
                        10L,
                        10L,
                        LocalDateTime.now()
                );
                listaTareas.add(customTaskInfo);
            }
        }
        model.addAttribute("cita", new Cita());
        model.addAttribute("listaTareas", listaTareas);
        return "/scheduling/cita";
    }

    /**
     * Programar cita string.
     *
     * @param cita the cita
     *
     * @return the string
     */
    @PostMapping("/scheduling/programar")
    public String programarCita(@ModelAttribute Cita cita)
    {
        log.info("Se ha recibido la programacion de una cita");
        schedulingService.crearCita(cita);
        return "redirect:/scheduling";
    }


}
