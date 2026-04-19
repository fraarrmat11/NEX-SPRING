package com.example.nexspringboot.repository;

import com.example.nexspringboot.model.Nivel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface NivelRepository extends JpaRepository<Nivel, Integer> {
    Optional<Nivel> findFirstByExperienciaNecesariaGreaterThanOrderByExperienciaNecesariaAsc(Integer experiencia);
}