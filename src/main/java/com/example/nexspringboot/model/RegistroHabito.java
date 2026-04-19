package com.example.nexspringboot.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroHabito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "habito_id")
    private Habito habito;

    private LocalDate fecha;
    private Integer cantidad;
    private Boolean completado = false;

    public RegistroHabito(Habito habito, LocalDate fecha, Integer cantidad, Boolean completado) {
        this.habito = habito;
        this.fecha = fecha;
        this.cantidad = cantidad;
        this.completado = completado;
    }
}