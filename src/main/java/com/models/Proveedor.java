package com.models;

import com.enums.TipoPersona;
import com.enums.TipoProveedor;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Proveedor extends Persona {
   // private TipoProveedor tipoProveedor;
    //private Productos productosProv;


    public Proveedor(String nombre, String apellido, String dni, Domicilio domicilio, TipoProveedor tipoProveedor) {
        this.setNombre(nombre);
        this.setApellido(apellido);
        this.setDni(dni);
        this.setDomicilio(domicilio);
        this.setActive(true);
        this.setTipoPersona(TipoPersona.PROVEEDOR);
     //   this.tipoProveedor = tipoProveedor;
    }

    public Proveedor(String nombre, String apellido, String dni, Domicilio domicilio) {
        this.setNombre(nombre);
        this.setApellido(apellido);
        this.setDni(dni);
        this.setDomicilio(domicilio);
        this.setActive(true);
        this.setTipoPersona(TipoPersona.PROVEEDOR);
    }

    public Proveedor(){
        //productosProv = new Productos();
    }

    //public TipoProveedor getTipoProveedor() {
    //    return tipoProveedor;
   // }

    //public void setTipoProveedor(TipoProveedor tipoProveedor) {
    //    this.tipoProveedor = tipoProveedor;
    //}

/*public Productos getProductosProv() {
        return productosProv;
    }

    public Proveedor setProductosProv(Productos productosProv) {
        this.productosProv = productosProv;
        return this;
    }*/

    @Override
    public String toString() {

        return getClass().getSimpleName() + " [id=" + this.getId() + ", nombre=" + this.getNombre() +
                ", apellido=" + this.getApellido()
                + ", dni=" + this.getDni() + ", domicilio=" + this.getDomicilio() + ", tipoPersona=" +
                this.getTipoPersona() + ", email=" + this.getEmail() + ", active=" + this.getActive() + "]";
    }

    @Override
    public Persona crearPersona(){

        Proveedor generic = new Proveedor();
        Domicilio domicilio= new Domicilio();
        generic.setNombre(JOptionPane.showInputDialog("Ingrese el nombre:"));
        generic.setApellido(JOptionPane.showInputDialog("Ingrese el apellido:"));
        generic.setDni(JOptionPane.showInputDialog("Ingrese el DNI:"));
        generic.setEmail(JOptionPane.showInputDialog("Ingrese el email:"));

        // Cargar y establecer el domicilio usando JOptionPane
        domicilio = Domicilio.cargarDomicilio();
        generic.setDomicilio(domicilio);

        // Establecer el estado activo
        generic.setActive(true);
        return generic;
    }


    public Proveedor crearProveedor() {
        Proveedor generic = new Proveedor();
        Domicilio domicilio;
        generic.setNombre(JOptionPane.showInputDialog("Ingrese el nombre:"));
        generic.setApellido(JOptionPane.showInputDialog("Ingrese el apellido:"));
        generic.setDni(JOptionPane.showInputDialog("Ingrese el DNI:"));
        generic.setEmail(JOptionPane.showInputDialog("Ingrese el email:"));

        // Cargar y establecer el domicilio usando JOptionPane
        domicilio = Domicilio.cargarDomicilio();
        generic.setDomicilio(domicilio);

        // Establecer el estado activo
        generic.setActive(true);
        return generic;
    }

    public int mostrarProveedor() {
        int respuesta = JOptionPane.showConfirmDialog(null,
                this.getApellido() + "\n" +
                        this.getNombre() + "\n" +
                        this.getActive() + "\n" +
                        this.getDni() + "\n" +
                        this.getTipoPersona() + "\n"
                , "Es correcta esta persona?", JOptionPane.YES_NO_OPTION);
        return respuesta;
    }

    public static Proveedor modificarProveedor (Proveedor p){

        int modificarMas = JOptionPane.YES_OPTION;
        while(modificarMas ==JOptionPane.YES_OPTION) {
            String[] opciones = {"Nombre", "Apellido", "Email", "Domicilio"};
            String atributo = (String) JOptionPane.showInputDialog(null, "Seleccione el atributo a modificar:",
                    "Modificar Cliente", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
            if (atributo != null) {
                switch (atributo) {
                    case "Nombre" -> {
                        String nuevoNombre = JOptionPane.showInputDialog("Ingrese el nuevo nombre:");
                        p.setNombre(nuevoNombre);
                    }
                    case "Apellido" -> {
                        String nuevoApellido = JOptionPane.showInputDialog("Ingrese el nuevo apellido:");
                        p.setApellido(nuevoApellido);
                    }
                    case "Email" -> {
                        String nuevoEmail = JOptionPane.showInputDialog("Ingrese el nuevo email:");
                        p.setEmail(nuevoEmail);
                    }
                    case "Domicilio" -> {
                        JOptionPane.showMessageDialog(null, "Ingrese el nuevo domicilio");
                        p.setDomicilio(Domicilio.cargarDomicilio());
                    }
                    default -> JOptionPane.showMessageDialog(null, "Opción no válida.");
                }
            }
            modificarMas = JOptionPane.showConfirmDialog(null, "¿Quiere modificar más datos?", "Modificar Cliente", JOptionPane.YES_NO_OPTION);
        }

        return p;
    }

    //************************************ cosas nuevas
    @Override
    public boolean equals(Object obj) {
        // Verificar si es el mismo objeto
        if (this == obj) {
            return true;
        }
        // Verificar si el objeto es nulo o de tipo diferente
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Proveedor person = (Proveedor) obj;
        return Objects.equals(this.getId(), person.getId()) &&
                Objects.equals(this.getNombre(), person.getNombre()) &&
                Objects.equals(this.getApellido(), person.getApellido()) &&
                Objects.equals(this.getEmail(), person.getEmail()) &&
                Objects.equals(this.getDomicilio(), person.getDomicilio());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getNombre(), this.getApellido(), this.getEmail(), this.getDomicilio());
    }

}
