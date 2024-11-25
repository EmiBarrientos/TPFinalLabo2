package com.Menus;

import com.models.Cuentas;
import com.models.PedidosList;
import com.models.Personas;
import com.models.Productos;
import com.models.funciones.Movimientos;
import org.example.Balances;

import javax.swing.JOptionPane;

public class MenuGral extends Menus{
    private MenuCliente menuCliente;
    private MenuPedidos menuPedidos;
    private MenuCuentas menuCuentas;
    private MenuProductos menuProductos;
    private MenuProveedores menuProveedores;
    private MenuPruebaCollections menuPruebaCollections;

    public MenuGral(Personas personas, Cuentas cuentas, Productos productos, Movimientos movimientos, PedidosList pedidosList, Balances balances) {
        super(personas, cuentas, productos, movimientos, pedidosList, balances);
        menuCliente = new MenuCliente(personas, cuentas, productos, movimientos, pedidosList, balances);
        menuPedidos = new MenuPedidos(personas, cuentas, productos, movimientos, pedidosList, balances);
        menuCuentas = new MenuCuentas(personas, cuentas, productos, movimientos, pedidosList, balances);
        menuProductos = new MenuProductos(personas, cuentas, productos, movimientos, pedidosList, balances);
        menuProveedores = new MenuProveedores(personas, cuentas, productos, movimientos, pedidosList, balances);
        menuPruebaCollections = new MenuPruebaCollections(personas, cuentas, productos, movimientos, pedidosList, balances);
    }

    public void menuPrincipal(){
        String menuOptions = "Ingrese a donde quiere entrar: \n\n" +
                "1. Clientes. \n" + //Chequeado.
                "2. Proveedores. \n" + //Chequeado.
                "3. Pedidos. \n" +
                "4. Cuentas. \n" + //Chequeado.
                "5. Productos. \n" + //Chequeado.
                "6. Prueba de velocidad. \n" +
                "0. Salir del programa. \n";

        String input = JOptionPane.showInputDialog(null, menuOptions, "Menú principal", JOptionPane.QUESTION_MESSAGE);

        if (input == null){
            JOptionPane.showMessageDialog(null, "Sale del programa.");
            return;
        }

        int option;

        try{
            option = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Opción no válida. Intente de nuevo.");
            menuPrincipal();
            return;
        }

        switch (option){
            case 1:
                JOptionPane.showMessageDialog(null, "Clientes:");
                menuCliente.menuClientes();
                break;
            case 2:
                JOptionPane.showMessageDialog(null,"Proveedores:");
                menuProveedores.menuProveedores();
                break;
            case 3:
                JOptionPane.showMessageDialog(null, "Pedidos:");
                menuPedidos.menuPedidos();
                break;
            case 4:
                JOptionPane.showMessageDialog(null, "Cuentas:");
                menuCuentas.menuCuentas();
                break;
            case 5:
                JOptionPane.showMessageDialog(null, "Productos:");
                menuProductos.menuProductos();
                break;
            case 6:
                JOptionPane.showMessageDialog(null, "Prueba de velocidad:");
                menuPruebaCollections.menuPruebaCollections();
                break;
            case 0:
                JOptionPane.showMessageDialog(null, "Sale del programa.");
                break;
            default:
                JOptionPane.showMessageDialog(null, "Opcion no valida. Intente de nuevo.");
                menuPrincipal();
                break;
        }

    }


}