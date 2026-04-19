package com.example.nexspringboot.init;

import com.example.nexspringboot.model.*;
import com.example.nexspringboot.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {
//SIGUIENTE PASO: INSERTAR TODOS LOS NIVELES, HÁBITOS, LOGROS Y SKINS DEL JUEGO
    private final NivelRepository nivelRepository;
    private final UsuarioRepository usuarioRepository;
    private final HabitoRepository habitoRepository;
    private final RegistroHabitoRepository registroHabitoRepository;
    private final LogroRepository logroRepository;
    private final UsuarioLogroRepository usuarioLogroRepository;
    private final SkinTiendaRepository skinTiendaRepository;
    private final UsuarioSkinRepository usuarioSkinRepository;
    private final DiarioRepository diarioRepository;

    public DataInitializer(NivelRepository nivelRepository,
                           UsuarioRepository usuarioRepository,
                           HabitoRepository habitoRepository,
                           RegistroHabitoRepository registroHabitoRepository,
                           LogroRepository logroRepository,
                           UsuarioLogroRepository usuarioLogroRepository,
                           SkinTiendaRepository skinTiendaRepository,
                           UsuarioSkinRepository usuarioSkinRepository,
                           DiarioRepository diarioRepository) {
        this.nivelRepository = nivelRepository;
        this.usuarioRepository = usuarioRepository;
        this.habitoRepository = habitoRepository;
        this.registroHabitoRepository = registroHabitoRepository;
        this.logroRepository = logroRepository;
        this.usuarioLogroRepository = usuarioLogroRepository;
        this.skinTiendaRepository = skinTiendaRepository;
        this.usuarioSkinRepository = usuarioSkinRepository;
        this.diarioRepository = diarioRepository;
    }

    @Override
    public void run(String... args) {



        /*System.out.println("Inicializando datos de prueba...");

        Nivel nivel = new Nivel("Nivel 1", 0, 0);
        nivelRepository.save(nivel);

        Usuario usuario = new Usuario("UsuarioPrueba");
        usuarioRepository.save(usuario);

        Habito habito = new Habito(usuario, "Beber agua", 8);
        habitoRepository.save(habito);

        RegistroHabito registro = new RegistroHabito(habito, LocalDate.now(), 1, true);
        registroHabitoRepository.save(registro);

        Logro logro = new Logro("Primer hábito", "Completa tu primer hábito", 1, 10);
        logroRepository.save(logro);

        UsuarioLogro usuarioLogro = new UsuarioLogro(usuario, logro);
        usuarioLogroRepository.save(usuarioLogro);

        SkinTienda skin = new SkinTienda("Skin Roja", 100, "#FF0000", "/images/skin_roja.png", "Descripción de la skin");
        skinTiendaRepository.save(skin);

        UsuarioSkin usuarioSkin = new UsuarioSkin(usuario, skin);
        usuarioSkinRepository.save(usuarioSkin);

        Diario diario = new Diario(usuario, LocalDate.now(), "Hoy hice mi primer hábito");
        diarioRepository.save(diario);

        System.out.println("Datos de prueba guardados correctamente en la BD");*/
    }
}
