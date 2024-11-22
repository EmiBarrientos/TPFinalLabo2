package com.models.funciones;

import com.models.Cuenta;
import com.models.Pedido;
import com.enums.TipoDeMovimiento;
import com.models.PedidoLinea;
import com.models.Producto;

import java.time.LocalDate;

public class Movimiento {
    private int id;
    private LocalDate fecha;
    private Cuenta cuenta = new Cuenta();
    private Pedido productosComercializados;
    private Double montoTotal;
    private Double saldoAnterior;
    private Double saldoModificado;
    private String descripcion;




    public Movimiento() {
    }

    public Movimiento(TipoDeMovimiento tipoDeMovimiento, // compra o venta
                      Cuenta cuenta, // la cuenta donde tiene que impacatar
                      Pedido productosComercializados, // el pedido
                      String descripcion,
                      LocalDate fecha) {
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.productosComercializados = productosComercializados;
        this.montoTotal = productosComercializados.getMontoTotal();
        this.saldoAnterior = cuenta.getSaldo();
        this.saldoModificado = cuenta.getSaldo() + this.montoTotal;
        cuenta.setSaldo(this.saldoModificado);
        this.cuenta = cuenta;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public Pedido getProductosComercializados() {
        return productosComercializados;
    }

    public void setProductosComercializados(Pedido productosComercializados) {
        this.productosComercializados = productosComercializados;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Double getSaldoAnterior() {
        return saldoAnterior;
    }

    public void setSaldoAnterior(Double saldoAnterior) {
        this.saldoAnterior = saldoAnterior;
    }

    public Double getSaldoModificado() {
        return saldoModificado;
    }

    public void setSaldoModificado(Double saldoModificado) {
        this.saldoModificado = saldoModificado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int mostrarMovimiento(){
        return Mensajes.mensajeYesNO(this.descripcion);
    }

    public Movimiento invertirMovimiento() {
        Pedido pedido = this.getProductosComercializados();
        pedido.invertirPedidos();
        this.setDescripcion("Anulacion, pedido: "+pedido.getId());
        this.setProductosComercializados(pedido);
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        Movimiento movObje = (Movimiento) obj;
        if (movObje.getId()==this.getId()) { return true;}
        return false;
    }


    @Override
    public String toString() {
        return getClass().getSimpleName() + " [id=" + id + ", fecha=" + fecha + ", cuenta=" + cuenta
                + ", productosComercializados=" + productosComercializados
                + ", montoTotal=" + montoTotal + ", saldoAnterior=" + saldoAnterior
                + ", saldoModificado=" + saldoModificado + ", descripcion=" + descripcion + "]";
    }

}



