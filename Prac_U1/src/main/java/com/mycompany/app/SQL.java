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
    static boolean Lite=FALSE;
    static String[] Tablas = {"Carrera","Aula","Plan","Materias","Profesor","Confianza","Disponibilidad","Grupo","LogIn"};
    Connection conectar = null;

    public void CargarBD(){
        Configuracion.CargarConfiguracion();
        int res=InstalarBD();
        if(res==0) {
            for (int i = 0; i < Tablas.length; i++)
                Insertar(Tablas[i]);
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
            Excepcion.CambiarUsuarioUrlyPass(1);
            InstalarBD();
        }
        try{
            System.out.println(rutaBD);
            Statement s = conectar.createStatement();
            s.addBatch("DROP DATABASE IF EXISTS " + NombreBD+";");
            s.addBatch("CREATE DATABASE "+ NombreBD+";");
            s.addBatch("USE "+ NombreBD+";");
            s.executeBatch();
            String[] CrearBD=Script();
            for(int i=0;i<CrearBD.length;i++) {
                s.execute(CrearBD[i]);
                System.out.println(CrearBD[i]);
            }
            s.close();
        } catch (SQLException e) {
            Excepcion.rutaIncorrecta(rutaBD);
            InstalarBD();
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
            //System.out.println(Script);
            bufferLine.close();
            leer.close();
        } catch (IOException e) {
            Excepcion.rutaIncorrecta(rutaBD);
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
            rs = s.executeQuery("SELECT * FROM " + Tabla);

        if (rs.next() == TRUE) {
            switch (Tabla.toUpperCase()) {
                case "PROFESOR":
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
                case "PLAN":
                    do {
                        A.add((T) new Plan_Estudio(rs.getString(1), rs.getString(2),rs.getString(3)));
                        System.out.println("Plan: " + rs.getString(1) + "Carrera: " + rs.getString(3));
                    } while (rs.next() == TRUE);
                    break;
                case "CARRERA":
                    do {
                        A.add((T) new Carrera(rs.getString(1), rs.getString(2)));
                        System.out.println("Carrera: " + rs.getString(1) + "  Nombre: " + rs.getString(2));
                    } while (rs.next() == TRUE);
                    break;
                case "LOGIN":
                    do {
                        A.add((T) new LogIn(rs.getString(1), rs.getString(2), rs.getString(3),rs.getString(4)));
                        System.out.println("Prof_ID: " + rs.getString(2) + "  Tipo: " + rs.getString(4));
                    } while (rs.next() == TRUE);
                    break;
                case "GRUPO":

                    s.close();
                    rs.close();
                    s = conectar.createStatement();
                    rs = s.executeQuery("SELECT * FROM " + Tabla + " ORDER BY Grupo_ID");
                    rs.next();
                    ID = rs.getString(1);
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

                    } while (X == TRUE);
                    break;
                case "AULA":
                    do {
                        A.add((T) new Aula(rs.getString(1), rs.getString(2), rs.getInt(3)));
                        System.out.println("Aula: " + rs.getString(1) + "  Salon: " + rs.getString(2)+
                                "Capacidad: "+rs.getInt(3));
                    } while (rs.next() == TRUE);
                    break;
                case "PRESTAMO":
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
                                "  Profesor: "+rs.getString(3)+"Puntos_Confianza: "+rs.getString(4)+
                                "Puntos_Conf_Director: "+rs.getString(5));
                    }while(rs.next()==TRUE);
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
        try {
           s = conectar.createStatement();
        } catch (SQLException throwables) {
            Excepcion.statementFallido();
            generarConexion();
        }
        try {
            switch (Tabla.toUpperCase()) {
                case "AULA":
                    List<Aula> A = B.LeerArchivoTxt(ruta + "BD_Aulas.txt");


                     for (int i = 0; i < A.size(); i++) {
                         s.executeUpdate("INSERT INTO Aula VALUES('" + A.get(i).getAula_ID() + "','"
                                 + A.get(i).getNombre() + "'," + A.get(i).getCapacidad() + ")");
                     }
                    break;
                case "CARRERA":
                    List<Carrera> C = B.LeerArchivoTxt(ruta + "BD_Carreras.txt");
                    for (int i = 0; i < C.size(); i++) {
                        s.executeUpdate("INSERT INTO Carrera VALUES('" + C.get(i).getCarrera_ID() + "','" +
                                C.get(i).getNombre() + "')");
                    }
                    break;
                case "GRUPO":
                    List<Grupo> D = B.LeerArchivoTxt(ruta + "BD_Grupos.txt");

                    for (int i = 0; i < D.size(); i++) {
                        ArrayList<String> Size= D.get(i).getMateria_ID();
                         max=Size.size();

                        while(j<max) {
                            s.executeUpdate("INSERT INTO Grupo VALUES('" + D.get(i).getGrupo_ID() + "','" +
                                    D.get(i).getMateria_ID(j) + "','" + D.get(i).getProfesor_ID(j) + "')");
                            j++;
                        }
                        j=0;
                    }
                    break;
                case "MATERIAS":
                    List<Materia> E = B.LeerArchivoTxt(ruta + "BD_Materias.txt");
                    for (int i = 0; i < E.size(); i++) {
                        s.executeUpdate("INSERT INTO Materias VALUES('" + E.get(i).getMateria_ID() +
                                "','" + E.get(i).getNombre() + "'," + E.get(i).getCreditos() + "," +
                                E.get(i).getCuatrimestre() + "," + E.get(i).getHrs_Sem() + ",'" + E.get(i).getPlan_ID() + "')");
                    }

                    break;
                case "PLAN":
                    List<Plan_Estudio> F = B.LeerArchivoTxt(ruta + "BD_Planes.txt");
                    for (int i = 0; i < F.size(); i++) {
                        s.executeUpdate("INSERT INTO Plan VALUES('" + F.get(i).getPlan_ID()+"','"
                                +F.get(i).getNivel()+"','"+ F.get(i).getCarrera_ID() + "')");
                    }
                    break;
                case "PROFESOR":
                    List<Profesor> G = B.LeerArchivoTxt(ruta + "BD_Profesores.txt");
                    for (int i = 0; i < G.size(); i++) {
                        s.executeUpdate("INSERT INTO Profesor VALUES('" + G.get(i).getProfesor_ID() + "','" +
                                G.get(i).getNombre() + "','" + G.get(i).getGrado() + "','" + G.get(i).getContrato() + "','" +
                                G.get(i).getCarrera() + "')");
                    }
                    break;
                case "CONFIANZA":
                    List<Confianza> H = B.LeerArchivoTxt(ruta + "BD_Confianza.txt");
                    for (int i = 0; i < H.size(); i++) {
                        s.executeUpdate("INSERT INTO Confianza VALUES('" + H.get(i).getMateria_ID() + "','" +
                                H.get(i).getPlan_ID() + "','" + H.get(i).getProfesor_ID() + "'," +
                                H.get(i).getPts_Conf_Prof() + "," + H.get(i).getPts_Conf_Dir() + ")");
                    }
                    break;
                case "PRESTAMO":
                    List<Prestamo> I = B.LeerArchivoTxt(ruta + "BD_Prestamo.txt");
                    for (int i = 0; i < I.size(); i++) {
                        s.executeUpdate("INSERT INTO Prestamo VALUES('" + I.get(i).getProfesor_ID() + "','" +
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
                        s.executeUpdate("INSERT INTO Disponibilidad VALUES(" + J.get(i).getDia() + ",'" +
                                Horas + "','" + J.get(i).getProfesor_ID() + "')");
                        Horas="";
                        count=1;
                    }
                    break;
                case "LOGIN":
                    List<LogIn> K = B.LeerArchivoTxt(ruta + "BD_LogIn.txt");
                    for (int i = 0; i < K.size(); i++) {
                        s.executeUpdate("INSERT INTO LogIn VALUES('"+K.get(i).getLogIn_ID()+"','" +
                                K.get(i).getProfesor_ID() + "','" +K.get(i).getPassword()+"','"+
                                K.get(i).getTipo() + "')");
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
            case "Aula":
                sentencia = "Aula_ID= ";
                break;
            case "Carrera":
                sentencia = "Carrera_ID= ";
                break;
            case "Grupo":
                sentencia = "Grupo_ID= ";
                break;
            case "LogIn":
                sentencia = "Log_ID= ";
                break;
            case "Materias":
                sentencia = "Materia_ID= ";
                break;
            case "Plan":
                sentencia = "Plan_ID= ";
                break;
            case "Profesor":
                sentencia = "Profesor_ID= ";
                break;
            case "Confianza":
                sentencia = "Profesor_ID=";
                break;
            case "Prestamo":
                sentencia = "Profesor_ID=";
                break;
            case "Disponibilidad":
                sentencia = "Profesor_ID";
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
                case "Aula":
                     s.executeUpdate("UPDATE "+Tabla+" SET Nombre= '"+Fila.get(1)+"', Capacidad= "+ Integer.parseInt(Fila.get(2))
                             +" WHERE Aula_ID='"+Fila.get(0)+"'");
                    break;
                case "Carrera":
                    s.executeUpdate("UPDATE "+Tabla+" SET Nombre= '"+Fila.get(1)+"'  WHERE Carrera_ID='"+Fila.get(0)+"'");
                    break;
                case "Grupo":
                    s.executeUpdate("UPDATE "+Tabla+" SET Materia_ID= '"+Fila.get(1) +"' Profesor_ID= '"+
                            Fila.get(2)+"'  WHERE Grupo_ID='"+Fila.get(0)+"'");
                    break;
                case "LogIn":
                    s.executeUpdate("UPDATE "+Tabla+" SET Profesor_ID= '"+Fila.get(1)+"', Contrasena= '"+ Fila.get(2)+"', Tipo='"+
                            Fila.get(3)+"' WHERE LogIn_ID='"+Fila.get(0)+"'");
                    break;
                case "Materias":
                    s.executeUpdate("UPDATE "+Tabla+" SET Nombre= '"+Fila.get(1)+"', Creditos= "+ Integer.parseInt(Fila.get(2))+", Cuatrimestre= "+Integer.parseInt(Fila.get(3))+" , Hrs_sem= "+
                            Integer.parseInt(Fila.get(4))+ ", Plan_ID= '"+Fila.get(4)+"'  WHERE Materia_ID='"+Fila.get(0)+"'");
                    break;
                case "Plan":
                    s.executeUpdate("UPDATE "+Tabla+" SET Carrera_ID= '"+Fila.get(1)+
                            "'  WHERE Plan_ID='"+Fila.get(0)+"'");
                    break;
                case "Profesor":
                    s.executeUpdate("UPDATE "+Tabla+" SET Nombre= '"+Fila.get(1)+"', Grado='"+Fila.get(2)+
                            "', Contrato= '"+Fila.get(3)+"', Carrera_ID= '"+ Fila.get(4)+"'  WHERE Profesor_ID='"+Fila.get(0)+"'");
                    break;
                case "Confianza":
                    s.executeUpdate("UPDATE "+Tabla+" SET P_Conf= '"+Fila.get(2)+"', P_conf_Dir='"+Fila.get(3)+
                            "'  WHERE Profesor_ID='"+Fila.get(0)+"' AND Materia_ID= '"+Fila.get(1)+"')");
                    break;
                case "Prestamo":
                    s.executeUpdate("UPDATE "+Tabla+"SET Carrera_ID= '"+Fila.get(1)+"' WHERE Profesor_ID= '"+Fila.get(0)+"'");
                    break;
                case "Disponibilidad":
                    s.executeUpdate("UPDATE "+Tabla+"SET Horas= '"+Fila.get(2)+
                            "' WHERE Profesor_ID= '"+Fila.get(0)+"' AND Dia= "+Fila.get(1));
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
}

