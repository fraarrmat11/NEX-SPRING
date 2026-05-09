package com.example.nexspringboot.service;

import com.example.nexspringboot.dto.NivelDto;
import com.example.nexspringboot.dto.UsuarioDto;
import com.example.nexspringboot.model.Nivel;
import com.example.nexspringboot.model.Usuario;
import com.example.nexspringboot.repository.NivelRepository;
import com.example.nexspringboot.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final NivelRepository nivelRepository;
    private final LogroService logroService;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, NivelRepository nivelRepository, LogroService logroService, BCryptPasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.nivelRepository = nivelRepository;
        this.logroService = logroService;
        this.passwordEncoder = passwordEncoder;
    }

    private NivelDto toNivelDto(Nivel n) {
        return new NivelDto(n.getId(), n.getNombre(), n.getExperienciaNecesaria(), n.getRecompensaMonedas());
    }

    private UsuarioDto toDto(Usuario u) {
        Integer xpSiguienteNivel = nivelRepository
                .findFirstByExperienciaNecesariaGreaterThanOrderByExperienciaNecesariaAsc(
                        u.getNivel() != null ? u.getNivel().getExperienciaNecesaria() : 0)
                .map(Nivel::getExperienciaNecesaria)
                .orElse(null); // null = nivel máximo

        return new UsuarioDto(
                u.getId(),
                u.getNombre(),
                u.getExperienciaActual(),
                u.getMonedas(),
                u.getNivel() != null ? toNivelDto(u.getNivel()) : null,
                u.getFechaCreacion(),
                xpSiguienteNivel
        );
    }

    public List<UsuarioDto> getAll() {
        return usuarioRepository.findAll().stream().map(this::toDto).toList();
    }

    public UsuarioDto getById(Integer id) {
        return usuarioRepository.findById(id)
                .map(this::toDto)
                .orElse(null);
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

            // Buscar el nivel correcto para la XP actual
            // El nivel correcto es el último cuya experienciaNecesaria <= xpActual
            nivelRepository.findAll().stream()
                    .filter(n -> n.getExperienciaNecesaria() <= usuario.getExperienciaActual())
                    .max(java.util.Comparator.comparingInt(Nivel::getExperienciaNecesaria))
                    .ifPresent(nivelCorrecto -> {
                        Nivel nivelActual = usuario.getNivel();
                        if (!nivelCorrecto.getId().equals(nivelActual.getId())) {
                            usuario.setNivel(nivelCorrecto);
                            usuario.setMonedas(usuario.getMonedas() + nivelCorrecto.getRecompensaMonedas());
                            logroService.comprobarLogrosNivel(usuario);
                        }
                    });

            usuarioRepository.save(usuario);
        });
    }

    public UsuarioDto registrar(String nombre, String contrasena) {
        // Comprobar que el nombre no existe ya
        if (usuarioRepository.findByNombre(nombre).isPresent()) {
            return null; // nombre ya en uso
        }
        Usuario nuevo = new Usuario(nombre, passwordEncoder.encode(contrasena));
        Nivel nivel1 = nivelRepository.findById(1).orElse(null);
        nuevo.setNivel(nivel1);
        nuevo.setExperienciaActual(0);
        nuevo.setMonedas(0);
        return toDto(usuarioRepository.save(nuevo));
    }

    public UsuarioDto login(String nombre, String contrasena) {
        return usuarioRepository.findByNombre(nombre)
                .filter(u -> passwordEncoder.matches(contrasena, u.getContrasena()))
                .map(this::toDto)
                .orElse(null); // null = credenciales incorrectas
    }

    public void completarFocus(Integer usuarioId, int minutos) {
        usuarioRepository.findById(usuarioId).ifPresent(usuario -> {
            // Sumar XP
            usuario.setExperienciaActual(usuario.getExperienciaActual() + minutos);

            // Comprobar subida de nivel
            nivelRepository.findAll().stream()
                    .filter(n -> n.getExperienciaNecesaria() <= usuario.getExperienciaActual())
                    .max(java.util.Comparator.comparingInt(Nivel::getExperienciaNecesaria))
                    .ifPresent(nivelCorrecto -> {
                        if (!nivelCorrecto.getId().equals(usuario.getNivel().getId())) {
                            usuario.setNivel(nivelCorrecto);
                            usuario.setMonedas(usuario.getMonedas() + nivelCorrecto.getRecompensaMonedas());
                        }
                    });

            // Comprobar logros de nivel y focus
            logroService.comprobarLogrosNivel(usuario);
            logroService.comprobarLogrosFocus(usuario, minutos);

            usuarioRepository.save(usuario);
        });
    }
}