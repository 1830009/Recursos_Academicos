package com.mycompany.app;

import java.util.ArrayList;
import java.util.Scanner;

public class Excepcion {

    public static void CambiarUsuarioUrlyPass(int i) {
        System.out.println("Lo sentimos, Datos incorectos Por favor verifique que:\n" +
                "-Dirección de BD correcta\n-Usuario y Contraseña Adecuados.");
        Configuracion Conf = new Configuracion();
        Scanner scan = new Scanner(System.in);

        if (i == 0)
            System.out.println("El url ingresado es: " + SQL.url);

        else
            System.out.println("El url ingresado es: " + SQL.url_new);

        System.out.println("Desea cambiarlo? (S/N): ");
        String texto = scan.nextLine();
        texto = texto.toUpperCase();
        if (texto.equals("S")) {
            System.out.println("Escriba de nuevo la URL:");

            if (i == 0)
                SQL.url = scan.nextLine();

            else
                SQL.url_new = scan.nextLine();
        }
        System.out.println("\n\nAhora el Usuario y contraseña Ingresado es:\n- " + SQL.user + "\n- " + SQL.password);
        System.out.println("Desea cambiarlo (S/N): ");
        texto = scan.nextLine();
        texto = texto.toUpperCase();
        if (texto.equals("S")) {

            System.out.print("-Usuario: ");
            texto = scan.nextLine();
            System.out.print("-Contraseña: ");
            String pass = scan.nextLine();
            Conf.cambiarUsuario(texto, pass);
        }
    }
    public static void cambiarDriver() {
        Scanner text = new Scanner(System.in);

        System.out.println("Lo sentimos, hemos encontrado un problema con el Driver\n para la conexion con la BD");
        System.out.println("Por favor Presione la sig. opcion:\n 1.-volver a intentar\n2.-Cambiar Driver\nR.-");
        if (text.hasNextInt() == true) {

            switch (text.nextInt()) {
                case 1:
                    System.out.println("\n\nBien, Intentaremos realizar la conexión de nuevo...");
                    break;
                case 2:
                    System.out.println("\n\nIngresa por favor el Driver: ");
                    SQL.Driver = text.nextLine();
                    break;
                default:
                    System.out.println("Lo sentimos haz ingresado un valor incorrecto\n Aun asi volveremos a intentar...");
            }

        }

    }
    public static void sqlsentence(){
        System.out.println("Lo sentimos, ocurrio un problema al leer las sentencias, Por favor asegurese que:");
        System.out.println("-La sintaxis sea correcta\n-El orden logico sea el correcto\n" +
                "-El nombre de los objetos existan en la BD o esten escritos bien");
    }

    public static void statementFallido() {
        System.out.println("Lo sentimos, Hemos encontrado un inconveniente al ejecutar un Statement," +
                "\n Intentaremos realizar de nuevo la conexión...");
    }

    public static String ConsultaFallida() {
        Scanner text = new Scanner(System.in);
        System.out.println("Lo sentimos, no hemos encontrado la tabla solicitada. Por Favor:" +
                "\n-Compruebe que este escrita correctamente\n-Que exista en la BD\n" +
                "-Que no haya puesto letras mayusculas o minusculas, que impidan su reconocimiento adecuado");
        System.out.print("\nPor favor vuelva a ingresar el nombre de la Tabla: ");
        return text.nextLine();
    }

    public static String[] EliminarFallida(){
        Scanner text= new Scanner(System.in);
        System.out.println("Lo sentimos, no hemos encontrado la tabla solicitada. Por Favor:" +
                "\n-Compruebe que este escrita correctamente\n-Que exista en la BD\n" +
                "-Que no haya puesto letras mayusculas o minusculas, que impidan su reconocimiento adecuado");
        System.out.print("\nPor favor vuelva a ingresar el nombre de la Tabla: ");
         String[] C= new String[2];
        C[0]=text.nextLine();
        System.out.println("Ahora ingresa el ID  de la fila a Eliminar: ");
        C[1]= text.nextLine();
        return C;
    }

    public static ArrayList<String> Actualizar(ArrayList<String> A) {
        Scanner X = new Scanner(System.in);
        System.out.println("Lo sentimos, ha ocurrido un error al actualizar la fila solicitada");
        System.out.println("A continuacion mostraremos los datos recibidos, porfavor\n" +
                "Verifique que sean correctos y el ID exista en la tabla...");
        int x=0;
        boolean salir=false;
        do{
        System.out.println("\n0.- Valor del ID: " + A.get(0));


            for (int i = 1; i < A.size(); i++) {
                System.out.println((i) + " .- " + A.get(i));
            }

        System.out.println("Presione un numero si Desea Cambiar algun valor: ");
         x = X.nextInt();
        if (x>= 0 && x <= A.size()) {

            System.out.println("Ingresa el nuevo valor: ");
            X.nextLine();
            A.set(x, X.nextLine());
            System.out.println("Desea cambiar otra fila? (S/N): ");
            String No= X.nextLine();
            No=No.toUpperCase();
            if(No.equals("N") ){
                salir=true;
            }
        } else {
            System.out.println("Ingresaste un valor fuera de los limites,\n vuelve a intentar");
        }
    }while(salir==false);
        return A;
    }

    public static String rutaIncorrecta(String rutax){
        String op="";
        Scanner t= new Scanner(System.in);
        System.out.println("Lo sentimos, la ruta o el contenido del script es incorrecto\nPor favor asegurar que:\n" +
                "-La sintaxis sea correcta\n-La ruta y/o el Nombre del archivo sea el correcto");
        System.out.println("La ruta ingresada es: "+rutax);
        System.out.println("Desea cambiarla (S/N):");
        op=t.nextLine();
        op=op.toUpperCase();
        if(op.equals("S")){
            System.out.println("Ingrese de nuevo la ruta de acceso al archivo: ");
            op= t.nextLine();
        return op;
        }
        else
            System.out.println("Intentaremos con la misma ruta...");
        return rutax;
    }

    public static void cambiarDriverLite(){
        String driver="";
        Scanner t= new Scanner(System.in);
        System.out.println("Lo sentimos, el Driver indicado no es el correcto...\n" +
                "Por favor vuelva a ingresar el Driver: ");
        driver=t.nextLine();
        SQLite.DriverLite=driver;
    }
    public static void cambiarConexionLite(String url, String ruta){
        String op;
        Scanner t= new Scanner(System.in);
        System.out.println("Lo sentimos, el URL indicado no es el correcto...\n" +
                "El URL ingresado es: "+ url+"\nDesea cambiarlo (S/N[Cualquier letra]): ");
        op=t.nextLine();
        if(op.toUpperCase().equals("S")){
            System.out.println("Por favor vuelva a ingresar el URL: ");
            url=t.nextLine();
        }
        System.out.println("Ahora, la ruta para la Base de Datos es la siguiente:\n"+ ruta);
        System.out.println("Desea cambiarla (S/N[Cualquier letra]): ");
        op=t.nextLine();
        if(op.toUpperCase().equals("S")) {
            System.out.println("Ahora ingrese de nuevo la ruta de acceso al archivo");
            ruta = t.nextLine();
        }
        else
            System.out.println("\nVolveremos a intentar con los valores atuales...");

        SQLite.urlLite=url;
        SQLite.rutaSQLite=ruta;
    }

    public static void instalarLite(){
        Configuracion.CargarConf_Lite();
        SQLite A= new SQLite();
        A.ConectarLite();
    }
}
