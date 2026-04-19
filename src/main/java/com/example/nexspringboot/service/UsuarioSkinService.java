package com.example.nexspringboot.service;

import com.example.nexspringboot.dto.SkinTiendaDto;
import com.example.nexspringboot.dto.UsuarioSkinDto;
import com.example.nexspringboot.dto.UsuarioSkinRequest;
import com.example.nexspringboot.model.SkinTienda;
import com.example.nexspringboot.model.Usuario;
import com.example.nexspringboot.model.UsuarioSkin;
import com.example.nexspringboot.repository.SkinTiendaRepository;
import com.example.nexspringboot.repository.UsuarioRepository;
import com.example.nexspringboot.repository.UsuarioSkinRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UsuarioSkinService {

    private final UsuarioSkinRepository repo;
    private final UsuarioRepository usuarioRepository;
    private final SkinTiendaRepository skinRepository;

    public UsuarioSkinService(UsuarioSkinRepository repo, UsuarioRepository usuarioRepository, SkinTiendaRepository skinRepository) {
        this.repo = repo;
        this.usuarioRepository = usuarioRepository;
        this.skinRepository = skinRepository;
    }

    private UsuarioSkinDto toDto(UsuarioSkin us) {
        SkinTienda s = us.getSkin();
        SkinTiendaDto skinDto = new SkinTiendaDto(s.getId(), s.getNombre(), s.getPrecio(), s.getImagenUrl());
        return new UsuarioSkinDto(us.getId(), us.getUsuario().getId(), skinDto, us.getFechaCompra());
    }

    public List<UsuarioSkinDto> getAll() {
        return repo.findAll().stream().map(this::toDto).toList();
    }

    public UsuarioSkinDto getById(Integer id) {
        return toDto(repo.findById(id).orElseThrow());
    }

    public List<UsuarioSkinDto> getByUsuario(Integer usuarioId) {
        return repo.findAll().stream()
                .filter(us -> us.getUsuario().getId().equals(usuarioId))
                .map(this::toDto)
                .toList();
    }

    public UsuarioSkinDto create(UsuarioSkinRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId()).orElseThrow();
        SkinTienda skin = skinRepository.findById(request.getSkinId()).orElseThrow();

        // Comprobar que tiene suficientes monedas
        if (usuario.getMonedas() < skin.getPrecio()) {
            throw new RuntimeException("Monedas insuficientes");
        }

        // Descontar monedas
        usuario.setMonedas(usuario.getMonedas() - skin.getPrecio());
        usuarioRepository.save(usuario);

        return toDto(repo.save(new UsuarioSkin(usuario, skin)));
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }
}