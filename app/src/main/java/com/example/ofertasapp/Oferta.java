package com.example.ofertasapp;

import androidx.annotation.Nullable;

/**
 * Clase que define una oferta
 */
public class Oferta {

    //atributos privados de la clase oferta
    private int id;
    private String descripcion;
    private String nombre;

    //Creamos dos constructores, uno por defecto y otro al que se le pasen todos
    //los campos

    /**
     * Constructor por defecto
     */
    public Oferta() {
    }

    /**
     * Constructor al que se le pasan todos los atributos de la clase menos el id
     *
     * @param descripcion define la descripción de la oferta
     * @param nombre define el nombre de la oferta
     */
    public Oferta(String descripcion, String nombre) {
        this.descripcion = descripcion;
        this.nombre = nombre;
    }

    /**
     * Obtenemos la descripción de la oferta
     *
     * @return devuelve la descripción de la oferta
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Asignamos la descripción de la oferta
     *
     * @param description
     */
    public void setDescription(String description) {
        this.descripcion = description;
    }

    /**
     * Obtenemos el nombre de la oferta
     *
     * @return devuelve el nombre de la oferta
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asignamos el nombre de la oferta
     *
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtenemos el id de la oferta
     *
     * @return devuelve el id de la oferta
     */
    public int getId() {
        return id;
    }

    /**
     * Asignamos el id de la oferta
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }
}
