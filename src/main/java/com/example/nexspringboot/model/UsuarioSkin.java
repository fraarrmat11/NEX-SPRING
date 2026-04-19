package com.example.nexspringboot.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioSkin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skin_id")
    private SkinTienda skin;

    private LocalDate fechaCompra;

    public UsuarioSkin(Usuario usuario, SkinTienda skin) {
        this.usuario = usuario;
        this.skin = skin;
        this.fechaCompra = LocalDate.now();
    }
}