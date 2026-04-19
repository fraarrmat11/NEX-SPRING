package com.example.nexspringboot.service;

import com.example.nexspringboot.dto.NivelDto;
import com.example.nexspringboot.model.Nivel;
import com.example.nexspringboot.repository.NivelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class NivelService {

    private final NivelRepository repo;

    public NivelService(NivelRepository repo) {
        this.repo = repo;
    }

    private NivelDto toDto(Nivel n) {
        return new NivelDto(n.getId(), n.getNombre(), n.getExperienciaNecesaria(), n.getRecompensaMonedas());
    }

    public List<NivelDto> getAll() {
        return repo.findAll().stream().map(this::toDto).toList();
    }

    public NivelDto getById(Integer id) {
        return toDto(repo.findById(id).orElseThrow());
    }

    public NivelDto create(Nivel nivel) {
        return toDto(repo.save(nivel));
    }

    public NivelDto update(Integer id, Nivel nivel) {
        nivel.setId(id);
        return toDto(repo.save(nivel));
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }
}