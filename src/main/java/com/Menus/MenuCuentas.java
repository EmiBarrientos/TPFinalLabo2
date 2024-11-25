package com.Menus;

import com.models.*;
import com.models.funciones.*;
import org.example.ArchivoUtil;
import org.example.Balances;
import javax.swing.JOptionPane;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public class MenuCuentas extends Menus{
    private Cuenta cuentaGenerico = new Cuenta();

    public MenuCuentas(Personas personas, Cuentas cuentas, Productos productos, Movimientos movimientos, PedidosList pedidosList, Balances balances) {
        super(personas, cuentas, productos, movimientos, pedidosList, balances);
    }

    public void menuCuentas(){
        String menuCuenta = "Ingresa una opción: \n\n" +
                "1. Activar Cuenta. \n" +
                "2. Baja de Cuenta. \n" +
                "3. Ver saldo de Cuenta (Clientes/Proveedores). \n" +
                "4. Listar Cuentas (Activas/Pasivas/Todas). \n" +
                "5. Listar Balance (Activas/Pasivas/Todas). \n" +
                "0. Volver al Menú Principal. \n";

        String input = JOptionPane.showInputDialog(null, menuCuenta, "Menú Cuentas.", JOptionPane.QUESTION_MESSAGE);

        if (input == null){
            JOptionPane.showMessageDialog(null, "Vuelve al Menú Cuentas.");//
        }

        int option;

        try{
            option = Integer.parseInt(input);
        } catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Opción no válida. Intente de nuevo.");
            menuCuentas();
            return;
        }

        switch (option) {
            case 1:
                JOptionPane.showMessageDialog(null, "Activar Cuenta.");
                activarCuenta();
                break;
            case 2:
                JOptionPane.showMessageDialog(null, "Baja de Cuenta.");
                bajaCuenta();
                break;
            case 3:
                JOptionPane.showMessageDialog(null, "Ver saldo de Cuenta (Clientes/Proveedores).");
                verSaldoCuentaPersonas();
                break;
            case 4:
                JOptionPane.showMessageDialog(null, "Listar Cuentas (Activas/Pasivas/Todas).");
                listarCuentas();
                break;
            case 5:
                JOptionPane.showMessageDialog(null, "Listar Balance (Activas/Pasivas/Todas).");
                listarBalance();
                break;
            case 0:
                JOptionPane.showMessageDialog(null, "Volver al Menú Principal.");
                menuGral.menuPrincipal();
                break;
            default:
                JOptionPane.showMessageDialog(null, "Opción inválida. Intente de nuevo.");
                menuCuentas();
                break;
        }
    }

    private void activarCuenta() {
        cuentaGenerico = Comercializar.buscarCuenta(personas, cuentas);
        cuentas.altaCuenta(cuentaGenerico);

        if (Mensajes.mensajeYesNO("¿Quiere dar de Alta otra Cuenta más pedidos?") == JOptionPane.YES_OPTION) {
            activarCuenta();
        }
    }

    private void bajaCuenta() {
        cuentaGenerico = Comercializar.buscarCuenta(personas, cuentas);
        cuentas.bajaCuenta(cuentaGenerico);

        if (Mensajes.mensajeYesNO("¿Quiere dar de Baja otra Cuenta?") == JOptionPane.YES_OPTION) {
            bajaCuenta();
        }
    }

    private Cuenta verSaldoCuentaPersonas() {

        Cuenta respuesta=null;
        cuentaGenerico = Comercializar.buscarCuenta(personas,cuentas);
        if(cuentaGenerico.mostrarCuenta()==JOptionPane.YES_OPTION){
            respuesta = cuentaGenerico;
        }
        if (Mensajes.mensajeYesNO("¿Quiere ver el Saldo de otra Persona?") == JOptionPane.YES_OPTION) {
            return verSaldoCuentaPersonas();
        }
        return respuesta;
    }

    private void listarCuentas() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String fechaHora = LocalDateTime.now().format(formatter);
        String archivoCSV = "InformeCuentas_" + fechaHora + "_" + UUID.randomUUID() + ".CSV";
        ArchivoUtil.crearArchivo(archivoCSV);
        List<Listas> informeCuentas = this.cuentas.informeCuentas();
        ArchivoUtil archivoUtil = new ArchivoUtil<>(archivoCSV, Listas.class);
        archivoUtil.escribirArchivo(informeCuentas,";");
        Mensajes.mensajeOut("Archivo: "+ archivoCSV+" creado.");

    }

    private void listarBalance() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String fechaHora = LocalDateTime.now().format(formatter);
        String archivoCSV = "InformeBalance_" + fechaHora + "_" + UUID.randomUUID() + ".CSV";
        ArchivoUtil.crearArchivo(archivoCSV);
        List<Listas> informe = this.balances.informeBalance();
        ArchivoUtil archivoUtil = new ArchivoUtil<>(archivoCSV, Listas.class);
        archivoUtil.escribirArchivo(informe,";");
        Mensajes.mensajeOut("Archivo: "+ archivoCSV+" creado.");

    }

}