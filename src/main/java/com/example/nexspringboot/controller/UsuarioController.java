package com.example.nexspringboot.controller;

import com.example.nexspringboot.dto.UsuarioDto;
import com.example.nexspringboot.model.Usuario;
import com.example.nexspringboot.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<UsuarioDto> getAll() { return usuarioService.getAll(); }

    @GetMapping("/{id}")
    public UsuarioDto getById(@PathVariable Integer id) { return usuarioService.getById(id); }

    @PostMapping
    public UsuarioDto crearUsuario(@RequestBody Usuario usuario) { return usuarioService.crearUsuario(usuario); }

    @PutMapping("/{id}")
    public UsuarioDto update(@PathVariable Integer id, @RequestBody Usuario usuario) { return usuarioService.update(id, usuario); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) { usuarioService.delete(id); }

    // ← nuevo
    @PutMapping("/{id}/experiencia")
    public void agregarExperiencia(@PathVariable Integer id, @RequestParam int cantidad) {
        usuarioService.agregarExperiencia(id, cantidad);
    }

    @GetMapping("/buscar")
    public UsuarioDto buscarOCrearPorNombre(@RequestParam String nombre) {
        return usuarioService.buscarOCrearPorNombre(nombre);
    }
}