package com.example.nexspringboot.service;

import com.example.nexspringboot.dto.LogroDto;
import com.example.nexspringboot.dto.UsuarioLogroDto;
import com.example.nexspringboot.dto.UsuarioLogroRequest;
import com.example.nexspringboot.model.Logro;
import com.example.nexspringboot.model.Usuario;
import com.example.nexspringboot.model.UsuarioLogro;
import com.example.nexspringboot.repository.LogroRepository;
import com.example.nexspringboot.repository.UsuarioLogroRepository;
import com.example.nexspringboot.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UsuarioLogroService {

    private final UsuarioLogroRepository repo;
    private final UsuarioRepository usuarioRepository;
    private final LogroRepository logroRepository;
    private final UsuarioService usuarioService;

    public UsuarioLogroService(UsuarioLogroRepository repo, UsuarioRepository usuarioRepository,
                               LogroRepository logroRepository, UsuarioService usuarioService) {
        this.repo = repo;
        this.usuarioRepository = usuarioRepository;
        this.logroRepository = logroRepository;
        this.usuarioService = usuarioService;
    }

    private UsuarioLogroDto toDto(UsuarioLogro ul) {
        Logro l = ul.getLogro();
        LogroDto logroDto = new LogroDto(l.getId(), l.getNombre(), l.getDescripcion(), l.getRequisito(), l.getExperienciaXDesbloquear());
        return new UsuarioLogroDto(ul.getId(), ul.getUsuario().getId(), logroDto, ul.getFecha());
    }

    public List<UsuarioLogroDto> getAll() {
        return repo.findAll().stream().map(this::toDto).toList();
    }

    public UsuarioLogroDto getById(Integer id) {
        return toDto(repo.findById(id).orElseThrow());
    }

    public UsuarioLogroDto create(UsuarioLogroRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId()).orElseThrow();
        Logro logro = logroRepository.findById(request.getLogroId()).orElseThrow();
        UsuarioLogro ul = new UsuarioLogro(usuario, logro);
        UsuarioLogro saved = repo.save(ul);
        usuarioService.agregarExperiencia(usuario.getId(), logro.getExperienciaXDesbloquear());
        return toDto(saved);
    }

    public List<UsuarioLogroDto> getByUsuario(Integer usuarioId) {
        return repo.findAll().stream()
                .filter(ul -> ul.getUsuario().getId().equals(usuarioId))
                .map(this::toDto)
                .toList();
    }
    public void delete(Integer id) {
        repo.deleteById(id);
    }
}