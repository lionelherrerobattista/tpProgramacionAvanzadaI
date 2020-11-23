package utilidades;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UConexion {

	private static UConexion uConexion; //Para guardar la instancia
	private Connection connection;
	private String driver;
	private String pathConexion;
	private String pass;
	private String usuario;
	
	private UConexion() {
		
	}
	

	public Connection getConnection() {
		return connection;
	}


	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	
	//Genero adentro de un método la única instancia de la clase
	public static UConexion getInstance() {
		
		//If para que sea solo 1 instancia
		if(uConexion == null) {
			uConexion = new UConexion();	
		}
		
		return uConexion;
	}
	
	
	/**
	 * Abre una instancia de la base de datos mySQL
	 */
	public void abrirConexion() throws ClassNotFoundException, SQLException {
		Class.forName(this.driver);
		this.connection = DriverManager.getConnection(this.pathConexion, this.usuario, this.pass);	
	}

	
	/**
	 * Cierra una conexión abierta en connection
	 */
	public void cerrarConexion() throws SQLException {
		this.connection.close();
	}
	
	public void read(File f) {
		List<String> listaDatos = new ArrayList<>();
		Map<String, String> config = new HashMap<>(); 
		
		try {
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String linea;
			
			linea = br.readLine();
			
			//Recorrer todo el archivo:
			while(linea != null) {
				listaDatos.add(linea);
				linea = br.readLine();
			}
			
			br.close();//siempre forzar que se cierre el archivo
			String[] array;
			
			for(String dato:listaDatos) {
				if(dato.split("=").length > 1) {
					config.put(dato.split("=")[0], dato.split("=")[1]);	
				} else {
					config.put(dato.split("=")[0], "");
				}
				
			}
			
			this.driver = config.get("driver");
			this.pathConexion = config.get("pathConexion");
			this.usuario = config.get("usuario");
			this.pass = config.get("pass");
					
		} catch (FileNotFoundException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
