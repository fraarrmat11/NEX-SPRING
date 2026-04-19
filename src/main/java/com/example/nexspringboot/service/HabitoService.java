package com.example.nexspringboot.service;

import com.example.nexspringboot.dto.HabitoDto;
import com.example.nexspringboot.dto.HabitoRequest;
import com.example.nexspringboot.model.Habito;
import com.example.nexspringboot.model.Nivel;
import com.example.nexspringboot.model.Usuario;
import com.example.nexspringboot.repository.HabitoRepository;
import com.example.nexspringboot.repository.NivelRepository;
import com.example.nexspringboot.repository.UsuarioRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class HabitoService {

    private final HabitoRepository habitoRepository;
    private final NivelRepository nivelRepository;
    private final UsuarioRepository usuarioRepository;
    private final LogroService logroService;

    public HabitoService(HabitoRepository habitoRepository, NivelRepository nivelRepository, UsuarioRepository usuarioRepository, LogroService logroService) {
        this.habitoRepository = habitoRepository;
        this.nivelRepository = nivelRepository;
        this.usuarioRepository = usuarioRepository;
        this.logroService = logroService;
    }

    private HabitoDto toDto(Habito h) {
        return new HabitoDto(
                h.getId(),
                h.getUsuario() != null ? h.getUsuario().getId() : null,
                h.getNombre(),
                h.getObjetivo(),
                h.getProgresoActual(),
                h.getExperienciaXCompletar(),
                h.getFechaCreacion(),
                h.getActivo()
        );
    }

    public List<HabitoDto> getAll() {
        return habitoRepository.findAll().stream().map(this::toDto).toList();
    }

    public HabitoDto getById(Integer id) {
        return toDto(habitoRepository.findById(id).orElseThrow());
    }

    public List<HabitoDto> getByUsuario(Integer usuarioId) {
        return habitoRepository.findAll().stream()
                .filter(h -> h.getUsuario() != null && h.getUsuario().getId().equals(usuarioId))
                .map(this::toDto)
                .toList();
    }

    public HabitoDto create(HabitoRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId()).orElseThrow();
        Habito habito = new Habito();
        habito.setUsuario(usuario);
        habito.setNombre(request.getNombre());
        habito.setObjetivo(request.getObjetivo());
        habito.setExperienciaXCompletar(request.getExperienciaXCompletar());
        return toDto(habitoRepository.save(habito));
    }

    public HabitoDto update(Integer id, HabitoRequest request) {
        Habito habito = habitoRepository.findById(id).orElseThrow();
        habito.setNombre(request.getNombre());
        habito.setObjetivo(request.getObjetivo());
        habito.setExperienciaXCompletar(request.getExperienciaXCompletar());
        return toDto(habitoRepository.save(habito));
    }

    public HabitoDto incrementar(Integer id) {
        Habito h = habitoRepository.findById(id).orElseThrow();

        if (h.getProgresoActual() < h.getObjetivo()) {
            h.setProgresoActual(h.getProgresoActual() + 1);

            if (h.getProgresoActual().equals(h.getObjetivo())) {
                Usuario u = h.getUsuario();
                u.setExperienciaActual(u.getExperienciaActual() + h.getExperienciaXCompletar());

                Nivel nivelActual = u.getNivel();

                Nivel siguienteNivel = nivelRepository
                        .findFirstByExperienciaNecesariaGreaterThanOrderByExperienciaNecesariaAsc(u.getExperienciaActual())
                        .orElse(null);

                // SUBIR NIVEL -> sumar monedas de recompensa
                if (siguienteNivel != null && !siguienteNivel.getId().equals(nivelActual.getId())) {
                    u.setNivel(siguienteNivel);
                    u.setMonedas(u.getMonedas() + siguienteNivel.getRecompensaMonedas()); // ← nuevo
                }
            }
        }
        logroService.comprobarLogros(h.getUsuario(), habitoRepository.findByUsuario(h.getUsuario()));
        return toDto(habitoRepository.save(h));
    }

    public HabitoDto decrementar(Integer id) {
        Habito h = habitoRepository.findById(id).orElseThrow();

        if (h.getProgresoActual() > 0) {
            boolean estabaCompleto = h.getProgresoActual().equals(h.getObjetivo());
            h.setProgresoActual(h.getProgresoActual() - 1);

            if (estabaCompleto) {
                Usuario u = h.getUsuario();
                u.setExperienciaActual(u.getExperienciaActual() - h.getExperienciaXCompletar());

                if (u.getExperienciaActual() < 0 && u.getNivel().getId() > 1) {
                    Nivel nivelAnterior = nivelRepository.findById(u.getNivel().getId() - 1).orElse(null);
                    if (nivelAnterior != null) {
                        u.setNivel(nivelAnterior);
                        u.setExperienciaActual(nivelAnterior.getExperienciaNecesaria() + u.getExperienciaActual());
                    }
                }
            }
        }

        return toDto(habitoRepository.save(h));
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void resetHabitos() {
        List<Habito> habitos = habitoRepository.findAll();
        habitos.forEach(h -> h.setProgresoActual(0));
        habitoRepository.saveAll(habitos);
    }

    public void delete(Integer id) {
        habitoRepository.deleteById(id);
    }
}