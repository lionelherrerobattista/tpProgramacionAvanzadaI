package program;

import entidades.Persona;
import servicios.Consultas;
import utilidades.UBean;

public class Program {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Persona p = new Persona(1l, "Juan", "Perez");
		
		Consultas.guardar(p);

		Consultas.modificar(p);
		Consultas.eliminar(p);
		
		
	}

}
