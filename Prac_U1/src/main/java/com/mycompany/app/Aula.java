package com.mycompany.app;

public class Aula {

    private String Aula_ID, Nombre,Tipo;
    private int Capacidad;

    public Aula(String aula_ID, String nombre, int capacidad) {
        Aula_ID = aula_ID;
        Nombre = nombre;
        Capacidad = capacidad;
    }

    public String getAula_ID() {
        return Aula_ID;
    }

    public void setAula_ID(String aula_ID) {
        Aula_ID = aula_ID;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public int getCapacidad() {
        return Capacidad;
    }

    public void setCapacidad(int capacidad) {
        Capacidad = capacidad;
    }
}
