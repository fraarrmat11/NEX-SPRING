package com.example.nexspringboot.controller;

import com.example.nexspringboot.dto.RegistroHabitoDto;
import com.example.nexspringboot.dto.RegistroHabitoRequest;
import com.example.nexspringboot.service.RegistroHabitoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registros")
public class RegistroHabitoController {

    private final RegistroHabitoService service;

    public RegistroHabitoController(RegistroHabitoService service) {
        this.service = service;
    }

    @GetMapping
    public List<RegistroHabitoDto> getAll() { return service.getAll(); }

    @PostMapping
    public RegistroHabitoDto create(@RequestBody RegistroHabitoRequest request) { return service.create(request); }
}