package com.example.nexspringboot.controller;

import com.example.nexspringboot.dto.UsuarioSkinDto;
import com.example.nexspringboot.dto.UsuarioSkinRequest;
import com.example.nexspringboot.service.UsuarioSkinService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario-skins")
public class UsuarioSkinController {

    private final UsuarioSkinService service;

    public UsuarioSkinController(UsuarioSkinService service) {
        this.service = service;
    }

    @GetMapping
    public List<UsuarioSkinDto> getAll() { return service.getAll(); }

    @GetMapping("/{id}")
    public UsuarioSkinDto getById(@PathVariable Integer id) { return service.getById(id); }

    @GetMapping("/usuario/{usuarioId}")
    public List<UsuarioSkinDto> getByUsuario(@PathVariable Integer usuarioId) {
        return service.getByUsuario(usuarioId);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody UsuarioSkinRequest request) {
        try {
            return ResponseEntity.ok(service.create(request));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) { service.delete(id); }
}