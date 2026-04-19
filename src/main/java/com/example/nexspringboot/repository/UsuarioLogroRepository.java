package com.example.nexspringboot.repository;
import com.example.nexspringboot.model.UsuarioLogro;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioLogroRepository extends JpaRepository<UsuarioLogro, Integer> {}