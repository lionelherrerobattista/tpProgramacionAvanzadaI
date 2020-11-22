package entidades;

import anotaciones.Columna;
import anotaciones.Id;
import anotaciones.Tabla;

@Tabla(nombre="sys_persona")
public class Persona {
	@Id
	@Columna(nombre="id")
	private Long id;
	@Columna(nombre="nombre")
	private String nombre;
	@Columna(nombre="apellido")
	private String apellido;
	
	public Persona() {
		
	}
	
	public Persona(Long id, String nombre, String apellido) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	@Override
	public String toString() {
		return "Persona [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + "]";
	}
	
	

}
