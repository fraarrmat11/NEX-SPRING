package com.example.nexspringboot.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String nombre;
    private String contrasena;
}
