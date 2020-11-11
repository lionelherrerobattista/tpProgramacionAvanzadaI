package servicios;

import java.lang.reflect.Field;
import java.util.ArrayList;

import anotaciones.Columna;
import anotaciones.Id;
import anotaciones.Tabla;
import utilidades.UBean;

public class Consultas {

	public static void guardar(Object o){
		String consulta = "insert into ";
		ArrayList<Field> listaAtributos;
		
		//Obtener el nombre de la tabla
		consulta += o.getClass().getAnnotation(Tabla.class).nombre() + " (";
		//Obtener los atributos con UBean
		listaAtributos = UBean.obtenerAtributos(o);
		
		//Recorrer los atributos
		for(Field atributo: listaAtributos) {
			//Agregar el nombre al string
			consulta += atributo.getAnnotation(Columna.class).nombre() + ", ";
		}
		
		//Sacar último string
		consulta = consulta.substring(0, consulta.length() - 2);
		
		//Terminar la consulta
		consulta += ") values (?,?,?)";
		
		//TODO ejecutar la query
		System.out.println(consulta);		
	}
	
	public static void modificar(Object o){
		String consulta = "update ";
		String id = "";
		ArrayList<Field> listaAtributos;
		
		// "UPDATE MyGuests SET lastname='Doe' WHERE id=2"
		
		//Obtener el nombre de la tabla
		consulta += o.getClass().getAnnotation(Tabla.class).nombre() + " set ";
		//Obtener los atributos con UBean
		listaAtributos = UBean.obtenerAtributos(o);
		
		//Recorrer los atributos
		for(Field atributo: listaAtributos) {
			
			if(atributo.getAnnotation(Columna.class).nombre().equals("id")) {
				id = UBean.ejecutarGet(o, atributo.getName()).toString();
			} else {
				//Agregar el nombre de la columna
				consulta += atributo.getAnnotation(Columna.class).nombre() + "=";
				//Agregar el valor
				
				consulta += "'" + UBean.ejecutarGet(o, atributo.getName()).toString() + "' ";	
			}
		}
		
		//Terminar la consulta
		consulta += "where id=" + id;
		
		//TODO ejecutar la query
		System.out.println(consulta);
	}
	
	public static void eliminar(Object o) {
		String consulta = "delete from ";
		String id = "";
		ArrayList<Field> listaAtributos;

		//Obtener el nombre de la tabla
		consulta += o.getClass().getAnnotation(Tabla.class).nombre() + " ";
		//Obtener los atributos con UBean
		listaAtributos = UBean.obtenerAtributos(o);
		
		//Recorrer los atributos
		for(Field atributo: listaAtributos) {
			
			if(atributo.getAnnotation(Columna.class).nombre().equals("id")) {
				id = UBean.ejecutarGet(o, atributo.getName()).toString();
				break;
			}
		}
		
		//Terminar la consulta
		consulta += "where id=" + id;
		
		//TODO ejecutar la query
		System.out.println(consulta);
	}
	
	public static void obtenerPorId(Class c, Object id) {
		
	}
}
