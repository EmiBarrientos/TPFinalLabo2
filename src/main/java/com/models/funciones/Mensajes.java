package com.models.funciones;

import com.enums.CatProducto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Mensajes {

    public static String mensajeReturnStringConDesplegable(String[] opciones, String titulo) {
        return  (String) JOptionPane.showInputDialog(null, "",
            titulo, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
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
    }
}
