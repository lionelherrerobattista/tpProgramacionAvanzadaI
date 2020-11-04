package utilidades;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;


public class UBean {
	
	/**
	 * Devuelve los atributos de un objeto
	 * @param obj objeto del que se quieren obtener los atributos
	 * @return ArrayList<Field> con los atributos
	 */
	public static ArrayList<Field> obtenerAtributos(Object obj) {
		
		ArrayList<Field> listaAtributos = new ArrayList<Field>();
		Class clase = obj.getClass(); //Obtener la clase
		
		//Obtener los atributos
		Field [] attrs = clase.getDeclaredFields();
		
		//Recorrer atributos y pasar a la lista
		for(Field f: attrs) {
			listaAtributos.add(f);
		}
		
		return listaAtributos;
		
	}
	
	/**
	 * Ejecuta el método set del objeto y le asigna el valor que se le pasa
	 * @param o objeto al que se le quiere asignar el valor
	 * @param att atributo al que se le quiere dar un valor
	 * @param valor que se le quiere asignar al atributo
	 */
	public static void ejecutarSet(Object o, String att, Object valor) {
		
		Class clase = o.getClass();
		String nombreSetter = "set" + att; //nombre del método
		
		Method[] metodos = clase.getDeclaredMethods();
		//tambien se puede encontrar el get method específico (+ performante)
		//clase.getMethod(nombreSetter, parameterTypes);
		
		//Buscar los set
		for(Method m:metodos) {
			//Buscar el nombre
			if(m.getName().equalsIgnoreCase(nombreSetter)) {
				
				//Buscar el 1° parametro
				Object[] params = new Object[1]; 
				
				params[1] = valor;
				
				//Invocar el método con el parámetro que se recibe
				try {
					m.invoke(o, params);
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
			}
		}			
	}
	
	/**
	 * Ejecuta el método get para el atributo que se le pasa como parámetro
	 * @param o objeto con el valor que se quiere recuperar
	 * @param att atributo que se quiere recuperar
	 * @return Object con el valor del atributo
	 */
	public static Object ejecutarGet(Object o, String att) {
		Class clase = o.getClass();
		String nombreGetter = "get" + att;
		Object valor = null;
		
		//Recuperar los métodos
		Method[] metodos = clase.getDeclaredMethods();
		
		//Buscar el get que corresponde
		for(Method m:metodos) {
			if(m.getName().equalsIgnoreCase(nombreGetter)) {
				//Invocar los get
				try {
					valor = m.invoke(o, null);
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
		
		return valor;
	}
	
}