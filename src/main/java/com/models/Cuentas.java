package com.models;

import com.enums.TipoCuenta;
import com.models.funciones.Listas;
import com.models.funciones.Movimiento;
import org.example.ArchivoUtil;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cuentas {
    private List<Cuenta> cuentas;
    private static final String archivo = "cuentas.csv";

    public Cuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }

    public Cuentas() {
        this.cuentas = new ArrayList<>();
        List<Cuenta> cuentasListas = persistenciaLeer();
        if(cuentasListas != null){
        cuentas= new ArrayList<>(cuentasListas);}
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }

    private List<Cuenta> persistenciaLeer(){
        ArchivoUtil.crearArchivo(archivo);
        ArchivoUtil archivoUtil = new ArchivoUtil<>(archivo,Cuenta.class);
        return archivoUtil.leerArchivo(";");
    }

    private void persistenciaEscribir(){
        ArchivoUtil archivoUtil = new ArchivoUtil<>(archivo,Cuenta.class);
        archivoUtil.escribirArchivo(this.cuentas,";");
    }

    public void persistenciaEscribirMock(){ //que solo se use en el mock
        ArchivoUtil archivoUtil = new ArchivoUtil<>(archivo,Cuenta.class);
        archivoUtil.escribirArchivo(this.cuentas,";");
    }

    private void add(Cuenta cuenta){
        int nuevoId= this.maxId()+1;
        cuenta.setId(nuevoId);
        this.cuentas.add(cuenta);
        this.persistenciaEscribir();
    }


    public void addAll(List <Cuenta> listaAgregar){
    this.cuentas.addAll(listaAgregar);
        this.persistenciaEscribir();
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
            )
            {return generic;}
        }
        return null;
    }


    public int modificarCuentaPorCuentaMock(Cuenta cuenta){
        int index=0;
        for (Cuenta generic : this.cuentas){
            if (generic.getId()== cuenta.getId()){
                this.cuentas.set(index,cuenta);
                return index;
            }
            index++;
        }
        return -1;
    }


    public int modificarCuentaPorCuenta(Cuenta cuenta){
        int index=0;
        for (Cuenta generic : this.cuentas){
            if (generic.getId()== cuenta.getId()){
                this.cuentas.set(index,cuenta);
                this.persistenciaEscribir();
                return index;
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
        linea.setCampo5("Estado de Cuenta");
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

    public void cargarCuentasNuevaPersonaProovedorROOT(Persona p){
        ArrayList <Cuenta> cuentasNuevas = Cuenta.cargarCuentasNuevaPersona(p);
        int i=0;
        for ( Cuenta generic : cuentasNuevas ){
            int a =i*2; // pares proovedor
            generic.setId(a);
            this.cuentas.add(generic);
            i++;
        }
        this.persistenciaEscribir();
    }

    public void cargarCuentasNuevaPersonaClienteROOT(Persona p){
        ArrayList <Cuenta> cuentasNuevas = Cuenta.cargarCuentasNuevaPersona(p);
        int i=0;
        for ( Cuenta generic : cuentasNuevas ){
            int a =i*2+1; // impares cliente
            generic.setId(a);
            this.cuentas.add(generic);
            i++;
        }
        this.persistenciaEscribir();
    }

    //lo utiliza en el menu
    public void cargarCuentasNuevaPersona(Persona p){
        ArrayList <Cuenta> cuentasNuevas = Cuenta.cargarCuentasNuevaPersona(p);
        for ( Cuenta generic : cuentasNuevas ){
            this.add(generic);
        }
        this.persistenciaEscribir();
    }
    public int maxId(){
        int maxId=0;
        for(Cuenta genetic:  this.cuentas){
            if(genetic.getId()>maxId){
                maxId=genetic.getId();
            }
        }
        return maxId;
    }

    public int altaCuenta(Cuenta cuenta){
        int index=0;
        for (Cuenta generic : this.cuentas){
            if (generic.getId()== cuenta.getId()){
                generic.setActiva(true);
                this.cuentas.set(index,generic);
                this.persistenciaEscribir();
                return 0;
            }
            index++;
        }
        return -1;
    }

    public int bajaCuenta(Cuenta cuenta){
        int index=0;
        for (Cuenta generic : this.cuentas){
            if (generic.getId()== cuenta.getId()){
                generic.setActiva(false);
                this.cuentas.set(index,generic);
                this.persistenciaEscribir();
                return 0;
            }
            index++;
        }
        return -1;
    }


}
