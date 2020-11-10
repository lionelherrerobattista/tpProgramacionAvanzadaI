package entidades;

import anotaciones.Columna;
import anotaciones.Id;
import anotaciones.Tabla;

@Tabla(nombre="sys_persona")
public class Persona {
	@Id
	Integer id;
	@Columna(nombre="nombre")
	String nombre;
	@Columna(nombre="apellido")
	String apellido;

}
