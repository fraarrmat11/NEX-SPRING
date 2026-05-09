package com.example.nexspringboot.controller;

import com.example.nexspringboot.dto.LoginRequest;
import com.example.nexspringboot.dto.UsuarioDto;
import com.example.nexspringboot.model.Usuario;
import com.example.nexspringboot.service.UsuarioService;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/login")
    public ResponseEntity<UsuarioDto> login(@RequestBody LoginRequest loginRequest){
        UsuarioDto dto = usuarioService.login(loginRequest.getNombre(), loginRequest.getContrasena());
        if(dto == null)
            return ResponseEntity.status(401).build();
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/registrar")
    public ResponseEntity<UsuarioDto> registrar(@RequestBody LoginRequest loginRequest){
        UsuarioDto dto = usuarioService.registrar(loginRequest.getNombre(), loginRequest.getContrasena());
        if (dto == null)
            return ResponseEntity.status(409).build();//nombre ya en uso
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public UsuarioDto getById(@PathVariable Integer id) { return usuarioService.getById(id); }

    @PostMapping
    public UsuarioDto crearUsuario(@RequestBody Usuario usuario) { return usuarioService.crearUsuario(usuario); }

    @PutMapping("/{id}")
    public UsuarioDto update(@PathVariable Integer id, @RequestBody Usuario usuario) { return usuarioService.update(id, usuario); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) { usuarioService.delete(id); }

    @PutMapping("/{id}/experiencia")
    public void agregarExperiencia(@PathVariable Integer id, @RequestParam int cantidad) {
        usuarioService.agregarExperiencia(id, cantidad);
    }

    @PutMapping("/{id}/focus")
    public void completarFocus(@PathVariable Integer id, @RequestParam int minutos) {
        usuarioService.completarFocus(id, minutos);
    }
}