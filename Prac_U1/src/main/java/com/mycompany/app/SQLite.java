package com.mycompany.app;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static java.lang.Boolean.TRUE;

/*
   static String rutaSQLite="resources/recursos.db";
    static String DiverLite="org.sqlite.JDBC";
    static String urlLite="jdbc:sqlite:";

 */
public class SQLite extends SQL {
    Connection Conexion=null;
    static String rutaSQLite;
    static String DriverLite;
    static String urlLite;

    public void ConectarLite(){
        File F= new File(rutaSQLite);
       F.delete();
        System.out.println("Driver Lite:" + DriverLite);
       try {
           Class.forName(DriverLite);
       } catch (ClassNotFoundException e) {
           Excepcion.cambiarDriverLite();
           System.out.println("Volviendo a Intentar...");
           ConectarLite();
           return;
       }
       try {
           Conexion = DriverManager.getConnection(urlLite + rutaSQLite);
           System.out.println("Se conecto");
       } catch (SQLException throwables) {
           Excepcion.cambiarConexionLite(urlLite,rutaSQLite);
           System.out.println("Volviendo a Intentar...");
           ConectarLite();
           return;
       }
        try{
            Statement s = Conexion.createStatement();
            String[] CrearBD=Script();

            //Crear la BD
            for(int i=0;i<CrearBD.length;i++)
            s.execute(CrearBD[i]);

            s.close();
            //Insertar los Datos a las Tablas
            SQL.Lite=TRUE;
            for(int i=0;i<Tablas.length;i++)
                Insertar(Tablas[i]);


            System.out.println("Instalación SQLite Completada con Exito...");

        } catch (SQLException throwables) {
            System.out.println(SQL.rutaBD);
            Excepcion.rutaIncorrecta(rutaSQLite);
                ConectarLite();
                return;
        }

    }

}
