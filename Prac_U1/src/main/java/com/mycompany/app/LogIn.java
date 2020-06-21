package com.mycompany.app;

public class LogIn {

    private String Profesor_ID,Password,Tipo;

    public LogIn(String profesor_ID, String password, String tipo) {

        Profesor_ID = profesor_ID;
        Password = password;
        Tipo = tipo;
    }





    public String getProfesor_ID() {
        return Profesor_ID;
    }

    public void setProfesor_ID(String profesor_ID) {
        Profesor_ID = profesor_ID;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }
}
