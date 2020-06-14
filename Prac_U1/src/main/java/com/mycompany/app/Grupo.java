package com.mycompany.app;

import java.util.ArrayList;

public class Grupo {

    private String Grupo_ID;
    private ArrayList<String> Materia_ID,Profesor_ID;

    public Grupo(String grupo_ID, ArrayList<String> materia_ID, ArrayList<String> profesor_ID) {
        Grupo_ID = grupo_ID;
        Materia_ID = materia_ID;
        Profesor_ID = profesor_ID;
    }

    public ArrayList<String> getMateria_ID() {
        return Materia_ID;
    }

    public ArrayList<String> getProfesor_ID() {
        return Profesor_ID;
    }

    public String getGrupo_ID() {
        return Grupo_ID;
    }

    public void setGrupo_ID(String grupo_ID) {
        Grupo_ID = grupo_ID;
    }

    public String getMateria_ID(int i) {
        return Materia_ID.get(i);
    }

    public void setMateria_ID(ArrayList<String> materia_ID) {
        Materia_ID = materia_ID;
    }

    public String getProfesor_ID(int i) {
        return Profesor_ID.get(i);
    }

    public void setProfesor_ID(ArrayList<String> profesor_ID) {
        Profesor_ID = profesor_ID;
    }
}
