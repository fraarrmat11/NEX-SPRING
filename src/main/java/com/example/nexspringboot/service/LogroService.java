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

        // IDs de logros que el usuario ya tiene (para no repetirlos)
        List<Integer> logrosYaDesbloqueados = usuario.getLogrosDesbloqueados()
                .stream().map(ul -> ul.getLogro().getId()).toList();

        // Cuenta hábitos completados (progreso == objetivo)
        int habitosCompletados = (int) habitos.stream()
                .filter(h -> h.getProgresoActual().equals(h.getObjetivo()))
                .count();

        int totalHabitos = (int) habitos.stream().count();


        for (Logro logro : todosLosLogros) {
            //si está desbloqueado o no es de tipo hábito no lo procesa
            if (logrosYaDesbloqueados.contains(logro.getId())) continue;
            if (!"HABITO".equals(logro.getTipo())) continue;

            boolean desbloqueado = false;

            // Caso especial: requisito 5 = día perfecto
            // Coge los hábitos que tiene el usuario y si son los mismos que los completados se consigue el logro
            if (logro.getRequisito() == 5 && totalHabitos > 0
                    && habitosCompletados == totalHabitos) {
                desbloqueado = true;

                // Resto: cumple si completa X hábitos
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
        //guarda todos los logros con el tipo nivel por su atributo tipo
        List<Logro> logrosNivel = repo.findAll().stream()
                .filter(l -> "NIVEL".equals(l.getTipo()))
                .toList();
        //guarda los id de los logros desbloquados del usuario
        List<Integer> logrosYaDesbloqueados = usuario.getLogrosDesbloqueados()
                .stream().map(ul -> ul.getLogro().getId()).toList();
        //coge el nivel del usuario (si no es null, coge su id, si lo es, asinga 0)
        int nivelActual = usuario.getNivel() != null ? usuario.getNivel().getId() : 0;

        for (Logro logro : logrosNivel) {
            //si el logro está desbloqueado no lo procesa
            if (logrosYaDesbloqueados.contains(logro.getId())) continue;
            //si el nivel es igual o superior al requisito se desbloquea
            if (nivelActual >= logro.getRequisito()) {
                desbloquearLogro(usuario, logro);
            }
        }
    }

    public void comprobarLogrosFocus(Usuario usuario, int minutosCompletados) {
        //guarda todos los logros con el tipo focus por su atributo tipo
        List<Logro> logrosFocus = repo.findAll().stream()
                .filter(l -> "FOCUS".equals(l.getTipo()))
                .toList();
        //guarda los id de los logros desbloqueados
        List<Integer> logrosYaDesbloqueados = usuario.getLogrosDesbloqueados()
                .stream().map(ul -> ul.getLogro().getId()).toList();

        for (Logro logro : logrosFocus) {
            //si completado, no procesa
            if (logrosYaDesbloqueados.contains(logro.getId())) continue;
            //si los minutos completados son mayor que el requisito, desbloquea
            if (minutosCompletados >= logro.getRequisito()) {
                desbloquearLogro(usuario, logro);
            }
        }
    }

    public void comprobarLogrosDiario(Usuario usuario) {
        //guarda todos los logros con el tipo diario por su atributo tipo
        List<Logro> logrosDiario = repo.findAll().stream()
                .filter(l -> "DIARIO".equals(l.getTipo()))
                .toList();
        //guarda id de logros desbloqueados
        List<Integer> logrosYaDesbloqueados = usuario.getLogrosDesbloqueados()
                .stream().map(ul -> ul.getLogro().getId()).toList();
        //guarda las entradas de diario por el usuario
        long entradasDiario = diarioRepository.countByUsuario(usuario);

        for (Logro logro : logrosDiario) {
            //si completado, no procesa
            if (logrosYaDesbloqueados.contains(logro.getId())) continue;
            //si el nº de entradas es mayor que el requisito, desbloquea
            if (entradasDiario >= logro.getRequisito()) {
                desbloquearLogro(usuario, logro);
            }
        }
    }

    private void desbloquearLogro(Usuario usuario, Logro logro) {
        //crea nueva entrada en UsuarioLogro, así el usuario guarda los logros completados
        UsuarioLogro ul = new UsuarioLogro(usuario, logro);
        usuarioLogroRepository.save(ul);
        //suma experiencia por completar el logro
        usuario.setExperienciaActual(
                usuario.getExperienciaActual() + logro.getExperienciaXDesbloquear());
    }
}