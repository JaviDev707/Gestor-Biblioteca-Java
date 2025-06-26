package com.mibiblioteca.Modelo;

public class Libro {

    private int isbn;
    private String titulo;
    private String autor;
    private int año;
    private String genero;
    private int stock_actual;
    private int stock_total;

    public Libro() {}

    public Libro(int isbn, String titulo, int año, String autor, String genero, int stock_actual, int stock_total) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.año = año;
        this.genero = genero;
        this.stock_actual = stock_actual;
        this.stock_total = stock_total;
    }

    public int getIsbn() {
        return this.isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAño() {
        return this.año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public String getGenero() {
        return this.genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getAutor() {
        return this.autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getstock_actual() {
        return this.stock_actual;
    }

    public void setstock_actual(int stock_actual) {
        this.stock_actual = stock_actual;
    }

    public int getstock_total() {
        return this.stock_total;
    }

    public void setstock_total(int stock_total) {
        this.stock_total = stock_total;
    }
}
