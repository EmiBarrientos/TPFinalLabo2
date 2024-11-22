package com.models.funciones;

import com.enums.TipoCuenta;
import com.models.*;
import com.enums.TipoDeMovimiento;

import javax.swing.*;
import java.lang.module.FindException;
import java.time.LocalDate;


public class Comercializar {


    public static void aplicarMovimiento(Productos inventario, Cuentas cuentas, Movimientos movimientos, Pedido pedido, PedidosList pedidosList) {

        if (JOptionPane.YES_OPTION==Mensajes.mensajeYesNO("¿Quiere ejecutar el pedido?")) {
            Cuenta cuentaAModificar = cuentas.buscarCuentaPorId(pedido.getIdCuenta());
            String descripcion = Mensajes.mensajeReturnString("Ingrese Descripcion del Movimiento");
                Movimiento movimiento = new Movimiento(pedido.getTipoDePedido(),cuentaAModificar,
                         pedido, descripcion, LocalDate.now()); // crea un movimiemnto

                if (movimiento.mostrarMovimiento() == JOptionPane.YES_OPTION) {

                    // Crear un metodo que: IF CUENTA A MODIFICAR ES DOLAR
                    // (PREGUNTAR EL VALOR DEL BLUE)
                    //    Aplique SET al movimiento
                    //    private Double montoTotal; (calcula en nuevo en dolares)
                    //    private Double saldoModificado; (calcula el nuevo usando el movimiento.getSaldoAnterior + montoTotal
                    //    setea la cueta que trae movimiento.getCuenta() con el nuevo valor de saldo
                    //    sigue desde aca
                    movimientos.add(movimiento); // lo cargo al listado de movimientos
                    cuentaAModificar = movimiento.getCuenta(); // trae la cuenta nueva con el nuevo saldo
                    cuentas.modificarCuentaPorCuenta(cuentaAModificar);// setea el nuevo saldo en el arreglo de cuentas

                    inventario.actualizarStockPorPedidos(movimiento.getProductosComercializados());
                    pedidosList.cambiarEstadoPedido(pedido);
                }
        }
    }


    public static void anularMovimiento(Movimiento movimiento, Movimientos movimientos, Cuentas cuentas, Productos inventario){
        movimiento.getProductosComercializados().mostrarPedido();
        movimiento.invertirMovimiento();
        movimiento.getProductosComercializados().mostrarPedido();
        movimientos.add(movimiento); // lo cargo al listado de movimientos
        Cuenta cuentaAModificar = movimiento.getCuenta(); // trae la cuenta nueva con el nuevo saldo
        cuentas.modificarCuentaPorCuenta(cuentaAModificar);// setea el nuevo saldo en el arreglo de cuentas
        inventario.actualizarStockPorPedidos(movimiento.getProductosComercializados());
    }


    public static void anularMovimientoMenus(Movimientos movimientos, Cuentas cuentas, Productos inventario, Personas personas) {
        Movimiento movimiento = Comercializar.buscarMovimiento
                (movimientos, cuentas, inventario, personas);
        if (movimiento != null){
            if (Mensajes.mensajeYesNO( "¿Confirma la eliminación del pedido?") == JOptionPane.YES_OPTION) {
                Comercializar.anularMovimiento(movimiento,movimientos, cuentas, inventario);
                Mensajes.mensajeOut( "Pedido eliminado con éxito.");
            }
        }
        else {
            Mensajes.mensajeOut("Movimiento no encontrado");
        }
    }

    public static Movimiento buscarMovimiento
            (Movimientos movimientos, Cuentas cuentas, Productos inventario, Personas personas) {
        String[] opcion = {"Buscar por ID", "Buscar por Fechas", "Por Fecha y Cliente"};
        int id;
        LocalDate fechaIni;
        LocalDate fechaFin;
        Movimiento movimiento = new Movimiento();
        int eleccion = Mensajes.mensajeReturnIntConOpciones(opcion, "Elija una opcion de Busqueda");
        if (eleccion == 0) {
            id = Mensajes.mensajesReturnINT("ingrese el iD");
            movimiento = movimientos.buscarMovimiento(id);// por id
        }

        if (eleccion == 1) {
            fechaIni = Mensajes.mensajeFecha("Ingresar la Fecha Inicial dd/MM/yyyy");
            fechaFin = Mensajes.mensajeFecha("Ingresar la Fecha Final dd/MM/yyyy");
            movimiento = movimientos.buscarMovimiento(fechaIni, fechaFin);// por fecha
        }

        if (eleccion == 2) {
            fechaIni = Mensajes.mensajeFecha("Ingresar la Fecha Inicial dd/MM/yyyy");
            fechaFin = Mensajes.mensajeFecha("Ingresar la Fecha Final dd/MM/yyyy");

            movimiento = movimientos.buscarMovimiento(personas.buscarPersonaConMensajito(), fechaIni, fechaFin);// por fecha
        }

        if (movimiento != null) {
            movimiento.mostrarMovimiento();
            return movimiento;
        }
        return null;
    }

