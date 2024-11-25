package org.example;

import com.models.Cuenta;
import com.models.funciones.Listas;

import java.util.ArrayList;
import java.util.List;

public class Balances {
    private List<Balance> balances;
    private static final String archivo = "balances.csv";

    public Balances(List<Balance> balances) {
        this.balances = balances;
    }

    public Balances() {
    balances= new ArrayList<>();
        List<Balance> listas = persistenciaLeer();
        if(listas != null){
            balances= new ArrayList<>(listas);}
    }

    private List<Balance> persistenciaLeer(){
        ArchivoUtil.crearArchivo(archivo);
        ArchivoUtil archivoUtil = new ArchivoUtil<>(archivo,Balance.class);
        return archivoUtil.leerArchivo(";");
    }

    private void persistenciaEscribir(){
        ArchivoUtil archivoUtil = new ArchivoUtil<>(archivo,Balance.class);
        archivoUtil.escribirArchivo(this.balances,";");
    }

    public void persistenciaEscribirMock(){
        ArchivoUtil archivoUtil = new ArchivoUtil<>(archivo,Balance.class);
        archivoUtil.escribirArchivo(this.balances,";");
    }


    public List<Balance> getBalances() {
        return balances;
    }

    public void setBalances(List<Balance> balances) {
        this.balances = balances;
    }

    public void addMock(Balance balance) {
        this.balances.add(balance);
    }

    public void add(Balance balance){
        this.balances.add(balance);
        this.persistenciaEscribir();
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
