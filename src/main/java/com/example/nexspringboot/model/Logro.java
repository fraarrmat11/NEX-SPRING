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
public class Logro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    private String descripcion;
    private Integer requisito;
    private Integer experienciaXDesbloquear = 0;
    private String tipo;

    @OneToMany(mappedBy = "logro", cascade = CascadeType.ALL)
    private List<UsuarioLogro> usuarios = new ArrayList<>();

    public Logro(String nombre, String descripcion, Integer requisito, Integer experienciaXDesbloquear, String tipo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.requisito = requisito;
        this.experienciaXDesbloquear = experienciaXDesbloquear;
        this.tipo = tipo;
    }
}