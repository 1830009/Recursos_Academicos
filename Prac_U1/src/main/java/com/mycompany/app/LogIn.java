package com.mycompany.app;

public class LogIn {

    private String LogIn_ID,Profesor_ID,Password,Tipo;

    public LogIn(String login,String profesor_ID, String password, String tipo) {
        LogIn_ID=login;
        Profesor_ID = profesor_ID;
        Password = password;
        Tipo = tipo;
    }

    public String getLogIn_ID() {
        return LogIn_ID;
    }

    public void setLogIn_ID(String logIn_ID) {
        LogIn_ID = logIn_ID;
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
