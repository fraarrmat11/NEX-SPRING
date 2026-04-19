package com.example.nexspringboot.controller;

import com.example.nexspringboot.dto.NivelDto;
import com.example.nexspringboot.model.Nivel;
import com.example.nexspringboot.service.NivelService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/niveles")
public class NivelController {

    private final NivelService service;

    public NivelController(NivelService service) {
        this.service = service;
    }

    @GetMapping
    public List<NivelDto> getAll() { return service.getAll(); }

    @GetMapping("/{id}")
    public NivelDto getById(@PathVariable Integer id) { return service.getById(id); }

    @PostMapping
    public NivelDto create(@RequestBody Nivel nivel) { return service.create(nivel); }

    @PutMapping("/{id}")
    public NivelDto update(@PathVariable Integer id, @RequestBody Nivel nivel) { return service.update(id, nivel); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) { service.delete(id); }
}