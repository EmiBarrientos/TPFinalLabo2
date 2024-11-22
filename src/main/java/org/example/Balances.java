package org.example;

import com.models.Cuenta;
import com.models.funciones.Listas;

import java.util.ArrayList;
import java.util.List;

public class Balances {
    private List<Balance> balances;

    public Balances(List<Balance> balances) {
        this.balances = balances;
    }

    public Balances() {
    balances= new ArrayList<>();
    }

    public List<Balance> getBalances() {
        return balances;
    }

    public void setBalances(List<Balance> balances) {
        this.balances = balances;
    }
    public void add(Balance balance){
        this.balances.add(balance);
    }

    public List<Listas> informeBalance() {
        Listas linea = new Listas();
        List<Listas> informe= new ArrayList<>();
        linea.setCampo1("Fecha");
        linea.setCampo2("Tipo");
        linea.setCampo3("Haber");
        linea.setCampo4("Debe");
        informe.add(linea);
        linea = new Listas();
        for (Balance generic : this.balances) {
            String fecha = String.valueOf(generic.getFecha());
            String tipo = String.valueOf(generic.getTipoCuenta());
            String haber = String.valueOf(generic.getHaber());
            String debe = String.valueOf(generic.getDebe());

            linea.setCampo1(fecha);
            linea.setCampo2(tipo);
            linea.setCampo3(haber);
            linea.setCampo4(debe);

            informe.add(linea);
            linea = new Listas();
        }

        return informe;
    }


}
