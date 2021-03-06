package com.mycompany.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;


public class SQL<T> {
    static String ruta ;
    static String url ;
    static String user;
    static String password;
    static String BD_nombre;
    static String BD_nueva;
    static String rutaBD;
    static String url_new;
    static String Driver;
    static String NombreBD;
    static int countInstalarBD=0;
    static boolean Lite=FALSE;
    static String[] Tablas = {"Carreras","Aulas","Plan_estudio","Materias","Profesores","Confianza","Disponibilidad",
                                "Grupos","LogIn","Prestamos","CATEGORIAS_EQUIPOS","EQUIPOS","AULA_EQUIPOS","USO_AULA_EQUIPOS"};
    Connection conectar = null;

    public void CargarBD() {
        Scanner text = new Scanner(System.in);
        Configuracion.CargarConfiguracion();
        int res = InstalarBD();
        int x=0;
        if (res == 0) {
            do {
                System.out.println("La Base de Datos ha sido creada con Exito!\n" +
                        "Ahora eliga el medio para extraer los datos:");
                System.out.print("\n1. Archivo de texto (.txt)\n2. Archivo Excel (.xlsx)\nR.- ");
                 x = text.nextInt();
                if (x == 1) {
                    for (int i = 0; i < Tablas.length; i++)
                        Insertar(Tablas[i]);
                } else if (x == 2) {
                    for (int i = 0; i < Tablas.length; i++)
                        InsertarXLSX(Tablas[i]);
                } else {
                    System.out.println("Lo sentimos intenta de nuevo, valor invalido\n Solo se admite (1 y 2)\n\n");
                }
            } while (x != 1 && x != 2);
        }
    }

    public int InstalarBD(){
        Scanner scan= new Scanner(System.in);
        String op;
        System.out.println("Nombre por defecto: "+NombreBD);
        System.out.print(".-¿Desea cambiarlo? (S/N): ");
        op=scan.nextLine();
        if(op.toUpperCase().equals("S")){
            System.out.print("Ingresa el nuevo Nombre: ");
            NombreBD=scan.nextLine();
        }
        System.out.println("Instalando Base De Datos, espere un momento por favor...");
        try{
            Class.forName(Driver);//.newInstance();
        }
        catch (ClassNotFoundException e){
            Scanner A= new Scanner(System.in);
            System.out.println("Lo sentimos, no fue posible conectar con un gestor de BD\nSe intentara a traves de SLQite...");
            System.out.println("Presione Enter para continuar...");
            A.nextLine();
            Excepcion.instalarLite();
            return 1;
        }
        try {
            conectar = DriverManager.getConnection(url_new, user, password);
            System.out.println("Conexión generada correctemente...");
        }
        catch(SQLException e) {
            System.out.println("Lo sentimos, no fue posible conectar con un gestor de BD");
            Excepcion.CambiarUsuarioUrlyPass(1);
            System.out.println(countInstalarBD);
            if(countInstalarBD==0) {
                countInstalarBD++;
                InstalarBD();

                    return 0;
                }
            else{
                System.out.println("Aun no es posible lograr conectarse a MySQL, desea utilizar SQLite? (S/N): ");
                Scanner x= new Scanner(System.in);
                String texto= x.nextLine();
                texto= texto.toUpperCase();
                if(texto.equals("S")){
                    Excepcion.instalarLite();
                    return 1;
                }
                else
                    System.out.println("Lo sentimos, no fue posible conectarse a la BD correctamente...");
            }
            return 1;
        }
        try{
            System.out.println("Leyendo Script de: "+rutaBD);
            String[] CrearBD=Script();
            try {
                Statement s = conectar.createStatement();
                s.addBatch("DROP DATABASE IF EXISTS " + NombreBD + ";");
                s.addBatch("CREATE DATABASE " + NombreBD + ";");
                s.addBatch("USE " + NombreBD + ";");
                s.executeBatch();

                for (int i = 0; i < CrearBD.length; i++) {
                    s.execute(CrearBD[i]);
                    System.out.println(CrearBD[i]);
                }
                s.close();
            }catch (Exception e){
                Excepcion.sqlsentence();
                return 1;
            }
        } catch (Exception e) {
            rutaBD=Excepcion.rutaIncorrecta(rutaBD);
            if(InstalarBD()==0)
                return 0;
            else
                return 1;
        }
        return 0;
    }

