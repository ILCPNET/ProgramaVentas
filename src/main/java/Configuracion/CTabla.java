/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Configuracion;

/**
 *
 * @author Ruben
 */
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class CTabla {

    public CTabla(Connection conectar) {
        this.conectar = conectar;
    }

    private Connection conectar;

    public void crearTablas() {
        try {
            Statement stmt = conectar.createStatement();

            // Crear tabla Clientes
            String crearTablaClientes = "CREATE TABLE IF NOT EXISTS cliente ("
                    + "idcliente INT AUTO_INCREMENT PRIMARY KEY, "
                    + "Nombre VARCHAR(100) NOT NULL, "
                    + "Dni VARCHAR(45), "
                    + "Telefono INT, "
                    + "Direccion VARCHAR(45), "
                    + "Localidad VARCHAR(45), "
                    + "Email VARCHAR(45), "
                    + "UfechaModificacion TIMESTAMP)";
            stmt.executeUpdate(crearTablaClientes);

            // Crear tabla Productos
            String crearTablaProductos = "CREATE TABLE IF NOT EXISTS producto ("
                    + "idproducto INT AUTO_INCREMENT PRIMARY KEY, "
                    + "Producto VARCHAR(150), "
                    + "Neto DECIMAL(10,2), "
                    + "Iva DECIMAL(5,2), "
                    + "PrecioProducto DECIMAL(10,2), "
                    + "Stock INT, "
                    + "UltimaModificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP)";
            stmt.executeUpdate(crearTablaProductos);

            // Verificar si las columnas existen antes de agregarlas
            DatabaseMetaData dbMetaData = conectar.getMetaData();
            ResultSet rs1 = dbMetaData.getColumns(null, null, "producto", "Clasificacion1");
            ResultSet rs2 = dbMetaData.getColumns(null, null, "producto", "Clasificacion2");

            boolean existeClasificacion1 = rs1.next(); // Verifica si hay resultados para la columna Clasificacion1
            boolean existeClasificacion2 = rs2.next(); // Verifica si hay resultados para la columna Clasificacion2

            // Solo agregar columnas si no existen
            if (!existeClasificacion1 || !existeClasificacion2) {
                String agregarTablaProductos = "ALTER TABLE producto "
                        + (!existeClasificacion1 ? "ADD COLUMN Clasificacion1 VARCHAR(45) NULL AFTER UltimaModificacion " : "")
                        + (!existeClasificacion1 && !existeClasificacion2 ? ", " : "")
                        + (!existeClasificacion2 ? "ADD COLUMN Clasificacion2 VARCHAR(45) NULL AFTER Clasificacion1" : "");

                stmt.executeUpdate(agregarTablaProductos);
            }

            // Crear tabla Detalle
            String crearTablaDetalle = "CREATE TABLE IF NOT EXISTS detalle ("
                    + "iddetalle INT AUTO_INCREMENT PRIMARY KEY, "
                    + "Fkfactura INT, "
                    + "FkProducto INT, "
                    + "Cantidad INT, "
                    + "PrecioVenta DECIMAL(10,2))";
            stmt.executeUpdate(crearTablaDetalle);

            // Crear tabla Factura
            String crearTablaFactura = "CREATE TABLE IF NOT EXISTS factura ("
                    + "idfactura INT AUTO_INCREMENT PRIMARY KEY, "
                    + "FechaFacturacion TIMESTAMP, "
                    + "FCliente INT)";
            stmt.executeUpdate(crearTablaFactura);

            // Crear tabla Proveedor
            String crearTablaProveedor = "CREATE TABLE IF NOT EXISTS proveedor ("
                    + "idproveedor INT AUTO_INCREMENT PRIMARY KEY, "
                    + "nombre VARCHAR(45), "
                    + "cuit INT, "
                    + "Direccion VARCHAR(45), "
                    + "Localidad VARCHAR(45), "
                    + "Email VARCHAR(45), "
                    + "Telefono INT, "
                    + "UfechaModificacion TIMESTAMP)";
            stmt.executeUpdate(crearTablaProveedor);

            // Crear tabla Usuarios
            String crearTablaUsuarios = "CREATE TABLE IF NOT EXISTS usuarios ("
                    + "ingresousuario VARCHAR(45) PRIMARY KEY, "
                    + "ingresocontrasenia VARCHAR(45) NOT NULL)";
            stmt.executeUpdate(crearTablaUsuarios);

            // Verificar si el usuario 'admin' ya existe
            String verificarAdmin = "SELECT COUNT(*) FROM usuarios WHERE ingresousuario = 'admin'";
            ResultSet rs = stmt.executeQuery(verificarAdmin);

            // Si no existe, insertar usuario admin
            if (rs.next() && rs.getInt(1) == 0) {
                String insertarAdmin = "INSERT INTO usuarios (ingresousuario, ingresocontrasenia) "
                        + "VALUES ('admin', '123456')";
                stmt.executeUpdate(insertarAdmin);
            }

            stmt.close(); // Cerrar el Statement

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al crear las tablas: " + e.toString());
        }
    }
}
