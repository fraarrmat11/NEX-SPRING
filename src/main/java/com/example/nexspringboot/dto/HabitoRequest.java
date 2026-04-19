package com.example.nexspringboot.dto;

import lombok.Data;

@Data
public class HabitoRequest {
    private Integer usuarioId;
    private String nombre;
    private Integer objetivo;
    private Integer experienciaXCompletar;
}