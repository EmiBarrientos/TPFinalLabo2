package com.Menus;

import com.models.Cuentas;
import com.models.PedidosList;
import com.models.Personas;
import com.models.Productos;
import com.models.funciones.Movimientos;
import org.example.Balances;

public class Menus {
    public static Personas personas;
    public static Cuentas cuentas;
    public static Productos productos;
    public static Movimientos movimientos;
    public static PedidosList pedidosList;
    public static Balances balances;

    public Menus(Personas personas, Cuentas cuentas, Productos productos, Movimientos movimientos, PedidosList pedidosList, Balances balances) {
        this.personas = personas;
        this.cuentas = cuentas;
        this.productos = productos;
        this.movimientos = movimientos;
        this.pedidosList = pedidosList;
        this.balances = balances;
    }
}
