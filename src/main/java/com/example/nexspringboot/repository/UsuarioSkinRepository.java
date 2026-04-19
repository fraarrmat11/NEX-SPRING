package com.example.nexspringboot.repository;
import com.example.nexspringboot.model.UsuarioSkin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioSkinRepository extends JpaRepository<UsuarioSkin, Integer> {}