package com.mycompany.app;

import java.util.ArrayList;

public class Disponibilidad {

    private int Dia;
    private ArrayList<Integer> Horas;
    private String Profesor_ID;

    public Disponibilidad(int dia, String profesor_ID, ArrayList<Integer> horas) {
        Dia = dia;
        Horas = horas;
        Profesor_ID = profesor_ID;
    }

    public Disponibilidad(int dia, String profesor_ID, String horas) {
        Dia = dia;
        String [] C;
        C=horas.split(";");
        ArrayList<Integer> Horasaux= new ArrayList<>();
        for(int i=0;i<C.length;i++)
        Horasaux.add(Integer.parseInt(C[i]));

        Horas=Horasaux;
        Profesor_ID = profesor_ID;
    }

    public int getDia() {
        return Dia;
    }

    public void setDia(int dia) {
        Dia = dia;
    }

    public int getHoras(int i) {
        return Horas.get(i);
    }

    public ArrayList<Integer> getHoras() {
        return Horas;
    }

    public void setHoras(ArrayList<Integer> horas) {
        Horas = horas;
    }

    public String getProfesor_ID() {
        return Profesor_ID;
    }

    public void setProfesor_ID(String profesor_ID) {
        Profesor_ID = profesor_ID;
    }
}
