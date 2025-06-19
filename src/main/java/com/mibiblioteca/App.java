package com.mibiblioteca;

import com.mibiblioteca.ConexionDB.ConexionDB;
import com.mibiblioteca.Servicio.Biblioteca;

/**
 * Crear un sistema para gestionar los libros de una biblioteca. 
 * El sistema permitirá registrar libros, usuarios, préstamos y devoluciones. 
 */
public class App {
    public static void main(String[] args) throws Exception {

        Biblioteca biblioteca = new Biblioteca();
        biblioteca.mostrarTablaLibros();
        System.out.println("============================================================");
        biblioteca.mostrarTablaPrestamos();

        ConexionDB.cerrarConexion();

    }
}
