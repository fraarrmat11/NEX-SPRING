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
public class Habito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private String nombre;
    private Integer objetivo;
    private Integer progresoActual = 0;
    private Integer experienciaXCompletar = 0;
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    private Boolean activo = true;

    @OneToMany(mappedBy = "habito", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RegistroHabito> registros = new ArrayList<>();

    public Habito(Usuario usuario, String nombre, Integer objetivo) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.objetivo = objetivo;
    }
}