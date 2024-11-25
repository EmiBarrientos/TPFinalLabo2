package com.models;

import javax.swing.*;

public class PedidoLinea {
    private Producto producto;
    private int cantidad;
    private Double montoIndividualCompra;
    private Double montoIndividualVenta;

    public PedidoLinea() {
    }

    public PedidoLinea(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.montoIndividualCompra =  producto.getPrecioDeCompra();
        this.montoIndividualVenta =  producto.getPrecioDeVenta();
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Double getMontoIndividualCompra() {
        return montoIndividualCompra;
    }

    public void setMontoIndividualCompra(Double montoIndividualCompra) {
        this.montoIndividualCompra = montoIndividualCompra;
    }

    public Double getMontoIndividualVenta() {
        return montoIndividualVenta;
    }

    public void setMontoIndividualVenta(Double montoIndividualVenta) {
        this.montoIndividualVenta = montoIndividualVenta;
    }


    public int mostrarPedido(){
        int respuesta = JOptionPane.showConfirmDialog(null,
                this.getProducto().getCategoriaProd() + "\n" +
                        this.getProducto().getMarcaProd() + "\n" +
                        this.getProducto().getNombreProd() + "\n" +
                        this.getMontoIndividualCompra() + "\n" +
                        this.getMontoIndividualVenta() + "\n" +
                        this.getCantidad() + "\n"
                        ,"Es correcta esta Linea?", JOptionPane.YES_NO_OPTION);
        return respuesta;
    }

    public Object[] mostrarLineaPedidosCompra(){
        return new Object[] {
                this.producto.getIdProd(),
                this.producto.getMarcaProd(),
                this.producto.getNombreProd(),
                this.getCantidad(),
                this.getMontoIndividualCompra(),
                this.getCantidad() * this.getMontoIndividualCompra() // Calcular el subtotal
        };
    }

    public Object[] mostrarLineaPedidosVenta(){
        return new Object[] {
                this.producto.getIdProd(),
                this.producto.getMarcaProd(),
                this.producto.getNombreProd(),
                this.getCantidad(),
                this.getMontoIndividualVenta(),
                this.getCantidad() * this.getMontoIndividualVenta() // Calcular el subtotal
        };
    }

    public PedidoLinea invertirPedido(){
        this.setCantidad(-this.cantidad);
        return this;
    }
    @Override
    public String toString() {
        return "PedidoLinea [producto=" + producto + ", cantidad=" + cantidad +
                ", montoCompra=" + montoIndividualCompra + ", montoVenta=" + montoIndividualVenta + "]";
    }


}
