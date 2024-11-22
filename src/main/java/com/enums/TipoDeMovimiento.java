package com.enums;

public enum TipoDeMovimiento {
    VENTA(1),
    COMPRA(2),
    BAJA(3),
    ALTA(4),
    INTERNO(5);

    private int numero;

    // Constructor to set the numero for each enum constant
    TipoDeMovimiento(int numero) {
        this.numero = numero;
    }

    public int getNumero() {
        return numero;
    }

}
