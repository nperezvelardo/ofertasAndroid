package com.example.ofertasapp;

/**
 * Clase que define las operaciones registradas
 */
public class Log {
    //atributos privados de la clase log
    String usuario, accion;

    //Creamos dos constructores, uno por defecto y otro al que se le pasen todos
    //los campos

    /**
     * Constructor por defecto
     */
    public Log(){

    }

    /**
     * Constructor al que se le pasan todos los atributos de la clase
     *
     * @param usuario nombre del usuario
     * @param accion accion que realiza el usuario
     */
    public Log(String usuario, String accion) {
        this.usuario = usuario;
        this.accion = accion;
    }

    /**
     * Obtenemos el usuario del log
     *
     * @return devuelve el usuario del log
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Asignamos el usuario del log
     *
     * @param usuario
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtenemos la accion del log
     *
     * @return devuelve la accion del log
     */
    public String getAccion() {
        return accion;
    }

    /**
     * Asignamos la accion del log
     *
     * @param accion
     */
    public void setAccion(String accion) {
        this.accion = accion;
    }
}
