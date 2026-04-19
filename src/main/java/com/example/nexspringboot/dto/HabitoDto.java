package com.example.nexspringboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HabitoDto {
    private Integer id;
    private Integer usuarioId;
    private String nombre;
    private Integer objetivo;
    private Integer progresoActual;
    private Integer experienciaXCompletar;
    private LocalDateTime fechaCreacion;
    private Boolean activo;
}