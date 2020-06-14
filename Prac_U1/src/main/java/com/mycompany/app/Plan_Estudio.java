package com.mycompany.app;

public class Plan_Estudio {
    private String Plan_ID,Nivel,Carrera_ID;

    public Plan_Estudio(String plan_ID,String nivel, String carrera_ID) {
        Plan_ID = plan_ID;
        Nivel=nivel;
        Carrera_ID = carrera_ID;
    }

    public String getPlan_ID() {
        return Plan_ID;
    }

    public void setPlan_ID(String plan_ID) {
        Plan_ID = plan_ID;
    }

    public String getNivel() {
        return Nivel;
    }

    public void setNivel(String nivel) {
        Nivel = nivel;
    }

    public String getCarrera_ID() {
        return Carrera_ID;
    }

    public void setCarrera_ID(String carrera_ID) {
        Carrera_ID = carrera_ID;
    }
}
