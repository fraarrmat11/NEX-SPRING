package com.example.nexspringboot.repository;
import com.example.nexspringboot.model.Logro;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogroRepository extends JpaRepository<Logro, Integer> {}