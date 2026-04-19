package com.example.nexspringboot.service;

import com.example.nexspringboot.dto.RegistroHabitoDto;
import com.example.nexspringboot.dto.RegistroHabitoRequest;
import com.example.nexspringboot.model.Habito;
import com.example.nexspringboot.model.RegistroHabito;
import com.example.nexspringboot.repository.HabitoRepository;
import com.example.nexspringboot.repository.RegistroHabitoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RegistroHabitoService {

    private final RegistroHabitoRepository repo;
    private final HabitoRepository habitoRepository;
    private final UsuarioService usuarioService;

    public RegistroHabitoService(RegistroHabitoRepository repo, HabitoRepository habitoRepository, UsuarioService usuarioService) {
        this.repo = repo;
        this.habitoRepository = habitoRepository;
        this.usuarioService = usuarioService;
    }

    private RegistroHabitoDto toDto(RegistroHabito r) {
        return new RegistroHabitoDto(
                r.getId(),
                r.getHabito() != null ? r.getHabito().getId() : null,
                r.getFecha(),
                r.getCantidad(),
                r.getCompletado()
        );
    }

    public List<RegistroHabitoDto> getAll() {
        return repo.findAll().stream().map(this::toDto).toList();
    }

    public RegistroHabitoDto create(RegistroHabitoRequest request) {
        Habito habito = habitoRepository.findById(request.getHabitoId()).orElseThrow();
        RegistroHabito registro = new RegistroHabito(habito, request.getFecha(), request.getCantidad(), request.getCompletado());
        RegistroHabito saved = repo.save(registro);

        if (saved.getCompletado()) {
            usuarioService.agregarExperiencia(
                    habito.getUsuario().getId(),
                    habito.getExperienciaXCompletar()
            );
        }

        return toDto(saved);
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }
}