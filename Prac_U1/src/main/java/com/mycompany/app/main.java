package com.mycompany.app;

/**
 * Hello world!
 *
 */
public class main {

    public static void main( String[] args ) {
        System.out.println("JAR CREADO EXITOSAMENTE!");
        SQL A = new SQL();
        Configuracion.CargarConfiguracion();
        for (int i = 0; i < A.Tablas.length; i++)
        A.Consultar(A.Tablas[i]);


    }
}
