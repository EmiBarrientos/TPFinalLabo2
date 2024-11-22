package com.models;

import com.enums.TipoPersona;
import com.validaciones.Validaciones;

import javax.swing.*;
import java.util.Objects;
import java.util.Scanner;

public abstract class Persona implements Comparable<Persona>{

    private int id;
    private String nombre;
    private String apellido;
    private Domicilio domicilio;
    private String dni;
    private TipoPersona tipoPersona;
    private String email;
    private boolean active;

    public Persona() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getNombre() {
        return nombre;
    }

    public Persona setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public String getApellido() {
        return apellido;
    }

    public Persona setApellido(String apellido) {
        this.apellido = apellido;
        return this;
    }



    public Persona setDni(String dni) {
        //Tarea: realizar una funcion que valide que sea un dni sino dar una excepcion.
        if (Validaciones.validarDNI(dni)) {
            this.dni = dni;
            return this;
        }
        return this;
    }

    public String getDni() {
        return this.dni;
    }

    public String getEmail() {
        return email;
    }

    public Persona setEmail(String email) {
        /*if (Validaciones.validarDNI(email)) {
            this.email = email;
            return this;
        }
        return this;
         */
        this.email=email;
        return this;
    }

    public TipoPersona getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(TipoPersona tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public boolean getActive() {
        return active;
    }

    public Persona setActive(Boolean active) {
        this.active = active;
        return this;
    }

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public Persona setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Persona person = (Persona) o;
        return id == person.id && Objects.equals(nombre, person.nombre) && Objects.equals(apellido, person.apellido) && Objects.equals(email, person.email) && Objects.equals(domicilio, person.domicilio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, apellido, email, domicilio);
    }

    @Override
    public int compareTo(Persona o) {
        return this.getNombre().compareTo(o.getNombre());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido
                + ", dni=" + dni + ", domicilio=" + domicilio + ", tipoPersona=" + tipoPersona +
                ", email=" + email + ", active" + active + "]";
    }


    public Persona crearPersona(){
        return null;
    }

    public int mostrarPersona(){
        int respuesta = JOptionPane.showConfirmDialog(null,
                this.getApellido()+"\n"+
                this.getNombre()+"\n"+
                this.getActive()+"\n"+
                this.getDni()+"\n"+
                this.getTipoPersona()+"\n"
                ,"Es esta persona?" ,JOptionPane.YES_NO_OPTION);
        return respuesta;
    }



}
