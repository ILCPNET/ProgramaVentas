
package Configuracion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Ruben
 */
public class CConexion {

    Connection conectar = null;

    String usuario = "root";
    String contrasenia = "root";
    String bd = "Carolintia";
    String ip = "localhost";
    String puerto = "3307";

    String cadena = "jdbc:mysql://" + ip + ":" + puerto + "/" + bd + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    public Connection estableceConexion() {
        try {
            // Cargar el driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Conectar sin seleccionar una base de datos para crearla si no existe
            conectar = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + puerto + "/", usuario, contrasenia);
            Statement stmt = conectar.createStatement();
            
            // Crear la base de datos si no existe
            String crearBD = "CREATE DATABASE IF NOT EXISTS " + bd;
            stmt.executeUpdate(crearBD);

            // Cerrar la conexión temporal y conectar a la base de datos creada
            conectar.close();
            conectar = DriverManager.getConnection(cadena, usuario, contrasenia);

            stmt.close(); // Cerrar el Statement
            
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Conexion Fallida: " + e.toString());
        }
        return conectar;
    }

    public void cerrarConexion() {
        try {
            if (conectar != null && !conectar.isClosed()) {
                conectar.close();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cerrar la conexión: " + e.toString());
        }
    }

}