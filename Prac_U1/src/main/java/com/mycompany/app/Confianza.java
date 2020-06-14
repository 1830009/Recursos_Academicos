package com.mycompany.app;

public class Confianza {
    private String Materia_ID, Plan_ID, Profesor_ID;
    private int pts_Conf_Prof,pts_Conf_Dir;

    public Confianza(String materia_ID, String plan_ID, String profesor_ID, int pts_Conf_Prof, int pts_Conf_Dir) {
        Materia_ID = materia_ID;
        Plan_ID = plan_ID;
        Profesor_ID = profesor_ID;
        this.pts_Conf_Prof = pts_Conf_Prof;
        this.pts_Conf_Dir = pts_Conf_Dir;
    }

    public String getMateria_ID() {
        return Materia_ID;
    }

    public void setMateria_ID(String materia_ID) {
        Materia_ID = materia_ID;
    }

    public String getPlan_ID() {
        return Plan_ID;
    }

    public void setPlan_ID(String plan_ID) {
        Plan_ID = plan_ID;
    }

    public String getProfesor_ID() {
        return Profesor_ID;
    }

    public void setProfesor_ID(String profesor_ID) {
        Profesor_ID = profesor_ID;
    }

    public int getPts_Conf_Prof() {
        return pts_Conf_Prof;
    }

    public void setPts_Conf_Prof(int pts_Conf_Prof) {
        this.pts_Conf_Prof = pts_Conf_Prof;
    }

    public int getPts_Conf_Dir() {
        return pts_Conf_Dir;
    }

    public void setPts_Conf_Dir(int pts_Conf_Dir) {
        this.pts_Conf_Dir = pts_Conf_Dir;
    }
}
