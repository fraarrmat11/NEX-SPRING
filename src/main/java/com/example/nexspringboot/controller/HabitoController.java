package com.example.nexspringboot.controller;

import com.example.nexspringboot.dto.HabitoDto;
import com.example.nexspringboot.dto.HabitoRequest;
import com.example.nexspringboot.service.HabitoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/habitos")
public class HabitoController {

    private final HabitoService habitoService;

    public HabitoController(HabitoService habitoService) {
        this.habitoService = habitoService;
    }

    @GetMapping
    public List<HabitoDto> getAll() { return habitoService.getAll(); }

    @GetMapping("/{id}")
    public HabitoDto getById(@PathVariable Integer id) { return habitoService.getById(id); }

    @GetMapping("/usuario/{usuarioId}")
    public List<HabitoDto> getByUsuario(@PathVariable Integer usuarioId) {
        return habitoService.getByUsuario(usuarioId);
    }

    @PostMapping
    public HabitoDto create(@RequestBody HabitoRequest request) { return habitoService.create(request); }

    @PutMapping("/{id}")
    public HabitoDto update(@PathVariable Integer id, @RequestBody HabitoRequest request) { return habitoService.update(id, request); }

    @PutMapping("/{id}/incrementar")
    public HabitoDto incrementar(@PathVariable Integer id) { return habitoService.incrementar(id); }

    @PutMapping("/{id}/decrementar")
    public HabitoDto decrementar(@PathVariable Integer id) { return habitoService.decrementar(id); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) { habitoService.delete(id); }
}