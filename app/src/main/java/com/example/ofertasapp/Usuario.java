package com.example.ofertasapp;

/**
 * Clase que define un usuario
 */
public class Usuario {
    //atributos privados de la clase usuario
    int id, Activo;
    String Nombre, Apellidos, Email, Password;

    //Creamos dos constructores, uno por defecto y otro al que se le pasen todos
    //los campos

    /**
     * Constructor por defecto
     */
    public Usuario() {

    }

    /**
     * Constructor al que se le pasan todos los atributos de la clase
     *
     * @param id define el id del usuario
     * @param nombre define el nombre del usuario+
     * @param apellidos define el primer apellido del usuario
     * @param email define el email del usuario
     * @param password define la contraseña del usuario
     */
    public Usuario(int id, String nombre, String apellidos, String email, String password, int activo) {
        this.id = id;
        Nombre = nombre;
        Apellidos = apellidos;
        Email = email;
        Password = password;
        this.Activo = activo;
    }

    /**
     * Obtenemos el id del usuario
     *
     * @return devuelve el id del usuario
     */
    public int getId() {
        return id;
    }

    /**
     * Asignamos el id del usuario
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtenemos el nombre del usuario
     *
     * @return devuelve el nombre del usuario
     */
    public String getNombre() {
        return Nombre;
    }

    /**
     * Asignamos el nombre del usuario
     *
     * @param nombre
     */
    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    /**
     * Obtenemos el primer apellido del usuario
     *
     * @return devuelve el primer apellido del usuario
     */
    public String getApellidos() {
        return Apellidos;
    }

    /**
     * Asignamos el primer apellido del usuario
     *
     * @param apellidos
     */
    public void setApellidos(String apellidos) {
        Apellidos = apellidos;
    }

    /**
     * Obtenemos el email del usuario
     *
     * @return devuelve el email del usuario
     */
    public String getEmail() {
        return Email;
    }

    /**
     * Asignamos el email del usuario
     *
     * @param email
     */
    public void setEmail(String email) {
        Email = email;
    }

    /**
     * Obtenemos la contraseña del usuario
     *
     * @return devuelve la contraseña del usuario
     */
    public String getPassword() {
        return Password;
    }

    /**
     * Asignamos la contraseña del usuario
     *
     * @param password
     */
    public void setPassword(String password) {
        Password = password;
    }

    /**
     * Obtenemos si el usuario está activo
     *
     * @return devuelve si el usuario está activo
     */
    public int getActivo() {
        return Activo;
    }

    /**
     * Asignamos si el usuario está o no activo
     *
     * @param activo
     */
    public void setActivo(int activo) {
        Activo = activo;
    }
}
