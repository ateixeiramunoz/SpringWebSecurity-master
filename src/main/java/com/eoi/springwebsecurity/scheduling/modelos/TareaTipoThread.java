package com.eoi.springwebsecurity.scheduling.modelos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * The type Tarea tipo thread.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TareaTipoThread implements Runnable{

        private String message;

        @Override
        public void run() {
            System.out.println(new Date()+" Tarea programada y por thread  con "+message
                    +" en el hilo "+Thread.currentThread().getName());
        }

}
