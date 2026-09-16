package com.example.actividadpracticapaesemana5.model;

public class Usuario {
    private String nombreDeUsuario;
    private String contrasenia;

    public Usuario(String nombreDeUsuario, String contrasenia) {
        this.nombreDeUsuario = nombreDeUsuario;
        this.contrasenia = contrasenia;
    }

    public String getNombreDeUsuario() {
        return nombreDeUsuario;
    }

    public void setNombreDeUsuario(String nombreDeUsuario) {
        this.nombreDeUsuario = nombreDeUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
}
