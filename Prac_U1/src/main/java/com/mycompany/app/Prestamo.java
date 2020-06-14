package com.mycompany.app;

public class Prestamo {
    private String Profesor_ID, Carrera_ID;

    public Prestamo(String profesor_ID, String carrera_ID) {
        Profesor_ID = profesor_ID;
        Carrera_ID = carrera_ID;
    }

    public String getProfesor_ID() {
        return Profesor_ID;
    }

    public void setProfesor_ID(String profesor_ID) {
        Profesor_ID = profesor_ID;
    }

    public String getCarrera_ID() {
        return Carrera_ID;
    }

    public void setCarrera_ID(String carrera_ID) {
        Carrera_ID = carrera_ID;
    }
}
