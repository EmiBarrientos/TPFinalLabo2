package com.models;
import com.enums.TipoCuenta;
import com.models.funciones.Listas;
import com.models.funciones.Mensajes;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


public class Cuenta {
    private static int idContador=0;
    private int id;
    private Persona persona;
    private int idPersona;
    private Double saldo;
    private TipoCuenta tipoCuenta;
    private Boolean activa;

    public Cuenta(Persona persona, TipoCuenta tipo) {
        this.id = idContador;
        idContador++;
        this.persona = persona;
        this.idPersona = persona.getId();
        this.tipoCuenta = tipo;
        this.saldo=0.0;
        this.activa = true;
    }

    public Cuenta() {
    }

    public int getId() {
        return id;
    }

    public Cuenta setId(int id) {
        this.id = id;
        return this;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public Cuenta setIdPersona(int idPersona) {
        this.idPersona = idPersona;
        return this;
    }

    public Double getSaldo() {
        return saldo;
    }

    public Cuenta setSaldo(Double saldo) {
        this.saldo = saldo;
        return this;
    }

    public TipoCuenta getTipoCuenta() {
        return tipoCuenta;
    }

    public Cuenta setTipoCuenta(TipoCuenta tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
        return this;
    }

    public Boolean getActiva() {
        return activa;
    }

    public Cuenta setActiva(Boolean activa) {
        this.activa = activa;
        return this;
    }

    public Persona getPersona() {
        return persona;
    }

    public Cuenta setPersona(Persona persona) {
        this.persona = persona;
        return this;
    }

    public static ArrayList<Cuenta> cargarCuentasNuevaPersona(Persona p){
        ArrayList<Cuenta> cuentas = new ArrayList<>();
        for (TipoCuenta tipoCuenta : TipoCuenta.values()) {
            cuentas.add(new Cuenta(p, tipoCuenta));
        }
        return cuentas;
    }

    public int mostrarCuenta(){
        int respuesta = Mensajes.mensajesReturnINT(
                this.getActiva()+"\n"+
                        this.getPersona().getDni()+"\n"+
                        this.getPersona().getApellido()+"\n"+
                        this.getTipoCuenta()+"\n"+
                        this.getSaldo()+"\n"
                ,"Es esta cuenta?" ,JOptionPane.YES_NO_OPTION);
        return respuesta;
    }


    @Override
    public String toString() {
        return "Cuenta [id=" + id + ", idPersona=" + idPersona + ", saldo=" + saldo +
                ", tipoCuenta=" + tipoCuenta + ", activa=" + activa + "]";
    }

}
