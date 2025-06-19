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
    
    Connection conexion;

    public Biblioteca() throws SQLException{
        this.conexion = ConexionDB.obtenerConexion();
    }

//---------------------------------- Gestion de Libros ------------------------------------------------------------------------------------
    /**
     * Metodo para mostrar todos los Libros de la biblioteca
     */
    public void mostrarTablaLibros() throws SQLException{

        LibroDAO dao = new LibroDAO(conexion);
        List<Libro> libros = dao.obtenerTodos();

        System.out.println("Total de libros en la biblioteca: " + libros.size());
        System.out.println("======================================================================================");
        for (Libro l : libros) {
            System.out.println(l.getIsbn() + " | " + l.getTitulo() + " - " + l.getAño() + " (" + l.getAutor() + " - " + l.getGenero() + ")");
        }
    }
    /**
     * Metodo para buscar un libro por su ISBN
     */
    public void filtrarIsbn(int isbn) throws SQLException{

        LibroDAO dao = new LibroDAO(conexion);
        Libro l = dao.filtroISBN(isbn);
        System.out.println(l.getIsbn() + " | " + l.getTitulo() + " - " + l.getAño() + " (" + l.getAutor() + " - " + l.getGenero() + ")");        
    }
    /**
     * Metodo para buscar libros por su autor
     */
    public void filtrarAutor(String nombre, String apellido1, String apellido2) throws SQLException{

        LibroDAO dao = new LibroDAO(conexion);
        List<Libro> libros = dao.filtroAutor(nombre, apellido1, apellido2);

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

        LibroDAO dao = new LibroDAO(conexion);
        List<Libro> libros = dao.filtroGenero(genero);

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

        UsuarioDAO dao = new UsuarioDAO(conexion);
        Usuario u = dao.consultaEmail(email);
        System.out.println(u.getId() + " - " + u.getNombre() + " - " + u.getApellido() + " - " + u.getEmail());
    }
    /**
     * Metodo que muestra toda la tabla de usuarios
     */
    public void mostrarTablaUsuarios() throws SQLException{

        UsuarioDAO dao = new UsuarioDAO(conexion);
        List<Usuario> usuarios = dao.obtenerTodos();

        for (Usuario u : usuarios) {
            System.out.println(u.getId() + " - " + u.getNombre() + " - " + u.getApellido() + " - " + u.getEmail());
        }
    }
    /**
     * Metodo que registra un nuevo usuario en la base de datos
     */
    public void registrarUsuario(String nombre, String apellido, String email) throws SQLException{

        Usuario usuario = new Usuario(0, nombre, apellido, email);
        UsuarioDAO dao = new UsuarioDAO(conexion);
        dao.insertarUsuario(usuario);
        System.out.println("Usuario registrado con éxito.");
    }
    /**
     * Metodo que borra un  usuario de la base de datos
     */
    public void eliminarUsuario(int id) throws SQLException{

        UsuarioDAO dao = new UsuarioDAO(conexion);
        boolean eliminado = dao.deleteUsuario(id);
        if (eliminado) {
            System.out.println("Usuario eliminado con éxito.");
        } else {
            System.out.println("No se encontró ningún usuario con ese ID.");
        }
    }

//---------------------------------- Gestion de Prestamos ----------------------------------------------------------------------------------
    /**
     * Metodo que registra un usario y un libro en la tabla prestamos
     */
    public void registrarPrestamo(int id_usuario, int isbn) throws SQLException{

        UsuarioDAO u_dao = new UsuarioDAO(conexion);
        LibroDAO l_dao = new LibroDAO(conexion);
        PrestamoDAO p_dao = new PrestamoDAO(conexion);
        // Verificamos que tanto el libro como el usuario existan en sus respectivas tablas
        if (u_dao.filtroId(id_usuario) != null && l_dao.filtroISBN(isbn) != null) {
            if (!p_dao.comprobarPrestamo(isbn)) {
                p_dao.insertarPrestamo(id_usuario, isbn);
                System.out.println("Libro prestado con éxito.");
            } else {
                System.out.println("El libro ya se encuentra prestado");
            }
        } 
    }
    /**
     * Metodo que muestra todas las filas de la tabla prestamos
     */
    public void mostrarTablaPrestamos() throws SQLException{

        PrestamoDAO dao = new PrestamoDAO(conexion);
        List<Prestamo> prestamos = dao.obtenerTodos();
        
        for (Prestamo p : prestamos) {
            System.out.println(p.getId() + " | " + p.getUsuario().getNombre() + " " + p.getUsuario().getApellido() +
                 " - " + p.getLibro().getTitulo() + " | " + p.getFecha_prestamo());
        }
    }
    /**
     * Metodo que muestra los prestamos a un usuario
     * !!!Falla cuando se introduce un usuario sin prestamos!!!
     */
    public void mostrarPrestamos(int id_usuario) throws SQLException{

        PrestamoDAO dao = new PrestamoDAO(conexion);
        List<Prestamo> prestamos = dao.filtroUsuario(id_usuario);
        
        boolean tienePrestamosActivos = false;
        System.out.println("Libros prestados actualmente a " + prestamos.get(0).getUsuario().getNombre() + "  " +
                            prestamos.get(0).getUsuario().getApellido() + ": " + prestamos.size());
        for (Prestamo p : prestamos) {
                System.out.println("- Id prestamo: " + p.getId() + " | " + p.getLibro().getTitulo());
                tienePrestamosActivos = true;
            }
        if (!tienePrestamosActivos) {
            System.out.println("El usuario no existe o no tiene préstamos activos");
        }
    }
    /**
     * Metodo que devuelve un libro prestado eliminandolo de la tabla de prestamos
     */
    public void devolucion(int id) throws SQLException{
        PrestamoDAO dao = new PrestamoDAO(conexion);
        boolean eliminado = dao.deletePrestamo(id);
        if (eliminado) {
            System.out.println("Usuario eliminado con éxito.");
        } else {
            System.out.println("No se encontró ningún usuario con ese ID.");
        }
    }
}
