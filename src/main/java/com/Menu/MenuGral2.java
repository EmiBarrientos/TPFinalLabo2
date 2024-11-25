package com.Menu;

import javax.swing.JOptionPane;

public class MenuGral2 {
    private MenuCliente2 menuCliente2;
    private MenuPedidos2 menuPedidos2;
    private MenuCuentas2 menuCuentas2;
    private MenuProductos2 menuProductos2;
    private MenuProveedores2 menuProveedores2;

    public static void menuPrincipal(){
        String menuOptions = "Ingrese a donde quiere entrar: \n\n" +
                "1. Clientes. \n" +
                "2. Proveedores. \n" +
                "3. Pedidos. \n" +
                "4. Cuentas/Saldos. \n" +
                "5. Productos. \n" +
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
                JOptionPane.showMessageDialog(null, "Clientes.");
                MenuCliente2.menuClientes();
                break;
            case 2:
                JOptionPane.showMessageDialog(null,"Proveedores.");
                MenuProveedores2.menuProveedores();
                break;
            case 3:
                JOptionPane.showMessageDialog(null, "Pedidos.");
                MenuPedidos2.menuPedidos();
                break;
            case 4:
                JOptionPane.showMessageDialog(null, "Cuentas.");
                MenuCuentas2.menuCuentas();
                break;
            case 5:
                JOptionPane.showMessageDialog(null, "Productos.");
                MenuProductos2.menuProductos();
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