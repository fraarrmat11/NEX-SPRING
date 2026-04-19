package com.example.nexspringboot.repository;
import com.example.nexspringboot.model.SkinTienda;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkinTiendaRepository extends JpaRepository<SkinTienda, Integer> {}
