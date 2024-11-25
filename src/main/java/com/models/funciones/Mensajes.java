package com.models.funciones;

import com.enums.CatProducto;

import javax.lang.model.element.NestingKind;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class Mensajes {

    public static String mensajeReturnStringConDesplegable(String[] opciones, String titulo) {
        return (String) JOptionPane.showInputDialog(null, "",
                titulo, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
    }

    public static double mensajeReturnDouble(String mensaje) {
        double numero = 0.0;
        boolean entradaValida = false;

        do {
            String input = JOptionPane.showInputDialog(mensaje);

            if (input != null) {
                try {
                    numero = Double.parseDouble(input);
                    entradaValida = true;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese un número válido.",
                            "Entrada inválida", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Operación cancelada. Inténtelo nuevamente.",
                        "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        } while (!entradaValida);

        return numero;
    }

    public static char mensajesReturnChar(String texto) {
        char caracter = '\0';
        boolean entradaValida = false;

        do {
            String input = JOptionPane.showInputDialog(texto);

            if (input != null && input.length() == 1) {
                caracter = input.charAt(0);
                entradaValida = true;
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese un único carácter.",
                        "Entrada inválida", JOptionPane.ERROR_MESSAGE);
            }
        } while (!entradaValida);

        return caracter;
    }

    public static int mensajeReturnIntConOpciones(String[] opciones, String titulo) {
        return JOptionPane.showOptionDialog(null, "Seleccione una opción",
                titulo, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                opciones, opciones[0]);
    }

    public static <E extends Enum<E>> E mensajeReturnEnumConOpciones(Class<E> enumClass, String titulo) {
        E[] arregloCatProducto = enumClass.getEnumConstants();
        String[] categoriasString = new String[arregloCatProducto.length];
        for (int i = 0; i < arregloCatProducto.length; i++) {
            categoriasString[i] = arregloCatProducto[i].name();
        }
        int opcion = Mensajes.mensajeReturnIntConOpciones(categoriasString, titulo);
        return arregloCatProducto[opcion];
    }

    public static String mensajeReturnString(String texto) {
        return JOptionPane.showInputDialog(texto);
    }

    public static void mensajeOut(String texto) {
        JOptionPane.showMessageDialog(null, texto);
    }

    public static int mensajeYesNO(String texto) {
        return JOptionPane.showConfirmDialog(null, texto);
    }

    public static int mensajesReturnINT(String texto) {
        int numero = 0;
        boolean entradaValida = false;

        do {
            try {
                String input = Mensajes.mensajeReturnString(texto);
                numero = Integer.parseInt(input);
                entradaValida = true;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese un número entero válido.",
                        "Entrada inválida", JOptionPane.ERROR_MESSAGE);
            }
        } while (!entradaValida);

        return numero;
    }

    public static int mensajeYesNoTabla(Object[][] datos, String[] columnas, String titulo, String textoEtiqueta, Double montoEtiqueta) {
        DefaultTableModel modelo = new DefaultTableModel(datos, columnas);
        JTable tabla = new JTable(modelo);
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JLabel sumaLabel = new JLabel(textoEtiqueta + montoEtiqueta);
        sumaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        panel.add(sumaLabel, BorderLayout.SOUTH);

        int opcion = JOptionPane.showConfirmDialog(
                null,
                panel,
                titulo,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        return opcion;
    }

    public static LocalDate mensajeFecha(String texto) {
        LocalDate fecha = null;
        boolean entradaValida = false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        do {
            try {
                String input = Mensajes.mensajeReturnString(texto);
                fecha = LocalDate.parse(input, formatter);
                entradaValida = true;
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese una fecha válida en formato dd/MM/yyyy.",
                        "Entrada inválida", JOptionPane.ERROR_MESSAGE);
            }
        } while (!entradaValida);

        return fecha;
    }




    /*public static String mensajeReturnStringConDesplegable(String[] opciones, String titulo) {
        return  (String) JOptionPane.showInputDialog(null, "",
            titulo, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
    }

    public static double mensajeReturnDouble(String mensaje) {
        double numero = 0.0; // Inicializamos con un valor por defecto
        boolean entradaValida = false;

        while (!entradaValida) {
            String input = JOptionPane.showInputDialog(mensaje);

            if (input != null) { // Verifica que el usuario no haya cerrado el cuadro
                try {
                    numero = Double.parseDouble(input); // Intenta convertir a double
                    entradaValida = true; // Si es válido, rompe el bucle
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null,
                            "Por favor, ingrese un número válido.",
                            "Entrada inválida", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        "Entrada cancelada. Se usará el valor predeterminado 0.0.",
                        "Operación cancelada", JOptionPane.WARNING_MESSAGE);
                break;
            }
        }

        return numero;
    }

    public static char mensajesReturnChar(String texto) {
        char caracter = '\0'; // Inicializamos con un carácter vacío
        boolean entradaValida = false;

        while (!entradaValida) {
            String input = JOptionPane.showInputDialog(texto);

            if (input != null && input.length() == 1) { // Verifica si hay solo un carácter
                caracter = input.charAt(0); // Toma el primer carácter
                entradaValida = true;
            } else {
                JOptionPane.showMessageDialog(null, "Por favor, ingrese un único carácter.",
                        "Entrada inválida", JOptionPane.ERROR_MESSAGE);
            }
        }

        return caracter;
    }

    public static int mensajeReturnIntConOpciones(String[] opciones, String titulo){
    return  JOptionPane.showOptionDialog(null, "Seleccione una opción",
            titulo, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
    opciones, opciones[0]);
    }

    public static <E extends Enum<E>> E mensajeReturnEnumConOpciones(Class<E> enumClass, String titulo){

        E[] arregloCatProducto =  enumClass.getEnumConstants();
        String[] categoriasString = new String[arregloCatProducto.length];
        for (int i = 0; i < arregloCatProducto.length; i++) {
            categoriasString[i] = arregloCatProducto[i].name();}
        int opcion = Mensajes.mensajeReturnIntConOpciones(categoriasString,"Seleccione la categoría del producto)");
        return arregloCatProducto[opcion];
    }


    public static String mensajeReturnString(String texto){
       return  JOptionPane.showInputDialog(texto);
    }

    public static void mensajeOut(String texto){
        JOptionPane.showMessageDialog(null, texto);
    }

    public static int mensajeYesNO(String texto){
    return JOptionPane.showConfirmDialog(null, texto);
    }

    public static int mensajesReturnINT(String texto){
        return Integer.parseInt(Mensajes.mensajeReturnString(texto));
    }

    public static int mensajeYesNoTabla(Object[][] datos, String[] columnas,String titulo, String textoEtiqueta, Double montoEtiqueta){
        DefaultTableModel modelo = new DefaultTableModel(datos, columnas); // metodo que sirve para crear tablas (muy bueno :) )
        JTable tabla = new JTable(modelo);
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JLabel sumaLabel = new JLabel(textoEtiqueta + montoEtiqueta); // MENSAJITO ABAJO
        sumaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        JPanel panel = new JPanel(new BorderLayout()); // PANEL A MOSTRAR EN EL MENSAJE
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER); // INGRESA LA TABLA
        panel.add(sumaLabel, BorderLayout.SOUTH); // INGRESA LA ETIQUETA SUBTOTAL
        int opcion = JOptionPane.showConfirmDialog(
                null,
                panel,
                titulo,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        return opcion;
    }

    public static LocalDate mensajeFecha(String texto){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(Mensajes.mensajeReturnString(texto),formatter);
    }*/
}
