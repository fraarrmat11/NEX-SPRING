package com.example.nexspringboot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioSkinDto {
    private Integer id;
    private Integer usuarioId;
    private SkinTiendaDto skin;
    private LocalDate fechaCompra;
}