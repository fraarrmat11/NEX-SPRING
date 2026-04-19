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
public class Nivel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer experienciaNecesaria;
    private String nombre;
    private Integer recompensaMonedas;

    @OneToMany(mappedBy = "nivel")
    List<Usuario> usuarios = new ArrayList<>();

    public Nivel(Integer experienciaNecesaria, String nombre, Integer recompensaMonedas) {
        this.experienciaNecesaria = experienciaNecesaria;
        this.nombre = nombre;
        this.recompensaMonedas = recompensaMonedas;
    }
}