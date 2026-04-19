package com.example.nexspringboot.service;

import com.example.nexspringboot.dto.NivelDto;
import com.example.nexspringboot.dto.UsuarioDto;
import com.example.nexspringboot.model.Nivel;
import com.example.nexspringboot.model.Usuario;
import com.example.nexspringboot.repository.NivelRepository;
import com.example.nexspringboot.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final NivelRepository nivelRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, NivelRepository nivelRepository) {
        this.usuarioRepository = usuarioRepository;
        this.nivelRepository = nivelRepository;
    }

    private NivelDto toNivelDto(Nivel n) {
        return new NivelDto(n.getId(), n.getNombre(), n.getExperienciaNecesaria(), n.getRecompensaMonedas());
    }

    private UsuarioDto toDto(Usuario u) {
        return new UsuarioDto(
                u.getId(),
                u.getNombre(),
                u.getExperienciaActual(),
                u.getMonedas(),
                u.getNivel() != null ? toNivelDto(u.getNivel()) : null,
                u.getFechaCreacion()
        );
    }

    public List<UsuarioDto> getAll() {
        return usuarioRepository.findAll().stream().map(this::toDto).toList();
    }

    public UsuarioDto getById(Integer id) {
        return toDto(usuarioRepository.findById(id).orElseThrow());
    }

    public UsuarioDto crearUsuario(Usuario usuario) {
        Nivel nivel1 = nivelRepository.findById(1).orElse(null);
        usuario.setNivel(nivel1);
        usuario.setExperienciaActual(0);
        return toDto(usuarioRepository.save(usuario));
    }

    public UsuarioDto update(Integer id, Usuario usuario) {
        usuario.setId(id);
        return toDto(usuarioRepository.save(usuario));
    }

    public void delete(Integer id) {
        usuarioRepository.deleteById(id);
    }

    public void agregarExperiencia(Integer usuarioId, int experiencia) {
        usuarioRepository.findById(usuarioId).ifPresent(usuario -> {
            usuario.setExperienciaActual(usuario.getExperienciaActual() + experiencia);

            // Comprobar si sube de nivel
            Nivel nivelActual = usuario.getNivel();
            Nivel siguienteNivel = nivelRepository
                    .findFirstByExperienciaNecesariaGreaterThanOrderByExperienciaNecesariaAsc(usuario.getExperienciaActual())
                    .orElse(null);

            if (siguienteNivel != null && !siguienteNivel.getId().equals(nivelActual.getId())) {
                usuario.setNivel(siguienteNivel);
                usuario.setMonedas(usuario.getMonedas() + siguienteNivel.getRecompensaMonedas());
            }

            usuarioRepository.save(usuario);
        });
    }

    public UsuarioDto buscarOCrearPorNombre(String nombre) {
        Optional<Usuario> existente = usuarioRepository.findByNombre(nombre);
        if (existente.isPresent()) {
            return toDto(existente.get());
        }
        // Si no existe, lo creamos
        Usuario nuevo = new Usuario(nombre);
        Nivel nivel1 = nivelRepository.findById(1).orElse(null);
        nuevo.setNivel(nivel1);
        nuevo.setExperienciaActual(0);
        nuevo.setMonedas(0);
        return toDto(usuarioRepository.save(nuevo));
    }
}