package com.example.nexspringboot.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Diario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private LocalDate fecha;
    private String contenido;

    public Diario(Usuario usuario, LocalDate fecha, String contenido) {
        this.usuario = usuario;
        this.fecha = fecha;
        this.contenido = contenido;
    }
}