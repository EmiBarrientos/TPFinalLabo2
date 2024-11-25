package com.Menus;

import com.models.*;
import com.models.funciones.Listas;
import com.models.funciones.Mensajes;
import com.models.funciones.Movimientos;
import org.example.ArchivoUtil;
import org.example.Balances;

import javax.swing.JOptionPane;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public class MenuProductos extends MenuGral{
    private MenuGral menuGral;

    public MenuProductos(Personas personas, Cuentas cuentas, Productos productos, Movimientos movimientos, PedidosList pedidosList, Balances balances) {
        super(personas, cuentas, productos, movimientos, pedidosList, balances);
    }

    public void menuProductos(){
        String menuProd = "Ingrese una opción: \n\n"+
                "1. Alta. \n" +
                "2. Baja. \n" +
                "3. Modificación. \n" +
                "4. Listado de Productos. \n" +
                "5. Buscar por Producto. \n" +
                "0. Volver al Menú Principal. \n";

        String input = JOptionPane.showInputDialog(null, menuProd, "Menú Proveedores.", JOptionPane.QUESTION_MESSAGE);

        if (input == null){
            JOptionPane.showMessageDialog(null, "Vuelve al Menú Productos.");
        }

        int option;

        try {
            option = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Opción no válida. Intente de nuevo.");
            menuProductos();
            return;
        }

        switch (option){
            case 1:
                JOptionPane.showMessageDialog(null, "Alta de Productos.");
                altaProducto();
                break;
            case 2:
                JOptionPane.showMessageDialog(null,"Baja de Productos.");
                bajaProducto();
                break;
            case 3:
                JOptionPane.showMessageDialog(null,"Modificación de Productos.");
                modificarProducto();
                break;
            case 4:
                JOptionPane.showMessageDialog(null,"Listado de Productos.");
                listadoProductos();
                break;
            case 5:
                JOptionPane.showMessageDialog(null,"Buscar por producto.");
                buscarPorProducto();
                break;
            case 0:
                JOptionPane.showMessageDialog(null,"Vuelve al Menú Principal.");
                menuGral.menuPrincipal();
                break;
            default:
                JOptionPane.showMessageDialog(null,"Opción no válida. Intente de nuevo.");
                menuProductos();
                break;
        }
    }

    private void altaProducto() {
        productos.altaDeProducto();
    }

    private void bajaProducto() {
        productos.bajaDeProducto();
    }

    private void modificarProducto() {
        String producto = JOptionPane.showInputDialog("Ingrese el nombre del Producto a Modificar:");
        String nuevoDato = JOptionPane.showInputDialog("Ingrese el nuevo dato del Producto:");
        JOptionPane.showMessageDialog(null, "Producto Modificado: " + nuevoDato);
    }

    private void listadoProductos() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String fechaHora = LocalDateTime.now().format(formatter);
        String archivoCSV = "InformeProductos_" + fechaHora + "_" + UUID.randomUUID() + ".CSV";
        ArchivoUtil.crearArchivo(archivoCSV);
        List<Listas> informeCuentas = this.productos.informeProductos();
        ArchivoUtil archivoUtil = new ArchivoUtil<>(archivoCSV, Listas.class);
        archivoUtil.escribirArchivo(informeCuentas,";");
        Mensajes.mensajeOut("Archivo: "+ archivoCSV+" creado");
    }

    private void buscarPorProducto() {
        productos.buscarProductoNombre(Mensajes.mensajeReturnString("Escribir el Nombre del Producto a Comprar:"));
    }
}