    public String[] Script(){
        String Linea, Script="";
        String[] A=null;
        try {
            FileReader leer = new FileReader(rutaBD);
            BufferedReader bufferLine = new BufferedReader(leer);

            while ((Linea = bufferLine.readLine()) != null) {
                Script = Script + "\n" + Linea.replace("`", "");
            }
            A=Script.split(";");
            System.out.println(Script);
            bufferLine.close();
            leer.close();
        } catch (IOException e) {
           rutaBD= Excepcion.rutaIncorrecta(rutaBD);
           A=Script();
        }
        return A;
    }
    public void generarConexion() {
        if(Lite==TRUE)
            Driver= SQLite.DriverLite;
        try {
            Class.forName(Driver);//.newInstance();
            try {
                if(Lite==TRUE)
                    conectar= DriverManager.getConnection(SQLite.urlLite + SQLite.rutaSQLite);
                else
                    conectar = DriverManager.getConnection(url+NombreBD+"?serverTimezone=UTC", user, password);

                System.out.println("Se ha conectado Exitosamente!");
            }
            catch(SQLException e){
                Excepcion.CambiarUsuarioUrlyPass(0);
                generarConexion();
            }
        } catch (ClassNotFoundException  e) {
            Excepcion.cambiarDriver();
            generarConexion();
        }
    }

