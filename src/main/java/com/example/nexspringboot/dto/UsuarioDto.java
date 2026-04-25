package com.example.nexspringboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto {
    private Integer id;
    private String nombre;
    private Integer experienciaActual;
    private Integer monedas;
    private NivelDto nivel;
    private LocalDateTime fechaCreacion;
    private Integer xpSiguienteNivel;
}