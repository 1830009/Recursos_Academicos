# Recursos_Academicos
Administracio de la BD de recursos academicos


Comando para ejecutar en Maven==>  mvn compile exec:java -Dexec.cleanupDaemonThreads=false\

<<<Recuerde cambiar el usuario y contraseña en el archivo: resources/Conf_SQL.txt>>>\

Estructura para Generar la Instalacion de la BD, e Insertar Datos de prueba:\
///////////////////////////////////////\
 //Crear una nueva Instancia y llamar al metodo CargarBD()\
 
 SQL A= new SQL();\
 Configuracion.CargarConfiguracion();\
             A.CargarBD();\
             
//////////////////////////////////////

SQL A = new SQL();\
        //Si se quiere usar SQLite\
        SQL.Lite=TRUE;\
        Configuracion.CargarConf_Lite();\
        //Instalar Base de Datos\
        Configuracion.CargarConfiguracion();\
        A.CargarBD();\
        //Ejemplo de Actualizar\
        ArrayList<String> F= new ArrayList<>();\
        F.add("ITI");\
        F.add("Ing. en Tecnologías de la Información 2");\
        A.Actualizar(F,"CARRERAS");\
        A.Actualizar(F,"CARRERAS");\
        //Ejemplo de eliminar una Fila\
        A.Eliminar("CARRERAS","IM");\
        //Ejemplo de Consultar\
        A.Consultar("CARRERAS");\
           
