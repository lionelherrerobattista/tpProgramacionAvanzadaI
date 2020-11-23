package program;

import java.io.File;

import entidades.Persona;
import servicios.Consultas;
import utilidades.UBean;
import utilidades.UConexion;

public class Program {

	public static void main(String[] args) {
		
		File configuracion = new File("framework.properties");
		UConexion uConexion = UConexion.getInstance();
		uConexion.read(configuracion);
		// TODO Auto-generated method stub
		Persona p = new Persona(3l, "Maria", "Gonzalez");
		
		Consultas.guardar(p);

		//Consultas.modificar(p);
		//Consultas.eliminar(p);
		//System.out.println(Consultas.obtenerPorId(Persona.class, 4));
		
		
	}

}
