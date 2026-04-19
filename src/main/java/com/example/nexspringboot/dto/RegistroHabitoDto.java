package com.example.nexspringboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroHabitoDto {
    private Integer id;
    private Integer habitoId;
    private LocalDate fecha;
    private Integer cantidad;
    private Boolean completado;
}