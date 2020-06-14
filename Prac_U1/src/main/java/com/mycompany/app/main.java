package com.mycompany.app;

import static java.lang.Boolean.TRUE;

/**
 * Hello world!
 *
 */
public class main {

    public static void main( String[] args ) {
        System.out.println("JAR CREADO EXITOSAMENTE!");
            SQL A= new SQL();
            Configuracion.CargarConf_Lite();
            SQL.Lite=TRUE;
            for(int i=0;i<SQL.Tablas.length;i++)
               A.Consultar(SQL.Tablas[i]);
            // A.CargarBD();
    }
}
