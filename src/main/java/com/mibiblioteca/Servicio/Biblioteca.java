package com.mibiblioteca.Servicio;

import java.sql.*;
import java.util.List;
import com.mibiblioteca.ConexionDB.ConexionDB;
import com.mibiblioteca.DAO.LibroDAO;
import com.mibiblioteca.DAO.PrestamoDAO;
import com.mibiblioteca.DAO.UsuarioDAO;
import com.mibiblioteca.Modelo.Libro;
import com.mibiblioteca.Modelo.Prestamo;
import com.mibiblioteca.Modelo.Usuario;

public class Biblioteca {
    
    private Connection conexion;
    private PrestamoDAO p_dao;
    private LibroDAO l_dao;
    private UsuarioDAO u_dao;

    public Biblioteca() throws SQLException{
        this.conexion = ConexionDB.obtenerConexion();
        p_dao = new PrestamoDAO(conexion);
        l_dao = new LibroDAO(conexion);
        u_dao = new UsuarioDAO(conexion);
    }

//---------------------------------- Gestion de Libros ------------------------------------------------------------------------------------
    /**
     * Metodo para mostrar todos los Libros de la biblioteca
     */
    public void mostrarTablaLibros() throws SQLException{

        List<Libro> libros = l_dao.obtenerTodos();

        System.out.println("Total de libros en la biblioteca: " + libros.size());
        System.out.println("======================================================================================");
        for (Libro l : libros) {
            System.out.println(l.getIsbn() + " | " + l.getTitulo() + " - " + l.getAño() + " (" + l.getAutor() + " - " + l.getGenero() + ") " +
                                "Stock: " + l.getstock_actual() + "/" + l.getstock_total());
        }
    }
    /**
     * Metodo para buscar un libro por su ISBN
     */
    public void filtrarIsbn(int isbn) throws SQLException{

        Libro l = l_dao.filtroISBN(isbn);
        System.out.println(l.getIsbn() + " | " + l.getTitulo() + " - " + l.getAño() + " (" + l.getAutor() + " - " + l.getGenero() + ") " +
                                "Stock: " + l.getstock_actual() + "/" + l.getstock_total());        
    }
    /**
     * Metodo para buscar libros por su autor
     */
    public void filtrarAutor(String nombre, String apellido1, String apellido2) throws SQLException{

        List<Libro> libros = l_dao.filtroAutor(nombre, apellido1, apellido2);

        System.out.println("Total de libros del autor " + nombre +" "+ apellido1 +" "+ apellido2 + " en la biblioteca: " + libros.size());
        System.out.println("======================================================================================");
        for (Libro l : libros) {
            System.out.println(l.getAutor() + " | " + l.getTitulo() + " - " + l.getAño() + " - " + l.getGenero());
        }
    }
    /**
     * Metodo para buscar libros segun su genero
     */
    public void filtrarGenero(String genero) throws SQLException{

        List<Libro> libros = l_dao.filtroGenero(genero);

        System.out.println("Total de libros del genero " + genero + " en la biblioteca: " + libros.size());
        System.out.println("======================================================================================");
        for (Libro l : libros) {
            System.out.println(l.getGenero() + " | " + l.getTitulo() + " - " + l.getAño() + " - " + l.getAutor());
        }
    }

//---------------------------------- Gestion de Usuarios -----------------------------------------------------------------------------------
    /**
     * Metodo para buscar usuario segun su email
     */
    public void buscarUsuario(String email) throws SQLException{

        Usuario u = u_dao.consultaEmail(email);
        System.out.println(u.getId() + " - " + u.getNombre() + " - " + u.getApellido() + " - " + u.getEmail());
    }
    /**
     * Metodo que muestra toda la tabla de usuarios
     */
    public void mostrarTablaUsuarios() throws SQLException{

        List<Usuario> usuarios = u_dao.obtenerTodos();

        for (Usuario u : usuarios) {
            System.out.println(u.getId() + " - " + u.getNombre() + " - " + u.getApellido() + " - " + u.getEmail());
        }
    }
    /**
     * Metodo que registra un nuevo usuario en la base de datos
     */
    public void registrarUsuario(String nombre, String apellido, String email) throws SQLException{

        Usuario usuario = new Usuario(0, nombre, apellido, email);
        u_dao.insertarUsuario(usuario);
        System.out.println("Usuario registrado con éxito.");
    }
    /**
     * Metodo que borra un  usuario de la base de datos
     */
    public void eliminarUsuario(int id) throws SQLException{

        boolean eliminado = u_dao.deleteUsuario(id);
        if (eliminado) {
            System.out.println("Usuario eliminado con éxito.");
        } else {
            System.out.println("No se encontró ningún usuario con ID: " + id);
        }
    }

//---------------------------------- Gestion de Prestamos ----------------------------------------------------------------------------------
    /**
     * Metodo que registra un usario y un libro en la tabla prestamos
     */
    public void registrarPrestamo(int id_usuario, int isbn) throws SQLException{

        // Verificamos que tanto el libro como el usuario existan en sus respectivas tablas
        if (u_dao.filtroId(id_usuario) != null && l_dao.filtroISBN(isbn) != null) {
            if (p_dao.comprobarStock(isbn)) {
                p_dao.insertarPrestamo(id_usuario, isbn);
                System.out.println("Libro prestado con éxito.");
            } else {
                System.out.println("No queda stock del libro indicado.");
            }
        } 
    }
    /**
     * Metodo que muestra la informacion de los prestamos
     */
    public void mostrarTablaPrestamos() throws SQLException{

        List<Prestamo> prestamos = p_dao.obtenerTodos();
        
        for (Prestamo p : prestamos) {
            System.out.println(p.getId() + " | (" + p.getUsuario().getId() + ") " + p.getUsuario().getNombre() + " " + p.getUsuario().getApellido() +
                 " - (" + p.getLibro().getIsbn() + ") " + p.getLibro().getTitulo() + " | " + p.getFecha_prestamo());
        }
    }
    /**
     * Metodo que muestra los prestamos a un usuario
     */
    public void mostrarPrestamos(int id_usuario) throws SQLException{
    
        if (u_dao.filtroId(id_usuario) == null) {
                return;
            }

        List<Prestamo> prestamos = p_dao.filtroUsuario(id_usuario);
        if (prestamos.isEmpty()) {
            System.out.println("El usuario no tiene prestamos activos");           
        } else {
            System.out.println("Libros prestados actualmente a " + prestamos.get(0).getUsuario().getNombre() + "  " +
                                prestamos.get(0).getUsuario().getApellido() + ": " + prestamos.size());
            for (Prestamo p : prestamos) {
                System.out.println("- Id prestamo: " + p.getId() + " | " + p.getLibro().getTitulo());
            }
        }  
    }
    /**
     * Metodo que devuelve un libro prestado eliminandolo de la tabla de prestamos
     * El prestamo se inserta automaticamente en una tabla historial_prestamos
     */
    public void devolucion(int id_usuario, int isbn) throws SQLException{
        
        // Verificamos que tanto el libro como el usuario existan en sus respectivas tablas
        if (u_dao.filtroId(id_usuario) != null && l_dao.filtroISBN(isbn) != null) {
            if (p_dao.comprobarStockTotal(isbn)) {
                boolean eliminado = p_dao.deletePrestamo(id_usuario, isbn);
                if (eliminado) {
                    System.out.println("Libro devuelto con éxito.");
                    p_dao.sumaStock(isbn); // +1 al stock del libro
                } else {
                    System.out.println("El libro no se encuentra prestado al usuario.");
                }             
            } else {
                System.out.println("El stock ya esta completo.");
            }
        }
    }
    /**
     * Metodo que muestra la informacion del historial de prestamos
     */
    public void mostrarTablaHistorialPrestamos() throws SQLException{

        List<Prestamo> prestamos = p_dao.obtenerHistorialPrestamos();
        
        for (Prestamo p : prestamos) {
            System.out.println(p.getId() + " | " + p.getUsuario().getNombre() + " " + p.getUsuario().getApellido() +
                 " - " + p.getLibro().getTitulo() + " | " + p.getFecha_prestamo() + " ---> " + p.getFecha_devolucion());
        }
    }
}
