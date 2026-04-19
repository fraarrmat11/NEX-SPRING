package com.example.nexspringboot.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class DiarioRequest {
    private Integer usuarioId;
    private LocalDate fecha;
    private String contenido;
}