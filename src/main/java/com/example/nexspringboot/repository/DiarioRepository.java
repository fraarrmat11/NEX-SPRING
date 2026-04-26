package com.example.nexspringboot.repository;
import com.example.nexspringboot.model.Diario;

import com.example.nexspringboot.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiarioRepository extends JpaRepository<Diario, Integer> {
    long countByUsuario(Usuario usuario);
}