    public static Pedido crearPedidoConDatosValidos(PedidosList pedidosList, Productos productos, TipoDeMovimiento tipoDeMovimiento, Cuenta cuenta) {
        int cantidad;
        int stock;
        Producto producto = new Producto();
        Pedido pedidoGenerico = new Pedido();
        PedidoLinea lineaPedidoLinea;

        // buscando  o creando producto
        do {
            int bandera = 0;
            int index = productos.buscarProductoNombre(Mensajes.mensajeReturnString("Escribir el Nombre del Producto a Comprar"));

            if (index > -1) {//esta el producto
                producto = productos.getList(index);
                bandera = 1;
            }

            if (index == -1 &&
                    (tipoDeMovimiento == TipoDeMovimiento.COMPRA || tipoDeMovimiento == TipoDeMovimiento.ALTA) &&
                    JOptionPane.YES_OPTION == Mensajes.mensajeYesNO("no hay producto con ese nombre, quiere cargar uno?")) {// no esta hay que crearlo
                producto = productos.agregarProductoAInventario();
                if (producto != null) {// agrego el producto
                    bandera = 1;
                }
            }
            if (index == -1 && bandera != 1) {
                Mensajes.mensajeOut("No existe el producto");
            }

            if (bandera == 1) {
                cantidad = Mensajes.mensajesReturnINT("Cuantos desea comprar?");
                stock = producto.getStock();

                if (tipoDeMovimiento == TipoDeMovimiento.VENTA ||
                        tipoDeMovimiento == TipoDeMovimiento.BAJA) { //si es de baja o venta chequear el stock
                    while (cantidad > stock) {
                        cantidad = Mensajes.mensajesReturnINT("La cantidad debe ser menor a: " + stock);
                    }
                }

                // ahora que tenemos producto y cantidad ya puedo crear la linea
                if (tipoDeMovimiento == TipoDeMovimiento.BAJA || tipoDeMovimiento == TipoDeMovimiento.VENTA) {
                    stock = producto.getStock() - cantidad;
                } else {
                    stock = producto.getStock() + cantidad;
                }
                producto.setStock(stock);
                lineaPedidoLinea = new PedidoLinea(producto, cantidad);

                if (lineaPedidoLinea.mostrarPedido() == JOptionPane.YES_OPTION) { // muestro antes de agregar
                    pedidoGenerico.addLineaDePedido(tipoDeMovimiento, lineaPedidoLinea);
                }
            }


        } while (Mensajes.mensajeYesNO("Desea seguir cargando Lineas de Pedidos?") != JOptionPane.NO_OPTION);
        if(pedidoGenerico!=null){
            pedidoGenerico.setIdCuenta(cuenta.getId());
            pedidoGenerico.setEjecutado(false);
            pedidosList.addPedido(pedidoGenerico);
            pedidoGenerico=pedidosList.ultimoPedidoAgregado();
            pedidoGenerico.mostrarPedido();}

        return pedidoGenerico;
    }

public static Cuenta buscarCuenta(Personas personas, Cuentas cuentas){
        Cuenta cuentaGenerico;
        String DNIgenerico = Mensajes.mensajeReturnString("Ingrese DNI de la Persona");// -- establecer Persona
        int indexGenerico = personas.buscarIndexPorDNI(DNIgenerico);
        if(indexGenerico == -1){
            Mensajes.mensajeOut("no existe persona con ese DNI");
            return null;
        }
        TipoCuenta tipoCuenta = Mensajes.mensajeReturnEnumConOpciones(TipoCuenta.class, "Tipo de Cuenta");
        cuentaGenerico = cuentas.buscarCuentaPorPersonaTipoCuenta (personas.buscarPersonaPorIndex(indexGenerico), tipoCuenta);
        return cuentaGenerico;
}

}


