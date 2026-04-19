package com.example.nexspringboot.repository;
import com.example.nexspringboot.model.Habito;

import com.example.nexspringboot.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabitoRepository extends JpaRepository<Habito, Integer> {
    List<Habito> findByUsuario(Usuario usuario);
}