    public List<T> Consultar(String Tabla) {
        List<T> A = new ArrayList<>();
        String ID;
        boolean X = TRUE;
        ArrayList<String> Profe = new ArrayList<>();
        ArrayList<String> Materia = new ArrayList<>();
        generarConexion();
        Statement s = null;
        try {
            s = conectar.createStatement();
        } catch (SQLException throwables) {
            Excepcion.statementFallido();
            generarConexion();
        }
        ResultSet rs = null;
        try {
            rs = s.executeQuery("SELECT * FROM " + Tabla.toUpperCase());

        if (rs.next() == TRUE) {

            System.out.println("\n-------------------------------------\n" +
                    "Tabla a Consultar: "+Tabla.toUpperCase());
            switch (Tabla.toUpperCase()) {
                case "PROFESORES":
                    do {
                        A.add((T) new Profesor(rs.getString(1), rs.getString(2), rs.getString(3),
                                rs.getString(4), rs.getString(5)));
                        System.out.println("\nProf_ID: " + rs.getString(1) + "  Nombre: " + rs.getString(2) + "\nGrado: " +
                                rs.getString(3) + "  Contrato: " + rs.getString(4) + "  Carrera: " + rs.getString(5));
                    } while (rs.next() == TRUE);
                    break;
                case "MATERIAS":
                    do {
                        A.add((T) new Materia(rs.getString(1), rs.getString(2), rs.getInt(3),
                                rs.getInt(4), rs.getInt(5), rs.getString(6)));
                        System.out.println("\nMat_ID: " + rs.getString(1) + "  Nombre: " + rs.getString(2) + "\nCreditos: " +
                                rs.getInt(3) + "  Cuatri: " + rs.getInt(4) + "  Hrs_Sem: " + rs.getInt(5) +
                                " Plan:" + rs.getString(6));
                    } while (rs.next() == TRUE);
                    break;
                case "PLAN_ESTUDIO":
                    do {
                        A.add((T) new Plan_Estudio(rs.getString(1), rs.getString(2),rs.getString(3)));
                        System.out.println("Plan: " + rs.getString(1) + "Carrera: " + rs.getString(3));
                    } while (rs.next() == TRUE);
                    break;
                case "CARRERAS":
                    do {
                        A.add((T) new Carrera(rs.getString(1), rs.getString(2)));
                        System.out.println("Carrera: " + rs.getString(1) + "  Nombre: " + rs.getString(2));
                    } while (rs.next() == TRUE);
                    break;
                case "LOGIN":
                    do {
                        A.add((T) new LogIn(rs.getString(1), rs.getString(2), rs.getString(3)));
                        System.out.println("Prof_ID: " + rs.getString(2) + "  Tipo: " + rs.getString(3));
                    } while (rs.next() == TRUE);
                    break;
                case "GRUPOS":

                    //s.close();
                    //rs.close();

                    try{
                        s = conectar.createStatement();
                        rs = s.executeQuery("SELECT * FROM " + Tabla + " ORDER BY Grupo_ID");
                }catch(SQLException e){
                        System.out.println("No se pudo consultar grupos");
                    }
                    rs.next();
                    do {
                        System.out.println("Grupo_ID: " + rs.getString(1) + "  Materia_ID: " + rs.getString(2)+
                                " Profesor_ID: "+ rs.getString(3));
                    } while (rs.next() == TRUE);


                    /*ID = rs.getString(1);
                    do {
                        if (rs.getString(1) == ID) {
                            Profe.add(rs.getString(3));
                            Materia.add(rs.getString(2));
                        } else {
                            A.add((T) new Grupo(ID, Materia, Profe));

                            for (int i = 0; i < Materia.size(); i++)
                                System.out.println("Grupo: " + ID + "Materia: " + Materia.get(i) +
                                        "Profesor: " + Profe.get(i));

                            Profe.clear();
                            Materia.clear();
                            Profe.add(rs.getString(3));
                            Materia.add(rs.getString(2));
                        }
                        ID = rs.getString(1);
                        if (rs.next() == TRUE)
                            X = TRUE;
                        else {
                            A.add((T) new Grupo(ID, Materia, Profe));

                            for (int i = 0; i < Materia.size(); i++)
                                System.out.println("Grupo: " + ID + "Materia: " + Materia.get(i) +
                                        "Profesor: " + Profe.get(i));
                            X = FALSE;
                        }

                    } while (X == TRUE);*/
                    break;
                case "AULAS":
                    do {
                        A.add((T) new Aula(rs.getString(1), rs.getString(2), rs.getInt(3)));
                        System.out.println("Aula: " + rs.getString(1) + "  Salon: " + rs.getString(2)+
                                "Capacidad: "+rs.getInt(3));
                    } while (rs.next() == TRUE);
                    break;
                case "PRESTAMOS":
                    do{
                        A.add((T) new Prestamo(rs.getString(1),rs.getString(2)));
                        System.out.println("Profesor: " + rs.getString(1) + "  Carrera: " + rs.getString(2));
                    }while(rs.next()==TRUE);
                    break;
                case "DISPONIBILIDAD":
                    do{
                        A.add((T) new Disponibilidad(rs.getInt(1),rs.getString(3),rs.getString(2)));
                        System.out.println("Dia: " + rs.getInt(1) + "  Profesor: " + rs.getString(3)+
                                "  Horas: "+rs.getString(2));
                    }while(rs.next()==TRUE);
                    break;
                case "CONFIANZA":
                    do{
                        A.add((T) new Confianza (rs.getString(1),rs.getString(2),
                                rs.getString(3),rs.getInt(4),rs.getInt(5)));
                        System.out.println("Materia:" + rs.getString(1) + "  Plan: " + rs.getString(2)+
                                "  Profesor: "+rs.getString(3)+"Puntos_Confianza: "+rs.getInt(4)+
                                "Puntos_Conf_Director: "+rs.getInt(5));
                    }while(rs.next()==TRUE);
                    break;

                case "CATEGORIAS_EQUIPOS":

                    System.out.println("=>ID_ Categoria: "+rs.getInt(1)+" | Nombre: "+ rs.getString(2)+
                            "\nDescripción: "+ rs.getString(3));
                    break;

                case "EQUIPOS":
                    System.out.println("=>ID_ Equipo: "+rs.getInt(1)+" | ID_Categoria: "+ rs.getInt(2)+
                            " | Nombre: "+ rs.getString(3)+" | Descripción: "+ rs.getString(4));
                    break;

                case "AULA_EQUIPOS":
                    System.out.println("=>ID_ Equipo: "+rs.getInt(1)+" | ID_Aula: "+ rs.getString(2)+
                            " | Cantidad: "+ rs.getInt(3));
                    break;

                case "USO_AULA_EQUIPOS":
                    System.out.println("=> Dia: "+rs.getInt(1)+" | Esp_Tiempo: "+ rs.getString(2)+
                            " | ID_Aula: "+ rs.getString(3)+"\nID_Grupo"+ rs.getString(4)+" | ID_Mat: "+ rs.getString(5));
                    break;
                    default:
                    System.out.println("Se ha ingresado un valor invalido\nIntente de nuevo...");
            }

            try {
                rs.close();
                conectar.close();
            } catch (SQLException e) {
                System.out.println("No es posible cerrar");
            }
            return A;

        } else {

            return null;
        }
        } catch (SQLException throwables) {
            Consultar(Excepcion.ConsultaFallida());
        }
        return null;
    }

