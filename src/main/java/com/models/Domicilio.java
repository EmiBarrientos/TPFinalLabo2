package com.models;

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
        domicilio.setCalle(JOptionPane.showInputDialog("Ingrese la calle:"));

        // Pedir la altura, asegurándonos de convertir el String a int
        String alturaStr = JOptionPane.showInputDialog("Ingrese la altura:");
        int altura = Integer.parseInt(alturaStr); // Convertir el String a int
        domicilio.setAltura(altura);

        // piso y depto
        String pisoStr = JOptionPane.showInputDialog("Ingrese el piso:");
        int piso = Integer.parseInt(pisoStr);
        domicilio.setPiso(piso);

        String deptoStr = JOptionPane.showInputDialog("Ingrese el departamento:");
        char depto = deptoStr.charAt(0);
        domicilio.setDepto(depto);

        JOptionPane.showMessageDialog(null, "Fin de domicilio"); // Mostrar mensaje de fin

        /* System.out.println("ingrese el Domicilio");
        System.out.println("ingrese la calle");
        domicilio.setCalle(entrada.nextLine());
        System.out.println("ingrese la Altura");
        entrada.nextInt();
        domicilio.setAltura(entrada.nextInt());
        System.out.println("fin de docimilio");//System.out.println("ingrese el Piso");
        //domicilio.setPiso(entrada.nextInt());
        //System.out.println("ingrese el depto");
        //domicilio.setDepto(entrada.nextLine().charAt(0));*/
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
