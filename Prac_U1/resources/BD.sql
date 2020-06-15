DROP TABLE IF EXISTS `Aula`;
CREATE TABLE `Aula` (
  `Aula_ID` varchar(10) NOT NULL,
  `Nombre` varchar(40) NOT NULL,
  `Capacidad` int NOT NULL,
  PRIMARY KEY (`Aula_ID`));

DROP TABLE IF EXISTS `Carrera`;
CREATE TABLE `Carrera` (
  `Carrera_ID` varchar(10) NOT NULL,
  `Nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`Carrera_ID`));

DROP TABLE IF EXISTS `Plan`;
CREATE TABLE `Plan` (
  `Plan_ID` varchar(20) NOT NULL,
  `Nivel` varchar(3) NOT NULL,
  `Carrera_ID` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`Plan_ID`),
  CONSTRAINT `Plan_ibfk_1` FOREIGN KEY (`Carrera_ID`) REFERENCES `Carrera` (`Carrera_ID`));
  
  DROP TABLE IF EXISTS `Materias`;
CREATE TABLE `Materias` (
  `Materia_ID` varchar(10) NOT NULL,
  `Nombre` varchar(60) NOT NULL,
  `Creditos` int NOT NULL,
  `Cuatrimestre` int NOT NULL,
  `Hrs_sem` int NOT NULL,
  `Plan_ID` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`Materia_ID`),
  CONSTRAINT `Materias_ibfk_1` FOREIGN KEY (`Plan_ID`) REFERENCES `Plan` (`Plan_ID`));
  
  DROP TABLE IF EXISTS `Profesor`;
CREATE TABLE `Profesor` (
  `Profesor_ID` varchar(10) NOT NULL,
  `Nombre` varchar(70) NOT NULL,
  `Grado` varchar(50) NOT NULL,
  `Contrato` varchar(50) NOT NULL,
  `Carrera_ID` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`Profesor_ID`),
  CONSTRAINT `Profesor_ibfk_1` FOREIGN KEY (`Carrera_ID`) REFERENCES `Carrera` (`Carrera_ID`));

DROP TABLE IF EXISTS `Confianza`;
CREATE TABLE `Confianza` (
  `Materia_ID` varchar(10) ,
  `Plan_ID` varchar(20) NOT NULL,
  `Profesor_ID` varchar(10) ,
  `P_Conf` int NOT NULL,
  `P_Conf_Dir` int DEFAULT '0',
  PRIMARY KEY (`Materia_ID`,`Profesor_ID`),
  CONSTRAINT conf_fk1 FOREIGN KEY (Materia_ID) REFERENCES Materias(Materia_ID),
  CONSTRAINT conf_fk2 FOREIGN KEY (Profesor_ID) REFERENCES Profesor(Profesor_ID));

DROP TABLE IF EXISTS `Disponibilidad`;
CREATE TABLE `Disponibilidad` (
  `Dia` int NOT NULL,
  `Horas` varchar(30) NOT NULL,
  `Profesor_ID` varchar(10) NOT NULL,
  PRIMARY KEY (`Profesor_ID`,`Dia`),
  CONSTRAINT `Disponibilidad_ibfk_1` FOREIGN KEY (`Profesor_ID`) REFERENCES `Profesor` (`Profesor_ID`));

DROP TABLE IF EXISTS `Grupo`;
CREATE TABLE `Grupo` (
  `Grupo_ID` varchar(10) NOT NULL,
  `Materia_ID` varchar(10) NOT NULL,
  `Profesor_ID` varchar(10) NOT NULL,
  PRIMARY KEY (`Grupo_ID`,`Materia_ID`,`Profesor_ID`),
  CONSTRAINT `Grupo_ibfk_1` FOREIGN KEY (`Materia_ID`) REFERENCES `Materias` (`Materia_ID`),
  CONSTRAINT `Grupo_ibfk_2` FOREIGN KEY (`Profesor_ID`) REFERENCES `Profesor` (`Profesor_ID`));

DROP TABLE IF EXISTS `LogIn`;
CREATE TABLE `LogIn` (
  `Log_ID` int NOT NULL,
  `Profesor_ID` varchar(10) DEFAULT NULL,
  `Contrasena` varchar(30) DEFAULT NULL,
  `Tipo` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`Log_ID`),
  CONSTRAINT `LogIn_ibfk_1` FOREIGN KEY (`Profesor_ID`) REFERENCES `Profesor` (`Profesor_ID`));

DROP TABLE IF EXISTS `Prestamo`;
CREATE TABLE `Prestamo` (
  `Profesor_ID` varchar(10) NOT NULL,
  `Carrera_ID` varchar(10) NOT NULL,
  CONSTRAINT `Prestamo_ibfk_1` FOREIGN KEY (`Profesor_ID`) REFERENCES `Profesor` (`Profesor_ID`),
  CONSTRAINT `Prestamo_ibfk_2` FOREIGN KEY (`Carrera_ID`) REFERENCES `Carrera` (`Carrera_ID`));
  
CREATE TABLE categorias_equipo(
    id_categoria INT(11) NOT NULL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(300)
);

CREATE TABLE equipo(
    id_equipo INT(11) NOT NULL PRIMARY KEY,
    id_categoria INT(11) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(100) NOT NULL,
    CONSTRAINT FK_equipo_categoriaequipo_id_categoria FOREIGN KEY (id_categoria) REFERENCES categorias_equipo(id_categoria)
);

CREATE TABLE aula_equipo(
    id_equipo INT(11) NOT NULL,
    id_aula VARCHAR(10) NOT NULL,
    cantidad INT(11) DEFAULT 0,
    PRIMARY KEY(id_equipo, id_aula),
    CONSTRAINT FK_aulaequipo_aula_id_aula FOREIGN KEY (id_aula) REFERENCES Aula(Aula_ID),
    CONSTRAINT FK_aulaequipo_equipo_id_equipo FOREIGN KEY (id_equipo) REFERENCES equipo(id_equipo)
);

CREATE TABLE uso_aula_grupo(
    dia TINYINT NOT NULL,
    espacio_tiempo TINYINT NOT NULL,
    id_aula VARCHAR(10) NOT NULL,
    clv_grupo VARCHAR(10) NOT NULL,
    clv_materia VARCHAR(10) NOT NULL,
    PRIMARY KEY (dia, espacio_tiempo, id_aula, clv_grupo, clv_materia),
    CONSTRAINT UNK_dia_espaciotiempo_idaula UNIQUE (dia, espacio_tiempo, id_aula),
    CONSTRAINT UNK_dia_espaciotiempo_clvgrupo UNIQUE (dia, espacio_tiempo, clv_grupo),
    CONSTRAINT FK_usoaulagrupo_grupos_clv_grupo FOREIGN KEY (clv_grupo) REFERENCES Grupo(Grupo_ID),
    CONSTRAINT FK_usoaulagrupo_materias_clv_materia FOREIGN KEY (clv_materia) REFERENCES Materias(Materia_ID),
    CONSTRAINT FK_usoaulagrupo_aulas_id_aula FOREIGN KEY (id_aula) REFERENCES Aula(Aula_ID));
