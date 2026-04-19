package com.example.nexspringboot.controller;

import com.example.nexspringboot.dto.DiarioDto;
import com.example.nexspringboot.dto.DiarioRequest;
import com.example.nexspringboot.service.DiarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/diarios")
public class DiarioController {

    private final DiarioService diarioService;

    public DiarioController(DiarioService diarioService) {
        this.diarioService = diarioService;
    }

    @GetMapping
    public List<DiarioDto> getAll() { return diarioService.getAll(); }

    @GetMapping("/{id}")
    public DiarioDto getById(@PathVariable Integer id) { return diarioService.getById(id); }

    @GetMapping("/usuario/{usuarioId}")
    public List<DiarioDto> getByUsuario(@PathVariable Integer usuarioId) {
        return diarioService.getByUsuario(usuarioId);
    }

    @PostMapping
    public DiarioDto create(@RequestBody DiarioRequest request) { return diarioService.create(request); }

    @PutMapping("/{id}")
    public DiarioDto update(@PathVariable Integer id, @RequestBody DiarioRequest request) { return diarioService.update(id, request); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) { diarioService.delete(id); }
}