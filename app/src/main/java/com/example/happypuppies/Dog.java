package com.example.happypuppies;

public class Dog {
    private String nombre_perro;
    private String raza;
    private String foto_url;

    // Constructor vacío necesario para Firebase
    public Dog() {
    }

    // Constructor
    public Dog(String nombre_perro, String raza, String foto_url) {
        this.nombre_perro = nombre_perro;
        this.raza = raza;
        this.foto_url = foto_url;
    }

    // Getters y Setters
    public String getNombre_perro() {
        return nombre_perro;
    }

    public void setNombre_perro(String nombre_perro) {
        this.nombre_perro = nombre_perro;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getFoto_url() {
        return foto_url;
    }

    public void setFoto_url(String foto_url) {
        this.foto_url = foto_url;
    }
}
