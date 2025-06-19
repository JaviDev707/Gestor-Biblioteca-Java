package com.mibiblioteca.ConexionDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    private static final String URL = "jdbc:mysql://localhost:3306/Biblioteca_Java";
    private static final String USUARIO = "root";
    private static final String CONTRASEÑA = "Root7007";

    private static Connection conexion = null;

    // Constructor privado para evitar instanciacion
    private ConexionDB() {}

    // Método estatico para obtener la conexión
    public static Connection obtenerConexion() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            conexion = DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
        }
        return conexion;
    }

    // Método para cerrar conexion manualmente 
    public static void cerrarConexion() throws SQLException {
        if (conexion != null && !conexion.isClosed()) {
            conexion.close();
        }
    }
}
