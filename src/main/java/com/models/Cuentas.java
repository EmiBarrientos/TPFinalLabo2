package com.models;

import com.enums.TipoCuenta;
import com.models.funciones.Listas;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cuentas {
    private List<Cuenta> cuentas;

    public Cuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }

    public Cuentas() {
        cuentas = new ArrayList<>();
        this.cuentas = cuentas;
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    public void add(Cuenta cuenta){
        this.cuentas.add(cuenta);
    }

    public Cuenta buscarCuentaPorId(int idCuenta) {
        for (Cuenta generic : this.cuentas){
            if (generic.getId()== idCuenta){return generic;}
            }
        return null;
    }

    public Cuenta buscarCuentaPorPersonaTipoCuenta(Persona persona, TipoCuenta tipo) {
        for (Cuenta generic : this.cuentas){
            if (generic.getPersona().getId() == persona.getId() &&
                    generic.getTipoCuenta() == tipo
            ){return generic;}
        }
        return null;
    }

    public int modificarCuentaPorCuenta(Cuenta cuenta){
        int index=0;
        for (Cuenta generic : this.cuentas){
            if (generic.getId()== cuenta.getId()){
                this.cuentas.set(index,cuenta);
                return 0;
            }
            index++;
        }
        return -1;
    }

    public List<Listas> informeCuentas() {
        Listas linea = new Listas();
        List<Listas> informe= new ArrayList<>();
        linea.setCampo1("Id Cuenta");
        linea.setCampo2("Tipo");
        linea.setCampo3("Numero Titular");
        linea.setCampo4("Apellido");
        linea.setCampo4("Estado de Cuenta");
        informe.add(linea);
        linea = new Listas();
        for (Cuenta generic : this.cuentas) {
            String idCuenta = String.valueOf(generic.getId());
            String tipo = String.valueOf(generic.getTipoCuenta());
            String dni = String.valueOf(generic.getPersona().getDni());
            String apellido = String.valueOf(generic.getPersona().getApellido());
            String estadoss = String.valueOf(generic.getActiva());

            linea.setCampo1(idCuenta);
            linea.setCampo2(tipo);
            linea.setCampo3(dni);
            linea.setCampo4(apellido);
            linea.setCampo5(estadoss);

            informe.add(linea);
            linea = new Listas();
            }

        return informe;
    }

    public void cargarCuentasNuevaPersona(Persona p){
        ArrayList <Cuenta> cuentasNuevas = Cuenta.cargarCuentasNuevaPersona(p);
        for ( Cuenta generic : cuentasNuevas ){
            this.add(generic);
        }
    }

}
