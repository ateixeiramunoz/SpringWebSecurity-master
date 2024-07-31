package com.eoi.springwebsecurity.scheduling.controller;

import com.eoi.springwebsecurity.scheduling.modelos.MyTimerTask;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Timer;
import java.util.TimerTask;

/**
 * The type Timer controller.
 */
public class TimerController {

    /**
     * Crear timer string.
     *
     * @return the string
     */
    @GetMapping
    public String crearTimer() {

        MyTimerTask timerTask = new MyTimerTask();
        Timer timer = new Timer();
        //Programar con delay
        timer.schedule(timerTask, 100);
        timer.cancel();
        return  null;
    }

}
