package com.example.nexspringboot.service;

import com.example.nexspringboot.dto.SkinTiendaDto;
import com.example.nexspringboot.model.SkinTienda;
import com.example.nexspringboot.repository.SkinTiendaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SkinTiendaService {

    private final SkinTiendaRepository repo;

    public SkinTiendaService(SkinTiendaRepository repo) {
        this.repo = repo;
    }

    private SkinTiendaDto toDto(SkinTienda s) {
        return new SkinTiendaDto(s.getId(), s.getNombre(), s.getPrecio(), s.getImagenUrl());
    }

    public List<SkinTiendaDto> getAll() {
        return repo.findAll().stream().map(this::toDto).toList();
    }

    public SkinTiendaDto getById(Integer id) {
        return toDto(repo.findById(id).orElseThrow());
    }

    public SkinTiendaDto create(SkinTienda skin) {
        return toDto(repo.save(skin));
    }

    public SkinTiendaDto update(Integer id, SkinTienda skin) {
        skin.setId(id);
        return toDto(repo.save(skin));
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }
}