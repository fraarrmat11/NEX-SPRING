package com.example.nexspringboot.controller;

import com.example.nexspringboot.dto.UsuarioLogroDto;
import com.example.nexspringboot.dto.UsuarioLogroRequest;
import com.example.nexspringboot.service.UsuarioLogroService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario-logros")
public class UsuarioLogroController {

    private final UsuarioLogroService service;

    public UsuarioLogroController(UsuarioLogroService service) {
        this.service = service;
    }

    @GetMapping
    public List<UsuarioLogroDto> getAll() { return service.getAll(); }

    @GetMapping("/{id}")
    public UsuarioLogroDto getById(@PathVariable Integer id) { return service.getById(id); }

    @GetMapping("/usuario/{usuarioId}")
    public List<UsuarioLogroDto> getByUsuario(@PathVariable Integer usuarioId) {
        return service.getByUsuario(usuarioId);
    }

    @PostMapping
    public UsuarioLogroDto create(@RequestBody UsuarioLogroRequest request) { return service.create(request); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) { service.delete(id); }
}