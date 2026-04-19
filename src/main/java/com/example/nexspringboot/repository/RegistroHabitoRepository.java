package com.example.nexspringboot.repository;
import com.example.nexspringboot.model.RegistroHabito;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistroHabitoRepository extends JpaRepository<RegistroHabito, Integer> {}
