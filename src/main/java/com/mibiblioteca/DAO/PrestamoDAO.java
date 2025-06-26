package com.mibiblioteca.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.mibiblioteca.Modelo.Libro;
import com.mibiblioteca.Modelo.Prestamo;
import com.mibiblioteca.Modelo.Usuario;

public class PrestamoDAO {
    
    private Connection conexion;
    private final String BASE_QUERY = "SELECT usuarios.id_usuario, usuarios.nombre, usuarios.apellido, usuarios.email,\n" + //
                        "libros.isbn, libros.nombre, libros.año, autores.nombre, autores.apellido1, autores.apellido2, generos.genero, libros.stock_actual, libros.stock_total,\n" + //
                        "prestamos.id_prestamo, prestamos.fecha_prestamo, prestamos.fecha_devolucion\n" + //
                        "FROM prestamos\n" + //
                        "JOIN usuarios ON prestamos.id_usuario = usuarios.id_usuario\n" + //
                        "JOIN libros ON prestamos.isbn = libros.isbn\n" + //
                        "JOIN autores ON libros.id_autor = autores.id_autor\n" + //
                        "JOIN generos ON libros.id_genero = generos.id_genero\n";

    public PrestamoDAO(Connection conexion){
        this.conexion = conexion;
    }

//----- Crear Prestamos -----
    /**
     * Metodo que inserta una nueva fila en la tabla prestamos
     */
    public void insertarPrestamo(int id_usuario, int isbn) throws SQLException{

        String sql = "INSERT INTO prestamos (id_usuario, isbn) VALUES (?, ?)";
        PreparedStatement stmt = conexion.prepareStatement(sql);
        stmt.setInt(1, id_usuario);
        stmt.setInt(2, isbn);
        stmt.executeUpdate();
        restaStock(isbn);
    }
    /**
     * Metodo que comprueba si el libro tiene stock disponible
     */
    public boolean comprobarStock(int isbn) throws SQLException{
        
        String sql = "SELECT stock_actual FROM libros WHERE isbn = ?";
        PreparedStatement stmt = conexion.prepareStatement(sql);
        stmt.setInt(1, isbn);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            int stock_actual = rs.getInt("stock_actual");
            return stock_actual > 0;
        } else {
            return false; 
        }
    }
    /**
     * Metodo que resta -1 a la columna stock_actual de la tabla libros
     */
    private void restaStock(int isbn) throws SQLException{

        String sql = "UPDATE libros SET stock_actual = stock_actual -1 WHERE isbn = ?";
        PreparedStatement stmt = conexion.prepareStatement(sql);
        stmt.setInt(1, isbn);
        stmt.executeUpdate();
    }

//----- Borrar Prestamos -----
    /**
     * Metodo que borra una fila de la tabla prestamos
     */
    public boolean deletePrestamo(int id_usuario, int isbn) throws SQLException{

        String sql = "DELETE FROM prestamos WHERE id_usuario = ? AND isbn = ? LIMIT 1;";

        PreparedStatement stmt = conexion.prepareStatement(sql);
        stmt.setInt(1, id_usuario);
        stmt.setInt(2, isbn);
        //Devuelve true si se elimina al menos una fila
        return stmt.executeUpdate() > 0;
    }
    /**
     * Metodo que comprueba si el libro tiene stock disponible
     */
    public boolean comprobarStockTotal(int isbn) throws SQLException{
        
        String sql = "SELECT stock_actual, stock_total FROM libros WHERE isbn = ?";
        PreparedStatement stmt = conexion.prepareStatement(sql);
        stmt.setInt(1, isbn);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            int stock_actual = rs.getInt("stock_actual");
            int stock_total = rs.getInt("stock_total");
            return stock_actual < stock_total;
        } else {
            return false; 
        }
    }
    /**
     * Metodo que suma +1 en la columna stock_actual de la tabla libros
     */
    public void sumaStock(int isbn) throws SQLException{

        String sql = "UPDATE libros SET stock_actual = stock_actual +1 WHERE isbn = ?";
        PreparedStatement stmt = conexion.prepareStatement(sql);
        stmt.setInt(1, isbn);
        stmt.executeUpdate();
    }

