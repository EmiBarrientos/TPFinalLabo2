package com.models;

import com.models.funciones.Mensajes;

import javax.swing.*;
import java.util.Objects;
import java.util.Scanner;

public class Domicilio {
    private String calle;
    private int altura;
    private int piso;
    private char depto;

    public Domicilio(String calle, int altura, int piso, char depto) {
        this.calle = calle;
        this.altura = altura;
        this.piso = piso;
        this.depto = depto;
    }

    public Domicilio() {
    }

    public String getCalle() {
        return calle;
    }

    public Domicilio setCalle(String calle) {
        this.calle = calle;
        return this;
    }

    public int getAltura() {
        return altura;
    }

    public Domicilio setAltura(int altura) {
        this.altura = altura;
        return this;
    }

    public int getPiso() {
        return piso;
    }

    public Domicilio setPiso(int piso) {
        this.piso = piso;
        return this;
    }

    public char getDepto() {
        return depto;
    }

    public Domicilio setDepto(char depto) {
        this.depto = depto;
        return this;
    }



    @Override
    public String toString() {
        return "Domicilio [calle=" + calle + ", altura=" + altura + ", piso=" + piso + ", depto=" + depto + "]";
    }


    //--
    public static Domicilio cargarDomicilio(){
        Domicilio domicilio = new Domicilio();
        // Pedir la calle
        domicilio.setCalle(Mensajes.mensajeReturnString("Ingrese la calle:"));// TODO: Solo verificar que sea tipo String


        int altura =  Mensajes.mensajesReturnINT("Ingrese la altura:"); // TODO: Solo verificar que sea tipo INT
        domicilio.setAltura(altura);

        // piso y depto
        int piso = Mensajes.mensajesReturnINT("Ingrese el piso:"); // TODO: Solo verificar que sea tipo INT
        domicilio.setPiso(piso);


        char depto = Mensajes.mensajesReturnChar("Ingrese el departamento:"); // TODO: Solo verificar que sea tipo CHAR
        domicilio.setDepto(depto);

        Mensajes.mensajeOut( "Fin de domicilio");

    return domicilio;
    }

//************************************ cosas nuevas
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Reflexividad
        if (obj == null || getClass() != obj.getClass()) return false; // Verifica tipo
        Domicilio other = (Domicilio) obj; // Conversión segura
        return Objects.equals(calle, other.calle) &&
                altura == other.altura &&
                piso == other.piso &&
                depto == other.depto;
    }
}
