package com.eoi.springwebsecurity.scheduling.modelos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * The type Custom task info.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomTaskInfo {

    private boolean recurrente;
    private long retraso;
    private long periodo;
    private LocalDateTime fechaProgramada;




}

