package PracticaColecciones_1;

import java.time.LocalDate;

public class Prestamo {
    
    private Usuario usuario;
    private Libro libro;
    private LocalDate fecha_prestamo;
    private LocalDate fecha_devolucion;
    @SuppressWarnings("unused")
    private boolean libroDevuelto;

    public Prestamo() {}

    public Prestamo(Usuario usuario, Libro libro, LocalDate fecha_prestamo, LocalDate fecha_devolucion) {
        this.usuario = usuario;
        this.libro = libro;
        this.fecha_prestamo = fecha_prestamo;
        this.fecha_devolucion = fecha_devolucion;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Libro getLibro() {
        return this.libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public LocalDate getFecha_prestamo() {
        return this.fecha_prestamo;
    }

    public void setFecha_prestamo(LocalDate fecha_prestamo) {
        this.fecha_prestamo = fecha_prestamo;
    }

    public LocalDate getFecha_devolucion() {
        return this.fecha_devolucion;
    }

    public void setFecha_devolucion(LocalDate fecha_devolucion) {
        this.fecha_devolucion = fecha_devolucion;
    }

    public boolean estaDevuelto(){
        if (fecha_devolucion != null) {
            return libroDevuelto = true;
        } else {
            return libroDevuelto = false;
        }
    }

    public void devolverLibro(){
        this.fecha_devolucion = LocalDate.now();
    }
    
}
