/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.rubefact;

import Configuracion.CConexion;
import Configuracion.CTabla;
import Formulario.FormLogin;
import java.sql.Connection;

/**
 *
 * @author Ruben
 */
public  class RubeFact {

    public static void main(String[] args) {
       
        CConexion conexionBD = new CConexion();
        Connection conexion = conexionBD.estableceConexion();
        
        // Si la conexión es exitosa, crear las tablas
        if (conexion != null) {
            CTabla tabla = new CTabla(conexion);
            tabla.crearTablas();  // Crear las tablas si no existen
        }
        
        // Cerrar la conexión después de crear las tablas
        conexionBD.cerrarConexion();
        
        
        
        
          Formulario.FormLogin objetoLogin = new FormLogin();
       objetoLogin.setVisible(true);
    }
        //Formulario.MenuPrincipal Menu= new Formulario.MenuPrincipal();
        //Menu.setVisible(true);
        
        
    }

