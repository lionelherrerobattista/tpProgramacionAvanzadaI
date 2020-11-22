package servicios;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import anotaciones.Columna;
import anotaciones.Id;
import anotaciones.Tabla;
import utilidades.UBean;
import utilidades.UConexion;

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
		
		consulta = consulta.substring(0, consulta.length() - 2);
		consulta += ") values (";
		
		//Recorrer los atributos
		for(Field atributo: listaAtributos) {
			
			if(atributo.getType().getSimpleName().equals("String")) {
				consulta += "'" + UBean.ejecutarGet(o, atributo.getName()).toString() + "', ";
			} else {
				consulta += UBean.ejecutarGet(o, atributo.getName()).toString() + ", ";
			}
								
		}
		
		consulta = consulta.substring(0, consulta.length() - 2);
		consulta += ")";
			
		//Activar conexión
		//TODO hacer método para ejecutar consulta
		UConexion uConexion = new UConexion();
		try {
			uConexion.abrirConexion();
			Connection connection = uConexion.getConnection();
			
			//Ejecutar consulta
			//Ejecutar cualquier consulta
			PreparedStatement st = connection.prepareStatement(consulta);

			st.execute();			
			uConexion.cerrarConexion();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void modificar(Object o){
		String consulta = "update ";
		String id = "";
		ArrayList<Field> listaAtributos;
		
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
				
				consulta += "'" + UBean.ejecutarGet(o, atributo.getName()).toString() + "', ";	
			}
		}
		//Borrar última coma
		consulta = consulta.substring(0, consulta.length() - 2);
		
		//Terminar la consulta
		consulta += " where id=" + id;
		
		UConexion uConexion = new UConexion();
		try {
			uConexion.abrirConexion();
			Connection connection = uConexion.getConnection();
			
			//Ejecutar consulta
			//Ejecutar cualquier consulta
			PreparedStatement st = connection.prepareStatement(consulta);
			System.out.println(st);
			st.execute();			
			uConexion.cerrarConexion();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
		
		UConexion uConexion = new UConexion();
		try {
			uConexion.abrirConexion();
			Connection connection = uConexion.getConnection();
			
			//Ejecutar consulta
			//Ejecutar cualquier consulta
			PreparedStatement st = connection.prepareStatement(consulta);
			System.out.println(st);
			st.execute();			
			uConexion.cerrarConexion();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Object obtenerPorId(Class c, Object id) {
		String consulta = "select * from ";
		Object objeto = null;
		ArrayList<Field> listaAtributos;
		
		//Recuperar el constructor por defecto
		Constructor[] constructores = c.getConstructors();
		
		for(Constructor cons: constructores) {
			if(cons.getParameterCount()==0) {
				try {
					objeto = cons.newInstance(null);
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}		
		}
		
		//Obtener los atributos con UBean
		listaAtributos = UBean.obtenerAtributos(objeto);
		
		//Obtener el nombre de la tabla
		consulta += ((Tabla) c.getAnnotation(Tabla.class)).nombre() + " ";
		
		//Terminar la consulta
		consulta += "where id=" + id;
		
		UConexion uConexion = new UConexion();
		try {
			uConexion.abrirConexion();
			Connection connection = uConexion.getConnection();

			//Ejecutar consultas que requieren respuesta .executeQuery
			PreparedStatement stConsulta = connection.prepareStatement(consulta);
			ResultSet rs = stConsulta.executeQuery(); //enlace a la respuesta
			
			while(rs.next()) {
				
				for(Field atributo: listaAtributos) {
					
					switch(atributo.getType().getSimpleName()) {
					
						case "String":
							UBean.ejecutarSet(objeto, atributo.getAnnotation(Columna.class).nombre(), 
									rs.getString(atributo.getAnnotation(Columna.class).nombre()));							
							break;
						case "Long":
							UBean.ejecutarSet(objeto, atributo.getAnnotation(Columna.class).nombre(), 
									rs.getLong(atributo.getAnnotation(Columna.class).nombre()));
							break;
					}
				}
			}
			
			uConexion.cerrarConexion();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		return objeto;
		
	}
}
