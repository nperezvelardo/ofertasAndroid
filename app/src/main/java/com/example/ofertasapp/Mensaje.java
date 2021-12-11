package com.example.ofertasapp;

/**
 * Clase que define un mensaje que se envian los usuarios
 */
public class Mensaje {

    //atributos privados de la clase mensaje
    private String contenido;
    private String titulo;

    //Creamos dos constructores, uno por defecto y otro al que se le pasen todos
    //los campos

    /**
     * Constructor por defecto
     */
    public Mensaje() {
    }

    /**
     * Constructor al que se le pasan todos los atributos de la clase
     *
     * @param contenido define el contenido del mensaje
     * @param titulo define el titulo del mensaje
     */
    public Mensaje(String contenido, String titulo) {
        this.contenido = contenido;
        this.titulo = titulo;
    }

    /**
     * Obtenemos el contenido del mensaje
     *
     * @return devuelve el contenido del mensaje
     */
    public String getContenido() {
        return contenido;
    }

    /**
     * Asignamos el contenido del mensaje
     *
     * @param contenido
     */
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    /**
     * Obtenemos el titulo del mensaje
     *
     * @return devuelve el titulo del mensaje
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Asignamos el titulo del mensaje
     *
     * @param titulo
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
