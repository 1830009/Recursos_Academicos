package com.mycompany.app;

public class Profesor {

    private String Profesor_ID,Nombre,Carrera,Grado,Contrato;

    public Profesor(String profesor_ID, String nombre, String carrera, String grado, String contrato) {
        Profesor_ID = profesor_ID;
        Nombre = nombre;
        Carrera = carrera;
        Grado = grado;
        Contrato = contrato;
    }

    public String getProfesor_ID() {
        return Profesor_ID;
    }

    public void setProfesor_ID(String Profesor_ID) {
        Profesor_ID = Profesor_ID;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getCarrera() {
        return Carrera;
    }

    public void setCarrera(String carrera) {
        Carrera = carrera;
    }

    public String getGrado() {
        return Grado;
    }

    public void setGrado(String grado) {
        Grado = grado;
    }

    public String getContrato() {
        return Contrato;
    }

    public void setContrato(String contrato) {
        Contrato = contrato;
    }
}