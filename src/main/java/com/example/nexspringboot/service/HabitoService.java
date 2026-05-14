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

    //recoge los habitos del usuario por su id para mostrarlos en la aplicación
    public List<HabitoDto> getByUsuario(Integer usuarioId) {
        return habitoRepository.findAll().stream()
                .filter(h -> h.getUsuario() != null && h.getUsuario().getId().equals(usuarioId))
                .map(this::toDto)
                .toList();
    }
    //crea un habito a partir de unos datos simplificados
    public HabitoDto create(HabitoRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId()).orElseThrow();
        Habito habito = new Habito();
        habito.setUsuario(usuario);
        habito.setNombre(request.getNombre());
        habito.setObjetivo(request.getObjetivo());
        habito.setExperienciaXCompletar(request.getExperienciaXCompletar());
        return toDto(habitoRepository.save(habito));
    }
    //actualiza los datos de un habito a partir del id del habito y los datos de request
    public HabitoDto update(Integer id, HabitoRequest request) {
        Habito habito = habitoRepository.findById(id).orElseThrow();
        habito.setNombre(request.getNombre());
        habito.setObjetivo(request.getObjetivo());
        habito.setExperienciaXCompletar(request.getExperienciaXCompletar());
        return toDto(habitoRepository.save(habito));
    }
    //incrementar contador de habitos, comprueba si se completa, se sube de nivel, etc
    public HabitoDto incrementar(Integer id) {
        Habito h = habitoRepository.findById(id).orElseThrow();
        //no sobrepasa objetivo
        if (h.getProgresoActual() < h.getObjetivo()) {
            h.setProgresoActual(h.getProgresoActual() + 1);
            //si se llega al objetivo suma experiencia al usuario
            if (h.getProgresoActual().equals(h.getObjetivo())) {
                Usuario u = h.getUsuario();
                u.setExperienciaActual(u.getExperienciaActual() + h.getExperienciaXCompletar());
                // Busca el nivel más alto alcanzable según la XP actual (ayuda de ia)
                nivelRepository.findAll().stream()
                        .filter(n -> n.getExperienciaNecesaria() <= u.getExperienciaActual())

                        // Se queda con el nivel válido con mayor requisito de experiencia
                        .max(java.util.Comparator.comparingInt(Nivel::getExperienciaNecesaria))

                        .ifPresent(nivelCorrecto -> {
                            // Solo actualiza si realmente cambia de nivel
                            if (!nivelCorrecto.getId().equals(u.getNivel().getId())) {
                                u.setNivel(nivelCorrecto);

                                // Aplica recompensa al subir de nivel
                                u.setMonedas(u.getMonedas() + nivelCorrecto.getRecompensaMonedas());
                            }
                        });
            }
        }
        //comprueba si al incrementar el hábito se ha completado algún logro
        logroService.comprobarLogros(h.getUsuario(), habitoRepository.findByUsuario(h.getUsuario()));
        return toDto(habitoRepository.save(h));
    }

    public HabitoDto decrementar(Integer id) {
        Habito h = habitoRepository.findById(id).orElseThrow();
        //no baja de 0
        if (h.getProgresoActual() > 0) {
            boolean estabaCompleto = h.getProgresoActual().equals(h.getObjetivo());
            h.setProgresoActual(h.getProgresoActual() - 1);
            //si estaba completo, resta al usuario la experiencia
            if (estabaCompleto) {
                Usuario u = h.getUsuario();
                u.setExperienciaActual(u.getExperienciaActual() - h.getExperienciaXCompletar());
                //si la XP cae por debajo del umbral del nivel actual y no está en nivel 1, baja de nivel
                if (u.getExperienciaActual() < u.getNivel().getExperienciaNecesaria() && u.getNivel().getId() > 1) {
                    Nivel nivelAnterior = nivelRepository.findById(u.getNivel().getId() - 1).orElse(null);
                    if (nivelAnterior != null) {
                        u.setNivel(nivelAnterior);
                    }
                }
            }
        }

        return toDto(habitoRepository.save(h));
    }
    //resetea los hábitos a las 00:00
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