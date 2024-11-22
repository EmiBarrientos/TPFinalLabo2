package com.models.funciones;

import com.models.Cuenta;
import com.models.Persona;

import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Movimientos {
    private List<Movimiento> movimientos;

    public Movimientos() {
        this.movimientos=new ArrayList<>();
    }

    public Movimientos(List<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }

    public List<Movimiento> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }

    public int maxId(){
        int maxId=0;
        for(Movimiento genetic:  this.movimientos){
            if(genetic.getId()>maxId){
                maxId=genetic.getId();
            }
        }
        return maxId;
    }

    public void add(Movimiento movimiento){
        movimiento.setId(maxId()+1);
        this.movimientos.add(movimiento);
    }

    public Movimiento buscarMovimiento (int idMovimiento){
        for (Movimiento generic : this.movimientos){
            if(generic.getId()==idMovimiento){return generic;}
        }
        return null;
    }


    public Movimiento buscarMovimiento (LocalDate fechaInic, LocalDate fechaFin){
        for (Movimiento generic : this.movimientos){
            if ((generic.getFecha().isEqual(fechaInic) || generic.getFecha().isAfter(fechaInic))
                    && (generic.getFecha().isEqual(fechaFin) || generic.getFecha().isBefore(fechaFin))) {
                if(generic.mostrarMovimiento() == JOptionPane.YES_OPTION) {return generic;}
            }
        }
        return null;
    }

    public Movimiento buscarMovimiento (Persona persona, LocalDate fechaInic, LocalDate fechaFin){
        if (persona != null) {
            for (Movimiento generic : this.movimientos) {
                if ((generic.getFecha().isEqual(fechaInic) || generic.getFecha().isAfter(fechaInic))
                        && (generic.getFecha().isEqual(fechaFin) || generic.getFecha().isBefore(fechaFin))
                        && persona.equals(generic.getCuenta().getPersona())) {
                    if (generic.mostrarMovimiento() == JOptionPane.YES_OPTION) {
                        return generic;
                    }
                }
            }
        }
        return null;
    }

    public void invertirMovimiento(Movimiento movimientoOriginal)
    {
        this.add(movimientoOriginal.invertirMovimiento());
    }

    public List<Listas> informeMovimientos() {
        Listas linea = new Listas();
        List<Listas> informe= new ArrayList<>();
        linea.setCampo1("Fecha");
        linea.setCampo2("Id Movimiento");
        linea.setCampo3("Id Cuenta");
        linea.setCampo4("Tipo");
        linea.setCampo5("Tipo Movimiento");
        linea.setCampo6("Numero Titular");
        linea.setCampo7("Saldo Antes Cuenta");
        linea.setCampo8("Saldo Despues Cuenta");
        linea.setCampo9("Monto Total");
        informe.add(linea);
        linea = new Listas();
        for (Movimiento generic : this.movimientos) {
            String fecha = String.valueOf(generic.getFecha());
            String id = String.valueOf(generic.getId());
            String idCuenta = String.valueOf(generic.getCuenta().getId());
            String tipoCuenta = String.valueOf(generic.getCuenta().getTipoCuenta());
            String tipoMovimiento = String.valueOf(generic.getProductosComercializados().getTipoDePedido());
            String dni = String.valueOf(generic.getCuenta().getPersona().getDni());
            String saldoAntes = String.valueOf(generic.getSaldoAnterior());
            String saldoDespues = String.valueOf(generic.getSaldoModificado());
            String MontoTotal = String.valueOf(generic.getMontoTotal());

            linea.setCampo1(fecha);
            linea.setCampo2(id);
            linea.setCampo3(idCuenta);
            linea.setCampo4(tipoCuenta);
            linea.setCampo5(tipoMovimiento);
            linea.setCampo6(dni);
            linea.setCampo7(saldoAntes);
            linea.setCampo8(saldoDespues);
            linea.setCampo9(MontoTotal);
            informe.add(linea);
            linea = new Listas();
        }

        return informe;
    }



}
