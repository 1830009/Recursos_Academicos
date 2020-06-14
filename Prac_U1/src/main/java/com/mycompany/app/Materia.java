package com.mycompany.app;

import java.io.IOException;

public class Materia {

    private String Materia_ID,Nombre,Plan_ID,Tipo_Mat;
    private int Creditos,Cuatrimestre,Posicion,Hrs_Sem;

    public Materia(String materia_ID, String nombre, int creditos, int cuatrimestre, int hrs_Sem,String plan_ID) {
        Materia_ID = materia_ID;
        Nombre = nombre;
        Plan_ID = plan_ID;
        Creditos = creditos;
        Cuatrimestre = cuatrimestre;
        Hrs_Sem = hrs_Sem;
    }

    public String getMateria_ID() {
        return Materia_ID;
    }

    public void setMateria_ID(String materia_ID) {
        Materia_ID = materia_ID;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getPlan_ID() {
        return Plan_ID;
    }

    public void setPlan_ID(String plan_ID) {
        Plan_ID = plan_ID;
    }

    public String getTipo_Mat() {
        return Tipo_Mat;
    }

    public void setTipo_Mat(String tipo_Mat) {
        Tipo_Mat = tipo_Mat;
    }

    public int getCreditos() {
        return Creditos;
    }

    public void setCreditos(int creditos) {
        Creditos = creditos;
    }

    public int getCuatrimestre() {
        return Cuatrimestre;
    }

    public void setCuatrimestre(int cuatrimestre) {
        Cuatrimestre = cuatrimestre;
    }

    public int getPosicion() {
        return Posicion;
    }

    public void setPosicion(int posicion) {
        Posicion = posicion;
    }

    public int getHrs_Sem() {
        return Hrs_Sem;
    }

    public void setHrs_Sem(int hrs_Sem) {
        Hrs_Sem = hrs_Sem;
    }

}
