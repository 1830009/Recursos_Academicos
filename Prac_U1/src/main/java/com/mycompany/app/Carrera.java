package com.mycompany.app;

public class Carrera {

    private String Carrera_ID,Nombre;

    public Carrera(String carrera_ID, String nombre) {
        Carrera_ID = carrera_ID;
        Nombre = nombre;
    }

    public String getCarrera_ID() {
        return Carrera_ID;
    }

    public void setCarrera_ID(String carrera_ID) {
        Carrera_ID = carrera_ID;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}
