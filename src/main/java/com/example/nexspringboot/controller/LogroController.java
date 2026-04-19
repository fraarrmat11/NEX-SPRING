package com.example.nexspringboot.controller;

import com.example.nexspringboot.dto.LogroDto;
import com.example.nexspringboot.model.Logro;
import com.example.nexspringboot.service.LogroService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logros")
public class LogroController {

    private final LogroService logroService;

    public LogroController(LogroService logroService) {
        this.logroService = logroService;
    }

    @GetMapping
    public List<LogroDto> getAll() { return logroService.getAll(); }

    @GetMapping("/{id}")
    public LogroDto getById(@PathVariable Integer id) { return logroService.getById(id); }

    @PostMapping
    public LogroDto create(@RequestBody Logro logro) { return logroService.create(logro); }

    @PutMapping("/{id}")
    public LogroDto update(@PathVariable Integer id, @RequestBody Logro logro) { return logroService.update(id, logro); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) { logroService.delete(id); }
}