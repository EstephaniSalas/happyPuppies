package com.example.happypuppies;

public class User {
    private String nombre_usuario;
    private String email;
    private String celular;
    private String codigo_postal;

    // Constructor vacío necesario para Firebase
    public User() {
    }

    // Constructor
    public User(String nombre_usuario, String email, String celular, String codigo_postal) {
        this.nombre_usuario = nombre_usuario;
        this.email = email;
        this.celular = celular;
        this.codigo_postal = codigo_postal;
    }

    // Getters y Setters
    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCodigo_postal() {
        return codigo_postal;
    }

    public void setCodigo_postal(String codigo_postal) {
        this.codigo_postal = codigo_postal;
    }
}
