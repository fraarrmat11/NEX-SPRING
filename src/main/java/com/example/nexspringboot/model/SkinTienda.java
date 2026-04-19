package com.example.nexspringboot.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SkinTienda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    private Integer precio;
    private String imagenUrl;

    @OneToMany(mappedBy = "skin", cascade = CascadeType.ALL)
    private List<UsuarioSkin> usuarios = new ArrayList<>();

    public SkinTienda(String nombre, Integer precio, String colorPrincipal, String descripcion, String imagenUrl) {
        this.nombre = nombre;
        this.precio = precio;
        this.imagenUrl = imagenUrl;
    }
}