    public void Insertar(String Tabla) {
        LeerRecursos B = new LeerRecursos();
        String aux;
        String Horas="";
        int j=0,count=1,max;
        String Archivo = "";
        generarConexion();
        Statement s= null;
        String[][] M_Datos;
        try {
           s = conectar.createStatement();
        } catch (SQLException throwables) {
            Excepcion.statementFallido();
            generarConexion();
        }
        try {
            M_Datos= B.LeerEquipo(Tabla);
            Tabla= Tabla.toUpperCase();
            System.out.println(Tabla);
            switch (Tabla) {
                case "AULAS":
                    List<Aula> A = B.LeerArchivoTxt(ruta + "BD_Aulas.txt");


                     for (int i = 0; i < A.size(); i++) {
                         s.executeUpdate("INSERT INTO AULAS VALUES('" + A.get(i).getAula_ID() + "','"
                                 + A.get(i).getNombre() + "'," + A.get(i).getCapacidad() + ")");
                     }
                    break;
                case "CARRERAS":
                    List<Carrera> C = B.LeerArchivoTxt(ruta + "BD_Carreras.txt");
                    for (int i = 0; i < C.size(); i++) {
                        s.executeUpdate("INSERT INTO CARRERAS VALUES('" + C.get(i).getCarrera_ID() + "','" +
                                C.get(i).getNombre() + "')");
                    }
                    break;
                case "GRUPOS":
                    List<Grupo> D = B.LeerArchivoTxt(ruta + "BD_Grupos.txt");

                    for (int i = 0; i < D.size(); i++) {
                        ArrayList<String> Size= D.get(i).getMateria_ID();
                         max=Size.size();

                        while(j<max) {
                            s.executeUpdate("INSERT INTO GRUPOS VALUES('" + D.get(i).getGrupo_ID() + "','" +
                                    D.get(i).getMateria_ID(j) + "','" + D.get(i).getProfesor_ID(j) + "')");
                            j++;
                        }
                        j=0;
                    }
                    break;
                case "MATERIAS":
                    List<Materia> E = B.LeerArchivoTxt(ruta + "BD_Materias.txt");
                    for (int i = 0; i < E.size(); i++) {
                        s.executeUpdate("INSERT INTO MATERIAS VALUES('" + E.get(i).getMateria_ID() +
                                "','" + E.get(i).getNombre() + "'," + E.get(i).getCreditos() + "," +
                                E.get(i).getCuatrimestre() + "," + E.get(i).getHrs_Sem() + ",'" + E.get(i).getPlan_ID() + "')");
                    }

                    break;
                case "PLAN_ESTUDIO":
                    List<Plan_Estudio> F = B.LeerArchivoTxt(ruta + "BD_Planes.txt");
                    for (int i = 0; i < F.size(); i++) {
                        s.executeUpdate("INSERT INTO PLAN_ESTUDIO VALUES('" + F.get(i).getPlan_ID()+"','"
                                +F.get(i).getNivel()+"','"+ F.get(i).getCarrera_ID() + "')");
                    }
                    break;
                case "PROFESORES":
                    List<Profesor> G = B.LeerArchivoTxt(ruta + "BD_Profesores.txt");
                    for (int i = 0; i < G.size(); i++) {
                        s.executeUpdate("INSERT INTO PROFESORES VALUES('" + G.get(i).getProfesor_ID() + "','" +
                                G.get(i).getNombre() + "','" + G.get(i).getGrado() + "','" + G.get(i).getContrato() + "','" +
                                G.get(i).getCarrera() + "')");
                    }
                    break;
                case "CONFIANZA":
                    List<Confianza> H = B.LeerArchivoTxt(ruta + "BD_Confianza.txt");
                    for (int i = 0; i < H.size(); i++) {
                        s.executeUpdate("INSERT INTO CONFIANZA VALUES('" + H.get(i).getMateria_ID() + "','" +
                                H.get(i).getPlan_ID() + "','" + H.get(i).getProfesor_ID() + "'," +
                                H.get(i).getPts_Conf_Prof() + "," + H.get(i).getPts_Conf_Dir() + ")");
                    }
                    break;
                case "PRESTAMOS":
                    List<Prestamo> I = B.LeerArchivoTxt(ruta + "BD_Prestamos.txt");
                    for (int i = 0; i < I.size(); i++) {
                        s.executeUpdate("INSERT INTO PRESTAMOS VALUES('" + I.get(i).getProfesor_ID() + "','" +
                                I.get(i).getCarrera_ID() + "')");
                    }
                    break;
                case "DISPONIBILIDAD":
                    List<Disponibilidad> J = B.LeerArchivoTxt(ruta + "BD_Disponibilidad.txt");
                    ArrayList<Integer> Hrs= new ArrayList<>();
                    for (int i = 0; i < J.size(); i++) {
                        Hrs= J.get(i).getHoras();
                        Horas=String.valueOf(Hrs.get(0));
                        while(count<Hrs.size()) {
                            aux = String.valueOf(Hrs.get(count));
                            Horas = Horas + ";" + aux;
                            count++;
                            System.out.println(Horas);
                        }
                        System.out.println("Insertar");
                        s.executeUpdate("INSERT INTO DISPONIBILIDAD VALUES(" + J.get(i).getDia() + ",'" +
                                Horas + "','" + J.get(i).getProfesor_ID() + "')");
                        Horas="";
                        count=1;
                    }
                    break;
                case "LOGIN":
                    List<LogIn> K = B.LeerArchivoTxt(ruta + "BD_LogIn.txt");
                    for (int i = 0; i < K.size(); i++) {
                        s.executeUpdate("INSERT INTO LOGIN VALUES('" +
                                K.get(i).getProfesor_ID() + "','" +K.get(i).getPassword()+"','"+
                                K.get(i).getTipo() + "')");
                    }
                    break;
                case "CATEGORIAS_EQUIPOS":

                    for (int i = 0; i < M_Datos.length; i++) {
                        s.executeUpdate("INSERT INTO CATEGORIAS_EQUIPOS VALUES("+ Integer.parseInt(M_Datos[i][0])+ ",'"+
                                M_Datos[i][1]+ "','"+M_Datos[i][2]+"')");
                    }
                    break;

                case "EQUIPOS":
                    for (int i = 0; i < M_Datos.length; i++) {
                        s.executeUpdate("INSERT INTO EQUIPOS VALUES("+ Integer.parseInt(M_Datos[i][0])+ ","+
                                Integer.parseInt(M_Datos[i][1])+",'"+M_Datos[i][2]+"','"+M_Datos[i][3]+"')");
                    }
                    break;

                case "AULA_EQUIPOS":
                    for (int i = 0; i < M_Datos.length; i++) {
                        s.executeUpdate("INSERT INTO AULA_EQUIPOS VALUES("+ Integer.parseInt(M_Datos[i][0])+ ",'"+
                                M_Datos[i][1]+"',"+Integer.parseInt(M_Datos[i][2])+")");
                    }
                    break;

                case "USO_AULA_EQUIPOS":
                    for (int i = 0; i < M_Datos.length; i++) {
                        s.executeUpdate("INSERT INTO USO_AULA_EQUIPOS VALUES("+ Integer.parseInt(M_Datos[i][0])+ ","+
                                Integer.parseInt(M_Datos[i][1])+",'"+M_Datos[i][2]+"','"+M_Datos[i][3]+"','"+M_Datos[i][4]+"')");
                    }
                    break;
                default:
                    System.out.println("Error Default");
            }

            s.close();
            conectar.close();
        } catch (Exception e) {
            System.out.println("[Advertencia]: La sentencia pudo no ejecutarse correctamente...");
        }

    }

