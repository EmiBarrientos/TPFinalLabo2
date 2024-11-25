package com.Menus;

import com.enums.TipoDeMovimiento;
import com.models.*;
import com.models.funciones.*;
import org.example.ArchivoUtil;
import org.example.Balances;
import javax.swing.JOptionPane;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public class MenuPedidos extends Menus{

    private Cuenta cuentaGenerico = new Cuenta();
    private Pedido pedidoGenerico = new Pedido();
    private String DNIgenerico="";
    TipoDeMovimiento tipoDeMovimiento;

    public MenuPedidos(Personas personas, Cuentas cuentas, Productos productos, Movimientos movimientos, PedidosList pedidosList, Balances balances) {
        super(personas, cuentas, productos, movimientos, pedidosList, balances);
        menuGral=new MenuGral(personas, cuentas, productos, movimientos, pedidosList, balances);
    }

    public void menuPedidos(){
        String menuPedido = "Ingrese una opción: \n\n"+
                "1. Crear Pedidos (Pendientes / Ejecutados). \n" +
                "2. Buscar Pedidos Pendientes por Cliente. \n" +
                "3. Eliminar Pedidos Pendientes. \n" +
                "4. Ejecutar Pedidos Pendientes por Cliente. \n" +
                "5. Listar Pedidos (Pendientes / Ejecutados). \n" +
                "6. Anular Pedidos Ejecutados. \n" +
                "7. Buscar Pedidos Ejecutados. \n" +
                "8. Listar Pedidos Ejecutados (Persona / Fecha). \n" +
                "0. Volver al Menú Principal.\n";

        String input = JOptionPane.showInputDialog(null, menuPedido, "Menú Pedidos.", JOptionPane.QUESTION_MESSAGE);

        if (input == null){
            JOptionPane.showMessageDialog(null, "Vuelve al Menú Pedidos."); //
        }

        int option;

        try {
            option = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Opción no válida. Intente de nuevo.");
            menuPedidos();
            return;
        }

        switch (option) {
            case 1:
                JOptionPane.showMessageDialog(null, "Crear Pedidos (Pendientes / Ejecutados).");
                crearPedido();
                break;
            case 2:
                JOptionPane.showMessageDialog(null, "Buscar Pedidos Pendientes por Cliente.");
                buscarPedidos();
                break;
            case 3:
                JOptionPane.showMessageDialog(null, "Eliminar Pedidos Pendientes.");
                eliminarPedidos();
                break;
            case 4:
                JOptionPane.showMessageDialog(null, "Ejecutar Pedidos Pendientes por Cliente.");
                ejecutarPedido();
                break;
            case 5:
                JOptionPane.showMessageDialog(null, "Listar Pedidos (Pendientes / Ejecutados).");
                listarPedidos();
                break;
            case 6:
                JOptionPane.showMessageDialog(null, "Anular Pedidos Ejecutados.");
                anularMovimiento();
                break;
            case 7:
                JOptionPane.showMessageDialog(null, "Buscar Pedidos Ejecutados.");
                buscarUnMovimiento();
                break;
            case 8:
                JOptionPane.showMessageDialog(null, "Listar Pedidos Ejecutados (Persona / Fecha).");
                listarMovimientos();
                break;
            case 0:
                JOptionPane.showMessageDialog(null, "Volver al Menú Principal.");
                menuGral.menuPrincipal();
                break;
            default:
                JOptionPane.showMessageDialog(null, "Opción inválida. Intente de nuevo.");
                menuPedidos();
                break;
        }

    }

    private void crearPedido() {

        cuentaGenerico = Comercializar.buscarCuenta(personas, cuentas);
        if (cuentaGenerico == null) {
            Mensajes.mensajeOut("No existe cuenta con esos datos.");
        }
        if (cuentaGenerico != null) {
            tipoDeMovimiento = Mensajes.mensajeReturnEnumConOpciones(TipoDeMovimiento.class, "Tipo de Movimiento");
            pedidoGenerico = Comercializar.crearPedidoConDatosValidos(pedidosList, productos, tipoDeMovimiento, cuentaGenerico);

            Comercializar.aplicarMovimiento(productos, cuentas, movimientos, pedidoGenerico, pedidosList,balances, personas);
            cuentaGenerico = new Cuenta();
            DNIgenerico = "";
            pedidoGenerico = new Pedido();
        }
        if (Mensajes.mensajeYesNO("¿Quiere crear más Pedidos/Movimientos?") == JOptionPane.YES_OPTION) {
            crearPedido();

        }
    }

    private int buscarPedidos() {
        cuentaGenerico = Comercializar.buscarCuenta(personas, cuentas);
        boolean estado = (Mensajes.mensajeYesNO("¿Buscar los que están Pendientes?")==JOptionPane.YES_OPTION);
        int index = pedidosList.buscarPedido(cuentaGenerico.getId(),estado);
        if (Mensajes.mensajeYesNO("¿Quiere buscar más Pedidos?") == JOptionPane.YES_OPTION) {
            index =-1;
            buscarPedidos();
        }
        return index;
    }

    private void eliminarPedidos() {
        String[] opcion ={"Buscar y Eliminar un Pedido. \n", "Eliminar todos los Pedidos Pendientes."};
        int eleccion = Mensajes.mensajeReturnIntConOpciones(opcion,"Elija una opción:");
        if (eleccion == 0 ){
            cuentaGenerico = Comercializar.buscarCuenta(personas, cuentas);
            pedidosList.eliminarPedidoPendiente(cuentaGenerico.getId());
        }
        if (eleccion==1){
            if(Mensajes.mensajeYesNO("¿Confirma la eliminación de todos los Pedidos Pendientes?")==
                    JOptionPane.YES_OPTION){pedidosList.eliminarTodosLosPendientes();}
        }
        if (Mensajes.mensajeYesNO("¿Quiere Modificar más Pedidos?") == JOptionPane.YES_OPTION) {
            eliminarPedidos();
        }
    }

    private void ejecutarPedido() {
        cuentaGenerico = Comercializar.buscarCuenta(personas,cuentas);
        int index = pedidosList.buscarPedido(cuentaGenerico.getId(),false);
        pedidoGenerico = pedidosList.getPedido(index) ;
        Comercializar.aplicarMovimiento(productos,cuentas,movimientos,pedidoGenerico,pedidosList,balances,personas);
        if (Mensajes.mensajeYesNO("\"¿Quiere ejecutar más Pedidos?\"") == JOptionPane.YES_OPTION) {
            ejecutarPedido();
        }
    }

    private void listarPedidos(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String fechaHora = LocalDateTime.now().format(formatter);
        String archivoCSV = "InformePedidos_" + fechaHora + "_" + UUID.randomUUID() + ".CSV";
        ArchivoUtil.crearArchivo(archivoCSV);
        List<Listas> informePendienteEjecutado = this.pedidosList.informePendienteEjecutado(false);
        ArchivoUtil archivoUtil = new ArchivoUtil<>(archivoCSV, Listas.class);
        archivoUtil.escribirArchivo(informePendienteEjecutado,";");
        Mensajes.mensajeOut("Archivo: "+ archivoCSV+" creado.");
    }

    private void anularMovimiento() {
        Comercializar.anularMovimientoMenus(movimientos, cuentas, productos, personas,balances);
        if (Mensajes.mensajeYesNO("¿Quiere anular otro Pedido Ejecutado?") == JOptionPane.YES_OPTION) {
            anularMovimiento();
        }
    }

    private void buscarUnMovimiento() {
        Comercializar.buscarMovimiento(movimientos,cuentas, productos,personas);
        if (Mensajes.mensajeYesNO("¿Quiere buscar otro Pedido Ejecutado?") == JOptionPane.YES_OPTION) {
            buscarUnMovimiento();
        }
    }

    private void listarMovimientos(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String fechaHora = LocalDateTime.now().format(formatter);
        String archivoCSV = "InformePedidosEjecutados_" + fechaHora + "_" + UUID.randomUUID() + ".CSV";
        ArchivoUtil.crearArchivo(archivoCSV);
        List<Listas> informe = this.movimientos.informeMovimientos();
        ArchivoUtil archivoUtil = new ArchivoUtil<>(archivoCSV, Listas.class);
        archivoUtil.escribirArchivo(informe,";");
        Mensajes.mensajeOut("Archivo: "+ archivoCSV+" creado.");
    }
}