//----- Mostrar prestamos -----
    /**
     * Metodo que devuelve una lista con Prestamos segun la tabla prestamos
     */
    public List<Prestamo> obtenerTodos() throws SQLException {

        List<Prestamo> prestamos = new ArrayList<>();
        String sql = BASE_QUERY + "ORDER BY prestamos.id_usuario";
        Statement stmt = conexion.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            prestamos.add(crearPrestamo(rs));
        }
        return prestamos;
    }
    /**
     * Metodo que devuelve los prestamos filtrados por usuario
     */
    public List<Prestamo> filtroUsuario(int id_usuario) throws SQLException{

        List<Prestamo> prestamos = new ArrayList<>();
        String sql = BASE_QUERY + " WHERE prestamos.id_usuario = ? ORDER BY prestamos.id_usuario";
        PreparedStatement stmt = conexion.prepareStatement(sql);
        stmt.setInt(1, id_usuario);

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            prestamos.add(crearPrestamo(rs));
        }
        return prestamos;
    }



    /**
     * Metodo privado que crea un objeto Prestamo
     */
    private Prestamo crearPrestamo(ResultSet rs) throws SQLException{

        return new Prestamo(
                rs.getInt("prestamos.id_prestamo"), 
                new Usuario(
                    rs.getInt("id_usuario"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("email")),
                new Libro(rs.getInt("libros.isbn"),
                    rs.getString("libros.nombre"),
                    rs.getInt("libros.año"), 
                    rs.getString("autores.nombre") + " " + rs.getString("autores.apellido1") + " " + rs.getString("autores.apellido2"),
                    rs.getString("generos.genero"),
                    rs.getInt("libros.stock_actual"),
                    rs.getInt("libros.stock_total")),
                rs.getDate("prestamos.fecha_prestamo"), 
                rs.getDate("prestamos.fecha_devolucion")
            );
    }
    /**
     * Metodo que devuelve una lista con la info del historial de prestamos
     */
    public List<Prestamo> obtenerHistorialPrestamos() throws SQLException {

        List<Prestamo> prestamos = new ArrayList<>();
        String sql = "SELECT usuarios.id_usuario, usuarios.nombre, usuarios.apellido, usuarios.email,\n" + //
                        "libros.isbn, libros.nombre, libros.año, autores.nombre, autores.apellido1, autores.apellido2, generos.genero, libros.stock_actual, libros.stock_total,\n" + //
                        "historial_prestamos.id_prestamo, historial_prestamos.fecha_prestamo, historial_prestamos.fecha_devolucion\n" + //
                        "FROM historial_prestamos\n" + //
                        "JOIN usuarios ON historial_prestamos.id_usuario = usuarios.id_usuario\n" + //
                        "JOIN libros ON historial_prestamos.isbn = libros.isbn\n" + //
                        "JOIN autores ON libros.id_autor = autores.id_autor\n" + //
                        "JOIN generos ON libros.id_genero = generos.id_genero\n";

        Statement stmt = conexion.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            prestamos.add(new Prestamo(
                rs.getInt("historial_prestamos.id_prestamo"), 
                new Usuario(
                    rs.getInt("id_usuario"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("email")),
                new Libro(rs.getInt("libros.isbn"),
                    rs.getString("libros.nombre"),
                    rs.getInt("libros.año"), 
                    rs.getString("autores.nombre") + " " + rs.getString("autores.apellido1") + " " + rs.getString("autores.apellido2"),
                    rs.getString("generos.genero"),
                    rs.getInt("libros.stock_actual"),
                    rs.getInt("libros.stock_total")),
                rs.getDate("historial_prestamos.fecha_prestamo"), 
                rs.getDate("historial_prestamos.fecha_devolucion")
            ));
        }
        return prestamos;
    }
}