    public void Eliminar(String Tabla, String ID){
        generarConexion();
        String sentencia;
        Statement s= null;
        try {
            s = conectar.createStatement();
        } catch (SQLException throwables) {
            Excepcion.statementFallido();
            generarConexion();
        }
        switch (Tabla) {
            case "AULAS":
                sentencia = "Aula_ID= ";
                break;
            case "CARRERAS":
                sentencia = "Carrera_ID= ";
                break;
            case "GRUPOS":
                sentencia = "Grupo_ID= ";
                break;
            case "LOGIN":
                sentencia = "Log_ID= ";
                break;
            case "MATERIAS":
                sentencia = "Materia_ID= ";
                break;
            case "PLAN_ESTUDIO":
                sentencia = "Plan_ID= ";
                break;
            case "PROFESORES":
                sentencia = "Profesor_ID= ";
                break;
            case "CONFIANZA":
                sentencia = "Profesor_ID=";
                break;
            case "PRESTAMOS":
                sentencia = "Profesor_ID=";
                break;
            case "DISPONIBILIDAD":
                sentencia = "Profesor_ID";
                break;
            case "CATEGORIAS_EQUIPOS":
                sentencia= "id_categoria";
                break;
            case "EQUIPOS":
                sentencia= "id_equipo";
                break;
            case "AULA_EQUIPOS":
                sentencia= "id_equipo";
                break;
            case "USO_AULA_EQUIPOS":
                sentencia= "dia";
                break;
            default:
                sentencia = "";

        }
        try {
            s.executeUpdate("DELETE " + Tabla + ".* FROM " + Tabla + " WHERE " + sentencia + "'" + ID + "'");
            s.close();
            conectar.close();
        } catch (SQLException throwables) {
          String[] C=Excepcion.EliminarFallida();
          Eliminar(C[0],C[1]);
        }
    }

