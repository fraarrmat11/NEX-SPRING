package com.example.nexspringboot.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class RegistroHabitoRequest {
    private Integer habitoId;
    private LocalDate fecha;
    private Integer cantidad;
    private Boolean completado;
}