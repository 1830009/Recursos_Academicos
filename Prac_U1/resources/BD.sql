DROP TABLE IF EXISTS `AULAS`;
CREATE TABLE `AULAS` (
  `Aula_ID` varchar(10) NOT NULL,
  `Nombre` varchar(40) NOT NULL,
  `Capacidad` int NOT NULL,
  PRIMARY KEY (`Aula_ID`));

DROP TABLE IF EXISTS `CARRERAS`;
CREATE TABLE `CARRERAS` (
  `Carrera_ID` varchar(10) NOT NULL,
  `Nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`Carrera_ID`));

DROP TABLE IF EXISTS `PLAN_ESTUDIO`;
CREATE TABLE `PLAN_ESTUDIO` (
  `Plan_ID` varchar(20) NOT NULL,
  `Nivel` varchar(3) NOT NULL,
  `Carrera_ID` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`Plan_ID`),
  CONSTRAINT `Plan_ibfk_1` FOREIGN KEY (`Carrera_ID`) REFERENCES `CARRERAS` (`Carrera_ID`));
  
  DROP TABLE IF EXISTS `MATERIAS`;
CREATE TABLE `MATERIAS` (
  `Materia_ID` varchar(10) NOT NULL,
  `Nombre` varchar(60) NOT NULL,
  `Creditos` int NOT NULL,
  `Cuatrimestre` int NOT NULL,
  `Hrs_sem` int NOT NULL,
  `Plan_ID` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`Materia_ID`),
  CONSTRAINT `Materias_ibfk_1` FOREIGN KEY (`Plan_ID`) REFERENCES `PLAN_ESTUDIO` (`Plan_ID`));
  
  DROP TABLE IF EXISTS `PROFESORES`;
CREATE TABLE `PROFESORES` (
  `Profesor_ID` varchar(10) NOT NULL,
  `Nombre` varchar(70) NOT NULL,
  `Grado` varchar(50) NOT NULL,
  `Contrato` varchar(50) NOT NULL,
  `Carrera_ID` varchar(10) NOT NULL,
  PRIMARY KEY (`Profesor_ID`),
  CONSTRAINT `Profesor_ibfk_1` FOREIGN KEY (`Carrera_ID`) REFERENCES `CARRERAS` (`Carrera_ID`));

DROP TABLE IF EXISTS `CONFIANZA`;
CREATE TABLE `CONFIANZA` (
  `Materia_ID` varchar(10) ,
  `Plan_ID` varchar(20) NOT NULL,
  `Profesor_ID` varchar(10) ,
  `P_Conf` int NOT NULL,
  `P_Conf_Dir` int DEFAULT '0',
  PRIMARY KEY (`Materia_ID`,`Profesor_ID`),
  CONSTRAINT conf_fk1 FOREIGN KEY (Materia_ID) REFERENCES MATERIAS(Materia_ID),
  CONSTRAINT conf_fk2 FOREIGN KEY (Profesor_ID) REFERENCES PROFESORES(Profesor_ID));

DROP TABLE IF EXISTS `DISPONIBILIDAD`;
CREATE TABLE `DISPONIBILIDAD` (
  `Dia` int NOT NULL,
  `Horas` varchar(30) NOT NULL,
  `Profesor_ID` varchar(10) NOT NULL,
  PRIMARY KEY (`Profesor_ID`,`Dia`),
  CONSTRAINT `Disponibilidad_ibfk_1` FOREIGN KEY (`Profesor_ID`) REFERENCES `PROFESORES` (`Profesor_ID`));

DROP TABLE IF EXISTS `GRUPOS`;
CREATE TABLE `GRUPOS` (
  `Grupo_ID` varchar(10) NOT NULL,
  `Materia_ID` varchar(10) NOT NULL,
  `Profesor_ID` varchar(10) NOT NULL,
  PRIMARY KEY (`Grupo_ID`,`Materia_ID`,`Profesor_ID`),
  CONSTRAINT `Grupo_ibfk_1` FOREIGN KEY (`Materia_ID`) REFERENCES `MATERIAS` (`Materia_ID`),
  CONSTRAINT `Grupo_ibfk_2` FOREIGN KEY (`Profesor_ID`) REFERENCES `PROFESORES` (`Profesor_ID`));

DROP TABLE IF EXISTS `LOGIN`;
CREATE TABLE `LOGIN` (
  `Profesor_ID` varchar(10) NOT NULL,
  `Contrasena` varchar(30) DEFAULT NULL,
  `Tipo` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`Profesor_ID`),
  CONSTRAINT `LogIn_ibfk_1` FOREIGN KEY (`Profesor_ID`) REFERENCES `PROFESORES` (`Profesor_ID`));

DROP TABLE IF EXISTS `PRESTAMOS`;
CREATE TABLE `PRESTAMOS` (
  `Profesor_ID` varchar(10) NOT NULL,
  `Carrera_ID` varchar(10) NOT NULL,
  CONSTRAINT `Prestamo_ibfk_1` FOREIGN KEY (`Profesor_ID`) REFERENCES `PROFESORES` (`Profesor_ID`),
  CONSTRAINT `Prestamo_ibfk_2` FOREIGN KEY (`Carrera_ID`) REFERENCES `CARRERAS` (`Carrera_ID`));
 
DROP TABLE IF EXISTS CATEGORIAS_EQUIPOS;  
CREATE TABLE CATEGORIAS_EQUIPOS(
    id_categoria INT(11) NOT NULL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(300)
);

DROP TABLE IF EXISTS EQUIPOS;
CREATE TABLE EQUIPOS(
    id_equipo INT(11) NOT NULL PRIMARY KEY,
    id_categoria INT(11) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(100) NOT NULL,
    CONSTRAINT FK_equipo_categoriaequipo_id_categoria FOREIGN KEY (id_categoria) REFERENCES CATEGORIAS_EQUIPOS(id_categoria));

DROP TABLE IF EXISTS AULA_EQUIPOS;
CREATE TABLE AULA_EQUIPOS(
    id_equipo INT(11) NOT NULL,
    id_aula VARCHAR(10) NOT NULL,
    cantidad INT(11) DEFAULT 0,
    PRIMARY KEY(id_equipo, id_aula),
    CONSTRAINT FK_aulaequipo_aula_id_aula FOREIGN KEY (id_aula) REFERENCES AULAS(Aula_ID),
    CONSTRAINT FK_aulaequipo_equipo_id_equipo FOREIGN KEY (id_equipo) REFERENCES EQUIPOS(id_equipo));


DROP TABLE IF EXISTS USO_AULA_EQUIPOS;
CREATE TABLE USO_AULA_EQUIPOS(
    dia INT NOT NULL,
    espacio_tiempo INT NOT NULL,
    id_aula VARCHAR(10) NOT NULL,
    clv_grupo VARCHAR(10) NOT NULL,
    clv_materia VARCHAR(10) NOT NULL,
    PRIMARY KEY (dia, espacio_tiempo, id_aula, clv_grupo, clv_materia),
    CONSTRAINT UNK_dia_espaciotiempo_idaula UNIQUE (dia, espacio_tiempo, id_aula),
    CONSTRAINT UNK_dia_espaciotiempo_clvgrupo UNIQUE (dia, espacio_tiempo, clv_grupo),
    CONSTRAINT FK_usoaulagrupo_grupos_clv_grupo FOREIGN KEY (clv_grupo) REFERENCES GRUPOS(Grupo_ID),
    CONSTRAINT FK_usoaulagrupo_materias_clv_materia FOREIGN KEY (clv_materia) REFERENCES MATERIAS(Materia_ID),
    CONSTRAINT FK_usoaulagrupo_aulas_id_aula FOREIGN KEY (id_aula) REFERENCES AULAS(Aula_ID));
