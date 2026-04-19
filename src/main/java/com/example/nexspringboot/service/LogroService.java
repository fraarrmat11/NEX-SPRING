package com.example.nexspringboot.service;

import com.example.nexspringboot.dto.LogroDto;
import com.example.nexspringboot.model.Habito;
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
public class LogroService {

    private final LogroRepository repo;
    private final UsuarioLogroRepository usuarioLogroRepository;

    public LogroService(LogroRepository repo, UsuarioLogroRepository usuarioLogroRepository) {
        this.repo = repo;
        this.usuarioLogroRepository = usuarioLogroRepository;
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

        int habitosCompletadosHoy = (int) habitos.stream()
                .filter(h -> h.getProgresoActual() != null
                        && h.getObjetivo() != null
                        && h.getProgresoActual().equals(h.getObjetivo()))
                .count();

        for (Logro logro : todosLosLogros) {
            if (logrosYaDesbloqueados.contains(logro.getId())) continue;

            boolean desbloqueado = false;

            // El requisito representa número de hábitos completados simultáneamente
            if (habitosCompletadosHoy >= logro.getRequisito()) {
                desbloqueado = true;
            }

            if (desbloqueado) {
                UsuarioLogro ul = new UsuarioLogro(usuario, logro);
                usuarioLogroRepository.save(ul);
                usuario.setExperienciaActual(
                        usuario.getExperienciaActual() + logro.getExperienciaXDesbloquear());
            }
        }
    }
}