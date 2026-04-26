package com.example.nexspringboot.service;

import com.example.nexspringboot.dto.LogroDto;
import com.example.nexspringboot.model.Habito;
import com.example.nexspringboot.model.Logro;
import com.example.nexspringboot.model.Usuario;
import com.example.nexspringboot.model.UsuarioLogro;
import com.example.nexspringboot.repository.DiarioRepository;
import com.example.nexspringboot.repository.LogroRepository;
import com.example.nexspringboot.repository.UsuarioLogroRepository;
import com.example.nexspringboot.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LogroService {

    private final LogroRepository repo;
    private final UsuarioLogroRepository usuarioLogroRepository;
    private final DiarioRepository diarioRepository;

    public LogroService(LogroRepository repo, UsuarioLogroRepository usuarioLogroRepository, DiarioRepository diarioRepository) {
        this.repo = repo;
        this.usuarioLogroRepository = usuarioLogroRepository;
        this.diarioRepository = diarioRepository;
    }

    private LogroDto toDto(Logro l) {
        return new LogroDto(l.getId(), l.getNombre(), l.getDescripcion(), l.getRequisito(), l.getExperienciaXDesbloquear());
    }

    public List<LogroDto> getAll() {
        return repo.findAll().stream().map(this::toDto).toList();
    }

    public LogroDto getById(Integer id) {
        return toDto(repo.findById(id).orElseThrow());
    }

    public LogroDto create(Logro logro) {
        return toDto(repo.save(logro));
    }

    public LogroDto update(Integer id, Logro logro) {
        logro.setId(id);
        return toDto(repo.save(logro));
    }

    public void delete(Integer id) {
        repo.deleteById(id);
    }

    public void comprobarLogros(Usuario usuario, List<Habito> habitos) {
        List<Logro> todosLosLogros = repo.findAll();
        List<Integer> logrosYaDesbloqueados = usuario.getLogrosDesbloqueados()
                .stream().map(ul -> ul.getLogro().getId()).toList();

        int habitosCompletados = (int) habitos.stream()
                .filter(h -> h.getProgresoActual() != null
                        && h.getObjetivo() != null
                        && h.getProgresoActual().equals(h.getObjetivo()))
                .count();

        int totalHabitos = (int) habitos.stream().count();

        for (Logro logro : todosLosLogros) {
            if (logrosYaDesbloqueados.contains(logro.getId())) continue;
            if (!"HABITO".equals(logro.getTipo())) continue; // Solo procesa logros de hábitos

            boolean desbloqueado = false;

            // Día perfecto: todos los hábitos completados
            if (logro.getRequisito() == 5 && totalHabitos > 0
                    && habitosCompletados == totalHabitos) {
                desbloqueado = true;
            } else if (logro.getRequisito() != 5
                    && habitosCompletados >= logro.getRequisito()) {
                desbloqueado = true;
            }

            if (desbloqueado) {
                desbloquearLogro(usuario, logro);
            }
        }
    }

    public void comprobarLogrosNivel(Usuario usuario) {
        List<Logro> logrosNivel = repo.findAll().stream()
                .filter(l -> "NIVEL".equals(l.getTipo()))
                .toList();

        List<Integer> logrosYaDesbloqueados = usuario.getLogrosDesbloqueados()
                .stream().map(ul -> ul.getLogro().getId()).toList();

        int nivelActual = usuario.getNivel() != null ? usuario.getNivel().getId() : 0;

        for (Logro logro : logrosNivel) {
            if (logrosYaDesbloqueados.contains(logro.getId())) continue;
            if (nivelActual >= logro.getRequisito()) {
                desbloquearLogro(usuario, logro);
            }
        }
    }

    public void comprobarLogrosFocus(Usuario usuario, int minutosCompletados) {
        List<Logro> logrosFocus = repo.findAll().stream()
                .filter(l -> "FOCUS".equals(l.getTipo()))
                .toList();

        List<Integer> logrosYaDesbloqueados = usuario.getLogrosDesbloqueados()
                .stream().map(ul -> ul.getLogro().getId()).toList();

        for (Logro logro : logrosFocus) {
            if (logrosYaDesbloqueados.contains(logro.getId())) continue;
            if (minutosCompletados >= logro.getRequisito()) {
                desbloquearLogro(usuario, logro);
            }
        }
    }

    public void comprobarLogrosDiario(Usuario usuario) {
        List<Logro> logrosDiario = repo.findAll().stream()
                .filter(l -> "DIARIO".equals(l.getTipo()))
                .toList();

        List<Integer> logrosYaDesbloqueados = usuario.getLogrosDesbloqueados()
                .stream().map(ul -> ul.getLogro().getId()).toList();

        long entradasDiario = diarioRepository.countByUsuario(usuario);

        for (Logro logro : logrosDiario) {
            if (logrosYaDesbloqueados.contains(logro.getId())) continue;
            if (entradasDiario >= logro.getRequisito()) {
                desbloquearLogro(usuario, logro);
            }
        }
    }

    private void desbloquearLogro(Usuario usuario, Logro logro) {
        UsuarioLogro ul = new UsuarioLogro(usuario, logro);
        usuarioLogroRepository.save(ul);
        usuario.setExperienciaActual(
                usuario.getExperienciaActual() + logro.getExperienciaXDesbloquear());
    }
}