package com.mibiblioteca;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Biblioteca {
    
    private HashMap<String, Libro> mapaLibros = new HashMap<>();
    private HashMap<Integer, Usuario> mapaUsuarios = new HashMap<>();
    private HashMap<Usuario, ArrayList<Prestamo>> mapaPrestamos = new HashMap<>();

//---------------------------------- Gestion de Libros ------------------------------------------------------------------------------------
    /**
     * Metodo que registra un libro en mapaLibros para buscarlo por su isbn
     */
    public void registrarLibro(Libro libro){
        String isbn = libro.getIsbn();
        mapaLibros.put(isbn, libro);
    }
    /**
     * Metodo para mostrar los Libros de la biblioteca
     */
    public void mostrarLibros(){
        System.out.println("Total de libros: " + mapaLibros.size());
        for (Libro l : mapaLibros.values()) {
            System.out.println(l.getIsbn() + " - " + l.getTitulo() + " - " + l.getGenero() + " (" + l.getAutor() + ", " + l.getAño() + ")");
        }
    }
    /**
     * Metodo para buscar libros por su ISBN
     */
    public void buscarLibro(String isbn){
        Libro libro = mapaLibros.get(isbn);
        if (libro != null) {
            System.out.println("ISBN: " + libro.getIsbn());
            System.out.println("Título: " + libro.getTitulo());
            System.out.println("Año: " + libro.getAño());
            System.out.println("Género: " + libro.getGenero());
            // Buscamos si el libro esta prestado o disponible
            for(Map.Entry<Usuario, ArrayList<Prestamo>> entrada : mapaPrestamos.entrySet()) {
                ArrayList<Prestamo> lista = entrada.getValue();
                for (Prestamo prestamo : lista) {
                    if (prestamo.getLibro().equals(libro) && !prestamo.estaDevuelto()) {
                        System.out.println("El libro se encuentra prestado al usuario " + entrada.getKey().getNombre());
                        return;
                    }
                }
            } 
            System.out.println("El libro esta disponible para su prestamo");
        } else {
            System.out.println("El libro con ISBN " + isbn + " no existe.");
        }        
    }

//---------------------------------- Gestion de Usuarios -----------------------------------------------------------------------------------
    /**
     * Metodo que registra un usuario en mapaUsuarios para buscarlo por su id
     */
    public void registrarUsuario(Usuario usuario){
        int id = usuario.getId();
        mapaUsuarios.put(id, usuario);
    }
    /**
     * Metodo para buscar usuario segun su id
     */
    public void buscarUsuario(int id){
        Usuario usuario = mapaUsuarios.get(id);
        if (usuario != null) {
            System.out.println("Id: " + id + " - " + mapaUsuarios.get(id).getNombre() + " - " + mapaUsuarios.get(id).getEmail());
        } else {
            System.out.println("El usuario " + id + " no existe");
        }
    }

//---------------------------------- Gestion de Prestamos ----------------------------------------------------------------------------------
    /**
     * Metodo que registra los prestamos en un hashmap de usuarios y pretamos
     */
    private void registrarPrestamo(Prestamo prestamo){
        Usuario usuario = prestamo.getUsuario();
        ArrayList<Prestamo> listaPrestamos = mapaPrestamos.get(usuario);
        //El usuario hace su primer prestamo
        if (listaPrestamos == null) {
            listaPrestamos = new ArrayList<>();
            listaPrestamos.add(prestamo);
            mapaPrestamos.put(usuario, listaPrestamos);
        //El usuario ya tiene mas prestamos
        //No es necesario usar .put() de nuevo porque esta modificando la lista que ya está en el mapa.
        } else {
            listaPrestamos.add(prestamo);
        }
    }
    /**
     * Metodo para mostrar los prestamos de un usuario
     * Verifica la fecha de devolucion para determinar los prestamos activos (prestamo.estaDevuelto())
     */
    public void mostrarPrestamos(Usuario usuario){
        ArrayList<Prestamo> lista = mapaPrestamos.get(usuario);
        //En caso de que el usuario no tenga prestamos
        if (lista == null || lista.isEmpty()) {
        System.out.println("El usuario " + usuario.getNombre() + " no tiene préstamos activos.");
        return;
        }
        boolean tienePrestamosActivos = false;
        System.out.println("Libros prestados actualmente al usuario " + usuario.getNombre() + ":");
        for (Prestamo prestamo : lista) {
            if (!prestamo.estaDevuelto()) {
                System.out.println("- " + prestamo.getLibro().getTitulo());
                tienePrestamosActivos = true;
            }
        }
        if (!tienePrestamosActivos) {
            System.out.println("no tiene préstamos activos");
        }
    }
    /**
     * Metodo en el que un usuario solicita el prestamo de un libro
     * Si el libro se encuentra disponible se registra un nuevo Prestamo
     */
    public void solicitarPrestamo(Usuario usuario, Libro libro){
        // Verificamos que tanto el libro como el usuario existan en sus respectivos mapas
        if (mapaLibros.get(libro.getIsbn()) != null && mapaUsuarios.get(usuario.getId()) != null) {
            // Recorremos todas las entradas del mapa de prestamos
            for(Map.Entry<Usuario, ArrayList<Prestamo>> entrada : mapaPrestamos.entrySet()) {
                ArrayList<Prestamo> lista = entrada.getValue();
                // Recorremos cada prestamo del usuario actual
                for (Prestamo prestamo : lista) {
                    // Comprobamos si el prestamo corresponde al mismo libro Y no ha sido devuelto
                    if (prestamo.getLibro().equals(libro) && !prestamo.estaDevuelto()) {
                        System.out.println("El libro " + libro.getTitulo() + " ya esta prestado");
                        return; // salimos del metodo porque no se puede prestar
                    }
                }
            }
            // Si llegamos aquí, es porque el libro está disponible
            // Creamos el prestamo y lo registramos
            Prestamo nuevoPrestamo = new Prestamo(usuario, libro, LocalDate.now(), null);
            registrarPrestamo(nuevoPrestamo);
            System.out.println("Préstamo del libro " +libro.getTitulo()+ " al usuario " +usuario.getNombre()+ " registrado con éxito.");
        }
        else {
            System.out.println("No se tienen registros del libro o del usuario");
        }
    }
    /**
     * Metodo para devolver un libro ya prestado
     * El libro se borra de la lista de prestamos
     */
    public void devolucion(Usuario usuario, Libro libro){
        // Verificamos que el usuario tenga prestamos registrados
        if (mapaPrestamos.containsKey(usuario)) {
            ArrayList<Prestamo> lista = mapaPrestamos.get(usuario);
            boolean encontrado = false;
            // Recorremos los prestamos del usuario
            for (Prestamo prestamo : lista) {
                if (prestamo.getLibro().equals(libro) && !prestamo.estaDevuelto()) {
                    prestamo.devolverLibro(); //Aqui actualizamos la fecha de devolucion y asi marcamos el libro como devuelto
                    System.out.println(usuario.getNombre() + " ha devuelto el libro " + libro.getTitulo() + " con exito");
                    encontrado = true;
                    break; // Salimos del bucle
                }
            }
            // Si no se encuentra un préstamo activo del libro
            if (!encontrado) {
            System.out.println("El libro " + libro.getTitulo() + " no está prestado al usuario " + usuario.getNombre());
        }
        } else {
            System.out.println("El usuario " + usuario.getNombre() + " no tiene ningun libro prestado");
        }
    }
}
