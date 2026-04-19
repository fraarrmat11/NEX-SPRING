package com.example.nexspringboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NivelDto {
    private Integer id;
    private String nombre;
    private Integer experienciaNecesaria;
    private Integer recompensaMonedas;
}