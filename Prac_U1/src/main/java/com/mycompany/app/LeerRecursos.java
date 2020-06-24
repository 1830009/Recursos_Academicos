package com.mycompany.app;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class LeerRecursos<T> {

    public String[][] LeerEquipo(String Tabla){
        String[][] M_Datos=null;
        FileReader leer=null;
        int i=0;
        try {
            leer = new FileReader("resources/" + Tabla + ".txt");

            try {
                BufferedReader bufferAUX=  new BufferedReader(leer);
                String linea;
                String C[];
                while ((linea = bufferAUX.readLine()) != null) {
                    if (linea.equals(""))
                        break;
                    i++;
                }
                M_Datos = new String[i][10];
                bufferAUX.close();

                try {
                    leer = new FileReader("resources/" + Tabla + ".txt");
                    BufferedReader buffer = new BufferedReader(leer);
                    System.out.println(i);
                    i = 0;

                    while ((linea = buffer.readLine()) != null) {
                        System.out.println(linea);
                        if (linea.equals(""))
                            break;

                        C = linea.split(";");
                        switch (Tabla) {
                            case "CATEGORIAS_EQUIPOS":
                                for (int x = 0; x < 3; x++)
                                    M_Datos[i][x] = C[x];
                                break;
                            case "EQUIPOS":
                                for (int x = 0; x < 4; x++)
                                    M_Datos[i][x] = C[x];
                                break;
                            case "AULA_EQUIPOS":
                                for (int x = 0; x < 3; x++)
                                    M_Datos[i][x] = C[x];
                                break;
                            case "USO_AULA_EQUIPOS":
                                for (int x = 0; x < 5; x++)
                                    M_Datos[i][x] = C[x];
                                break;
                        }
                    i++;
                    }
                }catch (Exception e) {
                    System.out.println();
                }
            } catch (IOException e) {
                System.out.println();
            }
        } catch (FileNotFoundException e) {
            System.out.println();
        }
        return M_Datos;
    }
    public List<T> LeerArchivoTxt(String Ruta)  {
        ArrayList<String> ListMat= new ArrayList<>();
        ArrayList<String> ListProf= new ArrayList<>();
        List<T> Lista = new ArrayList<>();
        ArrayList<Integer> Horas= new ArrayList<>();
        int ix,max;
        FileReader leer=null;
        try {
                 leer= new FileReader(Ruta);

                try (BufferedReader bufferLine = new BufferedReader(leer)){
                    String linea;
                    String C[];
                    String Tipo =bufferLine.readLine();
                    while ((linea = bufferLine.readLine()) != null) {
                        if (linea.equals(""))
                            break;

                        C = linea.split(";");
                        //for (int x = 0; x < C.length; x++)
                          //  C[x] = C[x].replaceAll("\\s", "");

                        switch (Tipo) {
                            case "P":
                                C[1] = C[1].replace("_", " ");
                                Lista.add((T) new Profesor(C[0], C[1], C[2], C[3], C[4]));
                                break;

                            case "M":
                                //C[1] = C[1].replace("_", " ");
                                Lista.add((T) new Materia(C[0], C[1], Integer.parseInt(C[2]), Integer.parseInt(C[3]),
                                        Integer.parseInt(C[4]), C[5]));
                                break;

                            case "G":
                                ListMat.clear();
                                ListProf.clear();
                                 max = C.length - 1 ;
                                if (max % 2 == 0) {
                                    for (int i = 1; i <= max / 2; i++) {
                                        ListMat.add(C[i]);
                                        ListProf.add(C[(max / 2) + i]);
                                    }
                                    Lista.add((T) new Grupo(C[0], ListMat, ListProf));

                                    break;
                                }

                            case "A":
                                C[1] = C[1].replace("_", " ");
                                Lista.add((T) new Aula(C[0], C[1], Integer.parseInt(C[2])));
                                break;

                            case "C":
                                C[1] = C[1].replace("_", " ");
                                Lista.add((T) new Carrera(C[0], C[1]));
                                break;

                            case "L":

                                Lista.add((T) new LogIn(C[0], C[1], C[2]));
                                break;
                            case "E":
                                //C[1] = C[1].replace("_", " ");
                                Lista.add((T) new Plan_Estudio(C[0], C[1],C[2]));
                                break;
                            case "X":
                                Lista.add((T) new Prestamo(C[0], C[1]));
                                break;
                            case "O":
                                Lista.add((T) new Confianza(C[0], C[1], C[2], Integer.parseInt(C[3]), Integer.parseInt(C[4])));
                                break;
                            case "D":
                                ix = 2;
                                Horas.clear();
                                while (ix < C.length){
                                    Horas.add(Integer.parseInt(C[ix]));
                                    ix++;
                                }
                                Lista.add((T) new Disponibilidad(Integer.parseInt(C[0]),C[1],Horas));

                                break;
                        }

                        System.out.println(Arrays.toString(C));
                    }
                    return Lista;
                } catch (IOException e) {
                    System.out.println("Ha ocurrido un problema al leer el archivo\n" +
                            "Comprueba que el formato este correcto, Intenta de nuevo...");
                        reingresarRuta();

                }
            }
        catch(FileNotFoundException e){
            System.out.println("\n---Error al abrir archivo,\nDirección o Nombre del Archivo incorrecta...");
            reingresarRuta();
        }
            finally {
                try {
                    if (null != leer) {
                        leer.close();
                    }
                } catch (Exception e2) {

                }
            }
            return Lista;
        }
    public void reingresarRuta() {
        Scanner leerRuta= new Scanner(System.in);
        String ruta;

        System.out.println("\n\nVuelve a Ingresar la ruta donde se encuentra el archivo:\n" +
                "(Asegurate de escribir correctamente):\n==> ");

        ruta=leerRuta.nextLine();
        System.out.println("Espere un momento, Recuperando Archivo...");

        LeerArchivoTxt(ruta);


    }
}