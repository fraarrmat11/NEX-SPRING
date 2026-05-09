package com.example.nexspringboot.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    private String contrasena;
    private Integer experienciaActual;
    private Integer monedas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nivel_id")
    private Nivel nivel;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Habito> habitos = new ArrayList<>();

    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<UsuarioLogro> logrosDesbloqueados = new ArrayList<>();

    public Usuario(String nombre, String contrasena) {
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.experienciaActual = 0;
        this.monedas = 0;
    }
}