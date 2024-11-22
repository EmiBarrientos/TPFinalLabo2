package com.models;

import com.enums.TipoDeMovimiento;
import com.models.funciones.Mensajes;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private List<PedidoLinea> lineasPedidoLineas;
    private TipoDeMovimiento tipoDePedido;
    private Double montoTotal;
    private int idCuenta;
    private boolean ejecutado=false; // cambia a verdadero cuando se convierte en movimiento
    private int id;

    public Pedido() {
        this.lineasPedidoLineas = new ArrayList<>();
    }


    public Pedido(List<PedidoLinea> lineasPedidoLineas, TipoDeMovimiento tipoDePedido) {
        this.lineasPedidoLineas = lineasPedidoLineas;
        this.tipoDePedido = tipoDePedido;
       // metodo para calcular montoTotal()
        this.calcularMontoTotal();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void asignarId(int maxId){// se usa solo cuando se agrega a la lista de pedidos
        this.id = maxId++;
    }

    public int getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }

    public boolean isEjecutado() {
        return ejecutado;
    }

    public void setEjecutado(boolean ejecutado) {
        this.ejecutado = ejecutado;
    }

    public List<PedidoLinea> getLineasPedidos() {
        return lineasPedidoLineas;
    }

    public void setLineasPedidos(List<PedidoLinea> lineasPedidoLineas) {
        this.lineasPedidoLineas = lineasPedidoLineas;
    }

    public TipoDeMovimiento getTipoDePedido() {
        return tipoDePedido;
    }

    public void setTipoDePedido(TipoDeMovimiento tipoDePedido) {
        this.tipoDePedido = tipoDePedido;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }



    // metodo para generar una lista de Productos retorna List<LineaPedidoCopia>
    // (hacer aparte necesita Productos inventarios)
    // FALTA HACER QUE CHEQUEE CANTIDAD CONTRA STOCK Y NO PERMITA SI NO HAY STOCK
    // ADEMAS CREE PRODUCTOS CON STOCK ACTUALIZADOS POR LINEA PARA SETEAR EN PRODUCTOS RECURRIENDO EL PEDIDOS
    // HACER UNA FUNCION ACTUALIZAR STOCK

    public void addLineaDePedido(TipoDeMovimiento tipoDePedid, PedidoLinea lineaPedidoLinea){
        this.lineasPedidoLineas.add(lineaPedidoLinea);
        this.tipoDePedido = tipoDePedid;
        this.calcularMontoTotal();
    }



    public int mostrarPedido(){
        String[] columnas = {"ID", "Marca", "Nombre", "Cantidad", "Precio Individual", "Subtotal"};
        Object[][] datos= new Object[this.lineasPedidoLineas.size()][6];

        if(this.tipoDePedido == TipoDeMovimiento.COMPRA || this.tipoDePedido == TipoDeMovimiento.ALTA)
        {for (int i = 0; i<this.lineasPedidoLineas.size(); i++){
            datos[i]=this.lineasPedidoLineas.get(i).mostrarLineaPedidosCompra();}}

        else{
            {for (int i = 0; i<this.lineasPedidoLineas.size(); i++){
                datos[i]=this.lineasPedidoLineas.get(i).mostrarLineaPedidosVenta();}}
        }
        int opcion = Mensajes.mensajeYesNoTabla(datos,columnas,"Pedidos","Total: ",this.montoTotal);
        return opcion;
    }

        // metodo para calcular montoTotal()
    public void calcularMontoTotal() {
        Double montoTota = 0.0;
        for (PedidoLinea generic : this.lineasPedidoLineas) {
            if (this.tipoDePedido == TipoDeMovimiento.COMPRA) {
                montoTota = montoTota + generic.getCantidad()*generic.getMontoIndividualCompra();
                            }
            if (this.tipoDePedido == TipoDeMovimiento.VENTA) {
                montoTota = montoTota + generic.getCantidad()*generic.getMontoIndividualVenta();
            }
        }
        this.montoTotal = montoTota;
    }

    public void setIndexProducto(int index, PedidoLinea pedidoLinea){
        this.lineasPedidoLineas.set(index,pedidoLinea);
    }

    public void invertirPedidos(){
        TipoDeMovimiento tipoDeMovimiento= this.getTipoDePedido();
        int stock;
        int i = 0;
        int cantidad;
        Producto producto;
        if(tipoDeMovimiento == TipoDeMovimiento.BAJA || tipoDeMovimiento == TipoDeMovimiento.VENTA) {
            for (PedidoLinea generic : this.getLineasPedidos()) {
                producto = generic.getProducto();
                cantidad = generic.getCantidad();
                stock = producto.getStock() + cantidad; // recuperamos lo comprado (impacta en las cuentas stock)
                producto.setStock(stock);
                generic.setProducto(producto);
                generic.setCantidad(-cantidad); // creada la antilinea (impacta en las cuentas del saldo)
                this.setIndexProducto(i, generic);// lo cargamos en este
                i++;
            }
        }

        if (tipoDeMovimiento == TipoDeMovimiento.ALTA || tipoDeMovimiento == TipoDeMovimiento.COMPRA) {
            for (PedidoLinea generic : this.getLineasPedidos()) {
                producto = generic.getProducto();
                cantidad = generic.getCantidad();
                stock = producto.getStock() - cantidad; // devolvemos lo comprado
                producto.setStock(stock);
                generic.setProducto(producto);
                generic.setCantidad(-cantidad); // creada la antilinea
                this.setIndexProducto(i, generic);// lo cargamos en este
                i++;

            }
        }
    }
    @Override
    public String toString() {
        return getClass().getSimpleName() + " [id=" + id + ", idCuenta=" + idCuenta + ", tipoDePedido=" + tipoDePedido
                + ", montoTotal=" + montoTotal + ", ejecutado=" + ejecutado +  ", lineasPedidoLineas=" + lineasPedidoLineas + "]";
    }


}





