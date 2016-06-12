package canciones.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
	
	//por defecto usa la BD musica en localhost
	private static  String url = "jdbc:mysql://localhost/musica";
	private static String user = "padmin";
	private static String password = "duni";
	
	
	public static Connection creaConexion() throws SQLException {
		Connection connection;
		connection = DriverManager.getConnection(url, user, password);
		return connection;
	}
	
	public static void conexionDefecto(){
		ConexionBD.setUrl("jdbc:mysql://localhost/musica");
		ConexionBD.setUser("padmin");
		ConexionBD.setPassword("duni");
	}

	public static String getUrl() {
		return url;
	}
	
	public static String getUrlUrl(String loQueQuiero) {
		String urlYTabla = url.substring(13);
		String[] corte = urlYTabla.split("/");
		if (loQueQuiero=="url") {
			return corte[0];			
		}
		else if (loQueQuiero=="tabla"){
			return corte[1];
		}
		else {
			return urlYTabla;
		}

	}
	

	public static void setUrl(String _url) {
		ConexionBD.url = _url;
	}

	public static String getUser() {
		return user;
	}

	public static void setUser(String _user) {
		ConexionBD.user = _user;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String _password) {
		ConexionBD.password = _password;
	}

	public static void cargaDriver() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			System.exit(0);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}