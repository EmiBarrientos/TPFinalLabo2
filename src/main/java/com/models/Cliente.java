package com.models;

import com.enums.TipoCliente;
import com.enums.TipoPersona;
import com.enums.TipoProveedor;
import com.models.funciones.Mensajes;
import com.validaciones.Validaciones;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Cliente extends Persona {
    //public Productos productosCliente; no tiene sentido

    public Cliente(String nombre, String apellido, String dni, Domicilio domicilio) {
        this.setNombre(nombre);
        this.setApellido(apellido);
        this.setDni(dni);
        this.setDomicilio(domicilio);
        this.setActive(true);
        this.setTipoPersona(TipoPersona.CLIENTE);
    }

    public Cliente() {
    }


    @Override
    public String toString() {
        return getClass().getSimpleName() + " [id=" + this.getId() + ", nombre=" + this.getNombre() +
                ", apellido=" + this.getApellido()
                + ", dni=" + this.getDni() + ", domicilio=" + this.getDomicilio() + ", tipoPersona=" +
                this.getTipoPersona() + ", email=" + this.getEmail() + ", active=" + this.getActive()+ "]";
    }


    public Cliente crearCliente() {
        Cliente generic = new Cliente();
        Domicilio domicilio;
        generic.setNombre(Mensajes.mensajeReturnString("Ingrese el nombre:"));
        generic.setApellido(Mensajes.mensajeReturnString("Ingrese el apellido:"));
        String DNI;
        do {
            DNI = Mensajes.mensajeReturnString("Ingrese el DNI:");

            if (DNI == null || !Validaciones.validarDNI(DNI)) {
                JOptionPane.showMessageDialog(null,
                        "El DNI ingresado es inválido o se canceló. Por favor, inténtelo nuevamente.",
                        "Entrada inválida",
                        JOptionPane.ERROR_MESSAGE);
            }
        } while (DNI == null || !Validaciones.validarDNI(DNI));
        generic.setDni(DNI);
        generic.setEmail(Mensajes.mensajeReturnString("Ingrese el email:"));

        // Cargar y establecer el domicilio usando JOptionPane
        domicilio = Domicilio.cargarDomicilio();
        generic.setDomicilio(domicilio);

        // Establecer el estado activo
        generic.setActive(true);
        generic.setTipoPersona(TipoPersona.CLIENTE);
        return generic;
    }

    public int mostrarCliente() {
        int respuesta = JOptionPane.showConfirmDialog(null,
                this.getApellido() + "\n" +
                        this.getNombre() + "\n" +
                        this.getActive() + "\n" +
                        this.getDni() + "\n" +
                        this.getTipoPersona() + "\n"
                , "Es correcta esta persona?", JOptionPane.YES_NO_OPTION);
        return respuesta;
    }


    @Override
    public Persona crearPersona() {

        Cliente generic = new Cliente();
        Domicilio domicilio = new Domicilio();
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



    public static Cliente modificarCliente (Cliente c){
    // cliente encontrado (no null)
    Cliente clienteGenerica = c;
    int modificarMas = JOptionPane.YES_OPTION;
                while(modificarMas ==JOptionPane.YES_OPTION) {
        String[] opciones = {"Nombre", "Apellido", "Email", "Domicilio"};
        String atributo = Mensajes.mensajeReturnStringConDesplegable(opciones,"Selecionar el Atributo");
        if (atributo != null) {
            switch (atributo) {
                case "Nombre" -> { // TODO: Solo verificar que sea tipo String
                    String nuevoNombre = Mensajes.mensajeReturnString("Ingrese el nuevo nombre:");
                    clienteGenerica.setNombre(nuevoNombre);
                }
                case "Apellido" -> { // TODO: Solo verificar que sea tipo String
                    String nuevoApellido = Mensajes.mensajeReturnString("Ingrese el nuevo apellido:");
                    clienteGenerica.setApellido(nuevoApellido);
                }
                case "Email" -> { // TODO: Solo verificar que sea tipo String
                    String nuevoEmail = Mensajes.mensajeReturnString("Ingrese el nuevo email:");
                    clienteGenerica.setEmail(nuevoEmail);
                }
                case "Domicilio" -> { // TODO: Solo verificar que los tipos Mensajes den bien
                    Mensajes.mensajeOut( "Ingrese el nuevo domicilio");
                    clienteGenerica.setDomicilio(Domicilio.cargarDomicilio());
                }
                default -> Mensajes.mensajeOut( "Opción no válida.");
            }
        }
        modificarMas = Mensajes.mensajeYesNO( "¿Quiere modificar más datos?");
    }

    return clienteGenerica;
}



}
