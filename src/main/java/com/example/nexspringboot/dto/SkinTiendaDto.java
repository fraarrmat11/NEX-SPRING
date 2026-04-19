package com.example.nexspringboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkinTiendaDto {
    private Integer id;
    private String nombre;
    private Integer precio;
    private String imagenUrl;
}