    public void Actualizar(ArrayList<String> Fila, String Tabla) {
        Scanner X = new Scanner(System.in);
        try {
            generarConexion();
            Statement s = conectar.createStatement();
            switch (Tabla) {
                case "AULAS":
                     s.executeUpdate("UPDATE "+Tabla+" SET Nombre= '"+Fila.get(1)+"', Capacidad= "+ Integer.parseInt(Fila.get(2))
                             +" WHERE Aula_ID='"+Fila.get(0)+"'");
                    break;
                case "CARRERAS":
                    s.executeUpdate("UPDATE "+Tabla+" SET Nombre= '"+Fila.get(1)+"'  WHERE Carrera_ID='"+Fila.get(0)+"'");
                    break;
                case "GRUPOS":
                    s.executeUpdate("UPDATE "+Tabla+" SET Materia_ID= '"+Fila.get(1) +"' Profesor_ID= '"+
                            Fila.get(2)+"'  WHERE Grupo_ID='"+Fila.get(0)+"'");
                    break;
                case "LOGIN":
                    s.executeUpdate("UPDATE "+Tabla+" SET Profesor_ID= '"+Fila.get(1)+"', Contrasena= '"+ Fila.get(2)+"', Tipo='"+
                            Fila.get(3)+"' WHERE LogIn_ID='"+Fila.get(0)+"'");
                    break;
                case "MATERIAS":
                    s.executeUpdate("UPDATE "+Tabla+" SET Nombre= '"+Fila.get(1)+"', Creditos= "+ Integer.parseInt(Fila.get(2))+", Cuatrimestre= "+Integer.parseInt(Fila.get(3))+" , Hrs_sem= "+
                            Integer.parseInt(Fila.get(4))+ ", Plan_ID= '"+Fila.get(4)+"'  WHERE Materia_ID='"+Fila.get(0)+"'");
                    break;
                case "PLAN_ESTUDIO":
                    s.executeUpdate("UPDATE "+Tabla+" SET Carrera_ID= '"+Fila.get(1)+
                            "'  WHERE Plan_ID='"+Fila.get(0)+"'");
                    break;
                case "PROFESORES":
                    s.executeUpdate("UPDATE "+Tabla+" SET Nombre= '"+Fila.get(1)+"', Grado='"+Fila.get(2)+
                            "', Contrato= '"+Fila.get(3)+"', Carrera_ID= '"+ Fila.get(4)+"'  WHERE Profesor_ID='"+Fila.get(0)+"'");
                    break;
                case "CONFIANZA":
                    s.executeUpdate("UPDATE "+Tabla+" SET P_Conf= '"+Fila.get(2)+"', P_conf_Dir='"+Fila.get(3)+
                            "'  WHERE Profesor_ID='"+Fila.get(0)+"' AND Materia_ID= '"+Fila.get(1)+"')");
                    break;
                case "PRESTAMOS":
                    s.executeUpdate("UPDATE "+Tabla+"SET Carrera_ID= '"+Fila.get(1)+"' WHERE Profesor_ID= '"+Fila.get(0)+"'");
                    break;
                case "DISPONIBILIDAD":
                    s.executeUpdate("UPDATE "+Tabla+"SET Horas= '"+Fila.get(2)+
                            "' WHERE Profesor_ID= '"+Fila.get(0)+"' AND Dia= "+Fila.get(1));
                    break;
                case "CATEGORIAS_EQUIPOS":
                    s.executeUpdate("UPDATE "+Tabla+"SET nombre= '"+Fila.get(1)+"', descripcion= '"+Fila.get(2)+
                            "' WHERE id_categoria= '"+Fila.get(0)+"'");
                    break;
                case "EQUIPOS":
                    s.executeUpdate("UPDATE "+Tabla+"SET id_categoria= '"+Fila.get(1)+"',nombre= '"+Fila.get(2)+
                             "',descripcion= '"+Fila.get(3)+"' WHERE id_equipo= '"+Fila.get(0)+"'");
                    break;
                case "AULA_EQUIPOS":
                    s.executeUpdate("UPDATE "+Tabla+"SET cantidad= '"+Fila.get(2)+
                            "' WHERE id_equipo= '"+Fila.get(0)+"' AND id_aula= '"+Fila.get(1)+"'");
                    break;
                case "USO_AULA_EQUIPOS":
                    System.out.println();
                    break;

            }
        } catch (SQLException e) {
            Fila= Excepcion.Actualizar(Fila);
            System.out.println("Cambiar el Nombre de la Tabla\n" +
                    "(Presione 0 si desea mantener la misma): ");
            Tabla=X.nextLine();
            Actualizar(Fila,Tabla);
        }
    }

