package com.example.nexspringboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogroDto {
    private Integer id;
    private String nombre;
    private String descripcion;
    private Integer requisito;
    private Integer experienciaXDesbloquear;
}