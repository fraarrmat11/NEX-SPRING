package com.example.nexspringboot.controller;

import com.example.nexspringboot.dto.SkinTiendaDto;
import com.example.nexspringboot.model.SkinTienda;
import com.example.nexspringboot.service.SkinTiendaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skins")
public class SkinTiendaController {

    private final SkinTiendaService service;

    public SkinTiendaController(SkinTiendaService service) {
        this.service = service;
    }

    @GetMapping
    public List<SkinTiendaDto> getAll() { return service.getAll(); }

    @GetMapping("/{id}")
    public SkinTiendaDto getById(@PathVariable Integer id) { return service.getById(id); }

    @PostMapping
    public SkinTiendaDto create(@RequestBody SkinTienda skin) { return service.create(skin); }

    @PutMapping("/{id}")
    public SkinTiendaDto update(@PathVariable Integer id, @RequestBody SkinTienda skin) { return service.update(id, skin); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) { service.delete(id); }
}