package com.mycompany.app;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Configuracion {
        private static String rutaConf= "resources/Conf_SQL.txt";
        private static String rutaConfLite="resources/Conf_Lite.txt";

    public static void setRutaConf(String rutaConf) {
        Configuracion.rutaConf = rutaConf;
    }

    public static void setRutaConfLite(String rutaConfLite) {
        Configuracion.rutaConfLite = rutaConfLite;
    }

    public static void CargarConf_Lite() {
        ArrayList<String> F= new ArrayList<>();
        FileReader leer=null;
        try {
            leer = new FileReader(rutaConfLite);

            try (BufferedReader bufferLine = new BufferedReader(leer)) {
                String linea;
                String[] C = new String[5];

                int d=1;
                while ((linea = bufferLine.readLine()) != null) {
                    if (linea.equals(""))
                        break;
                    C = linea.split(";");
                    F.add(C[d].replaceAll("\\s", ""));
                    System.out.println(C[d]);
                }

                SQLite.rutaSQLite=F.get(0);
                SQLite.DriverLite=F.get(1);
                SQLite.urlLite=F.get(2);

            } catch (IOException e) {
                System.out.println("Lo sentimos, el archivo se encontro\npero no ha sido posible leer correctamente");
                System.out.println("Por favor asegurese que este escrito el formato correctamente...");

            }
        } catch (FileNotFoundException e) {
            Excepcion.rutaIncorrecta(rutaConf);
        }

    }
    public static void CargarConfiguracion() {
        ArrayList<String> F= new ArrayList<>();
        FileReader leer=null;
        try {
            leer = new FileReader(rutaConf);

            try (BufferedReader bufferLine = new BufferedReader(leer)) {
                String linea;
                String[] C = new String[5];

                int d=1;
                while ((linea = bufferLine.readLine()) != null) {
                    if (linea.equals(""))
                        break;
                    C = linea.split(";");
                    F.add(C[d].replaceAll("\\s", ""));
                    System.out.println(C[d]);
                }

                SQL.user=F.get(0);
                SQL.password=F.get(1);
                SQL.rutaBD=F.get(2);
                SQL.ruta=F.get(3);
                SQL.NombreBD=F.get(4);
                SQL.url_new=F.get(5);
                SQL.url=F.get(6);
                SQL.Driver=F.get(7);

            } catch (IOException e) {
                System.out.println("Lo sentimos, el archivo se encontro\npero no ha sido posible leer correctamente");
                System.out.println("Por favor asegurese que este escrito el formato correctamente...");

            }
        } catch (FileNotFoundException e) {
            Excepcion.rutaIncorrecta(rutaConf);
        }

    }
    public void cambiarConexion(String url){
        SQL.url=url;
    }
    public void cambiarUsuario(String user,String pass){
        SQL.user=user;
        SQL.password=pass;
    }
    public void cambiarRutaDir(String ruta){
        SQL.ruta=ruta;
    }
    public void cambiarRutaBD( String rutaBD){
        SQL.rutaBD=rutaBD;
    }
    public void cambiarUrl_new(String urlNew){
        SQL.url_new=urlNew;
    }
    public void cambiarNombreBDlite(String BDLite){
        SQLite.rutaSQLite=BDLite;
    }
    public void cambiarDriver(String Driver){
        SQL.Driver=Driver;
    }
}
