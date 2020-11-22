package utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UConexion {

	private static UConexion uConexion; //Para guardar la instancia
	
	Connection connection;
	

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
		Class.forName("com.mysql.jdbc.Driver");
		String pathConexion = "jdbc:mysql://localhost:3306/test";
		this.connection = DriverManager.getConnection(pathConexion, "root", "");	
	}

	
	/**
	 * Cierra una conexión abierta en connection
	 */
	public void cerrarConexion() throws SQLException {
		this.connection.close();
	}
}
