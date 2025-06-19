package com.mibiblioteca.Modelo;

import java.util.Date;

public class Prestamo {
    
    private int id;
    private Usuario usuario;
    private Libro libro;
    private Date fecha_prestamo;
    private Date fecha_devolucion;
    @SuppressWarnings("unused")
    private boolean libroDevuelto;

    public Prestamo() {}

    public Prestamo(int id, Usuario usuario, Libro libro, Date fecha_prestamo, Date fecha_devolucion) {
        this.id = id;
        this.usuario = usuario;
        this.libro = libro;
        this.fecha_prestamo = fecha_prestamo;
        this.fecha_devolucion = fecha_devolucion;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getFecha_prestamo() {
        return this.fecha_prestamo;
    }

    public void setFecha_prestamo(Date fecha_prestamo) {
        this.fecha_prestamo = fecha_prestamo;
    }

    public Date getFecha_devolucion() {
        return this.fecha_devolucion;
    }

    public void setFecha_devolucion(Date fecha_devolucion) {
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
        //this.fecha_devolucion = LocalDate.now();
    }
    
}
