package com.models;
import com.enums.TipoCuenta;
import com.models.funciones.Listas;
import com.models.funciones.Mensajes;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Cuenta implements Comparable<Cuenta> {

    private int id;
    private Persona persona;
    private int idPersona;
    private Double saldo;
    private TipoCuenta tipoCuenta;
    private Boolean activa;

    public Cuenta(Persona persona, TipoCuenta tipo) {
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

    // devuele cuenta con todos los tipos de cuentas que hay para una persona
    // esto lo utiliza cuando crea cuentas para una persona y la agrega a la lista de Cuentas
    public static ArrayList<Cuenta> cargarCuentasNuevaPersona(Persona p){
        ArrayList<Cuenta> cuentas = new ArrayList<>();
        for (TipoCuenta tipoCuenta : TipoCuenta.values()) {
            cuentas.add(new Cuenta(p, tipoCuenta));
        }
        return cuentas;
    }

    public int mostrarCuenta(){
        int respuesta = Mensajes.mensajeYesNO(
                this.getActiva()+"\n"+
                        this.getPersona().getDni()+"\n"+
                        this.getPersona().getApellido()+"\n"+
                        this.getTipoCuenta()+"\n"+
                        this.getSaldo()+"\n"
                +"Es esta cuenta?");
        return respuesta;
    }


    @Override
    public String toString() {
        return "Cuenta [id=" + id +", persona="+persona+", idPersona=" + idPersona + ", saldo=" + saldo +
                ", tipoCuenta=" + tipoCuenta + ", activa=" + activa + "]";
    }
/*
 private int id;
    private Persona persona;
    private int idPersona;
    private Double saldo;
    private TipoCuenta tipoCuenta;
    private Boolean activa;
 */
@Override
public int compareTo(Cuenta otraCuenta) { // para la prueba de velocidad
    // Comparar por número de cuenta (puedes cambiarlo según la lógica deseada)
    return Integer.compare(this.getId(), otraCuenta.getId());
}

    public boolean equals(Object obj) {
        // Verifica si es la misma instancia
        if (this == obj) {
            return true;
        }
        // Verifica si el objeto es nulo o de otra clase
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        // Convierte el objeto al tipo Cuenta
        Cuenta cuenta = (Cuenta) obj;

        // Compara los atributos relevantes para igualdad
        return id == cuenta.id &&
                Objects.equals(persona, cuenta.persona) &&
                idPersona == cuenta.idPersona &&
                Objects.equals(saldo, cuenta.saldo) &&
                tipoCuenta == cuenta.tipoCuenta &&
                Objects.equals(activa, cuenta.activa);
    }
}
