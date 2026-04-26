package com.example.nexspringboot.service;

import com.example.nexspringboot.dto.DiarioDto;
import com.example.nexspringboot.dto.DiarioRequest;
import com.example.nexspringboot.model.Diario;
import com.example.nexspringboot.model.Usuario;
import com.example.nexspringboot.repository.DiarioRepository;
import com.example.nexspringboot.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DiarioService {

    private final DiarioRepository repo;
    private final UsuarioRepository usuarioRepository;
    private final LogroService logroService;

    public DiarioService(DiarioRepository repo, UsuarioRepository usuarioRepository, LogroService logroService) {
        this.repo = repo;
        this.usuarioRepository = usuarioRepository;
        this.logroService = logroService;
    }

    private DiarioDto toDto(Diario d) {
        return new DiarioDto(
                d.getId(),
                d.getUsuario() != null ? d.getUsuario().getId() : null,
                d.getFecha(),
                d.getContenido()
        );
    }

    public List<DiarioDto> getAll() {
        return repo.findAll().stream().map(this::toDto).toList();
    }

    public DiarioDto getById(Integer id) {
        return toDto(repo.findById(id).orElseThrow());
    }

    public List<DiarioDto> getByUsuario(Integer usuarioId) {
        return repo.findAll().stream()
                .filter(d -> d.getUsuario() != null && d.getUsuario().getId().equals(usuarioId))
                .map(this::toDto)
                .toList();
    }

    public DiarioDto create(DiarioRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId()).orElseThrow();
        Diario diario = new Diario(usuario, request.getFecha(), request.getContenido());
        DiarioDto resultado = toDto(repo.save(diario));
        logroService.comprobarLogrosDiario(usuario);
        return resultado;
    }

    public DiarioDto update(Integer id, DiarioRequest request) {
        Diario diario = repo.findById(id).orElseThrow();
        diario.setFecha(request.getFecha());
        diario.setContenido(request.getContenido());
        return toDto(repo.save(diario));
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }
}