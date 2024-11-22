package org.example;

import com.enums.TipoCuenta;

import java.time.LocalDate;

public class Balance {
    private LocalDate fecha;
    private double debe;
    private double haber;
    private TipoCuenta tipoCuenta;

    public Balance(LocalDate fecha, double debe, double haber, TipoCuenta tipoCuenta) {
        this.fecha = fecha;
        this.debe = debe;
        this.haber = haber;
        this.tipoCuenta = tipoCuenta;
    }

    public Balance() {
    }

    public TipoCuenta getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(TipoCuenta tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getDebe() {
        return debe;
    }

    public void setDebe(double debe) {
        this.debe = debe;
    }

    public double getHaber() {
        return haber;
    }

    public void setHaber(double haber) {
        this.haber = haber;
    }
}