    //EXCEL

    public void InsertarXLSX(String Tabla) {
        String aux;
        String Horas="";
        int j=1,max=0;

        generarConexion();
        Statement s= null;
        String [][] M_Datos;
        Excel Ee= new Excel();
        try {
            s = conectar.createStatement();
        } catch (SQLException throwables) {
            Excepcion.statementFallido();
            generarConexion();
        }
        try {
            M_Datos= Ee.LeerExcel(Tabla);

            switch (Tabla.toUpperCase()) {
                case "AULAS":

                    for (int i = 0; i < M_Datos.length; i++) {
                        s.executeUpdate("INSERT INTO AULAS VALUES('" + M_Datos[i][0] + "','" + M_Datos[i][1] + "'," +
                                Integer.parseInt(M_Datos[i][2]) + ")");
                    }
                    break;
                case "CARRERAS":

                    for (int i = 0; i < M_Datos.length; i++) {
                        s.executeUpdate("INSERT INTO CARRERAS VALUES('" + M_Datos[i][0] + "','" + M_Datos[i][1] + "')");
                    }
                    break;
                case "GRUPOS":

                    while (M_Datos[0][max] != null)
                        max++;

                    for (int i = 0; i < M_Datos.length; i++) {

                        while (j <= (max / 2)) {
                            s.executeUpdate("INSERT INTO GRUPOS VALUES('" + M_Datos[i][0] + "','" +
                                    M_Datos[i][j] + "','" + M_Datos[i][(max / 2) + j] + "')");
                            j++;
                        }
                        j = 1;
                    }
                    break;
                case "MATERIAS":

                    for (int i = 0; i < M_Datos.length; i++) {
                        s.executeUpdate("INSERT INTO MATERIAS VALUES('" + M_Datos[i][0] + "','" + M_Datos[i][1] +
                                "'," + Integer.parseInt(M_Datos[i][2]) + "," + Integer.parseInt(M_Datos[i][3]) +
                                "," + Integer.parseInt(M_Datos[i][4]) + ",'" + M_Datos[i][5] + "')");
                    }
                    break;
                case "PLAN_ESTUDIO":

                    for (int i = 0; i < M_Datos.length; i++) {
                        s.executeUpdate("INSERT INTO PLAN_ESTUDIO VALUES('" + M_Datos[i][0] + "','" + M_Datos[i][1] +
                                "','" + M_Datos[i][2] + "')");
                    }
                    break;
                case "PROFESORES":
                    for (int i = 0; i < M_Datos.length; i++) {
                        s.executeUpdate("INSERT INTO PROFESORES VALUES('" + M_Datos[i][0] + "','" + M_Datos[i][1] +
                                "','" + M_Datos[i][2] + "','" + M_Datos[i][3] + "','" + M_Datos[i][4] + "')");
                    }
                    break;
                case "CONFIANZA":
                    for (int i = 0; i < M_Datos.length; i++) {
                        s.executeUpdate("INSERT INTO CONFIANZA VALUES('" + M_Datos[i][0] + "','" + M_Datos[i][1] +
                                "','" + M_Datos[i][2] + "'," + Integer.parseInt(M_Datos[i][3]) + "," +
                                Integer.parseInt(M_Datos[i][4]) + ")");
                    }
                    break;
                case "PRESTAMOS":
                    for (int i = 0; i < M_Datos.length; i++) {
                        s.executeUpdate("INSERT INTO PRESTAMOS VALUES('" + M_Datos[i][0] + "','" + M_Datos[i][1] + "')");
                    }
                    break;
                case "DISPONIBILIDAD":

                    for (int i = 0; i < M_Datos.length; i++) {
                        j=3;
                        Horas=M_Datos[i][2];
                        while(M_Datos[i][j]!=null) {
                            aux = String.valueOf(M_Datos[i][j]);
                            Horas = Horas + ";" + aux;

                            j++;
                        }
                        s.executeUpdate("INSERT INTO DISPONIBILIDAD VALUES('" + M_Datos[i][0] + "','"+Horas+"','" + M_Datos[i][1] + "')");
                    }


                    break;
                case "LOGIN":
                    for (int i = 0; i < M_Datos.length; i++) {
                        s.executeUpdate("INSERT INTO LOGIN VALUES('"+ M_Datos[i][0]+ "','"+M_Datos[i][1]+
                                "','"+M_Datos[i][2]+"')");
                    }
                    break;

                case "CATEGORIAS_EQUIPOS":
                    for (int i = 0; i < M_Datos.length; i++) {
                        s.executeUpdate("INSERT INTO CATEGORIAS_EQUIPOS VALUES("+ Integer.parseInt(M_Datos[i][0])+ ",'"+
                                M_Datos[i][1]+ "','"+M_Datos[i][2]+"')");
                    }
                    break;

                case "EQUIPOS":
                    for (int i = 0; i < M_Datos.length; i++) {
                        s.executeUpdate("INSERT INTO EQUIPOS VALUES("+ Integer.parseInt(M_Datos[i][0])+ ","+
                                Integer.parseInt(M_Datos[i][1])+",'"+M_Datos[i][2]+"','"+M_Datos[i][3]+"')");
                    }
                    break;

                case "AULA_EQUIPOS":
                    for (int i = 0; i < M_Datos.length; i++) {
                        s.executeUpdate("INSERT INTO AULA_EQUIPOS VALUES("+ Integer.parseInt(M_Datos[i][0])+ ",'"+
                                M_Datos[i][1]+"',"+Integer.parseInt(M_Datos[i][2])+")");
                    }
                    break;

                case "USO_AULA_EQUIPOS":
                    for (int i = 0; i < M_Datos.length; i++) {
                        s.executeUpdate("INSERT INTO USO_AULA_EQUIPOS VALUES("+ Integer.parseInt(M_Datos[i][0])+ ","+
                                Integer.parseInt(M_Datos[i][1])+",'"+M_Datos[i][2]+"','"+M_Datos[i][3]+"','"+M_Datos[i][4]+"')");
                    }
                    break;
                    default:
                    System.out.println("Error Default");
            }

            s.close();
            conectar.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error");
        }

    }
}

