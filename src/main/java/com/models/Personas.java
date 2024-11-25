package com.models;

import com.enums.TipoPersona;
import com.models.funciones.Mensajes;
import org.example.ArchivoUtil;

import javax.swing.*;
import java.util.*;

public class Personas {
    private ArrayList<Persona> personas;
    private static final String archivo ="personas.csv";

    public Personas(ArrayList<Persona> personas) {
        this.personas = personas;
    }

    public Personas() {
        this.personas = new ArrayList<>();
        List<Persona> listas = persistenciaLeer();
        if(listas != null){
            personas= new ArrayList<>(listas);}

    }

    private List<Persona> persistenciaLeer(){
        ArchivoUtil.crearArchivo(archivo);
        ArchivoUtil archivoUtil = new ArchivoUtil<>(archivo,Persona.class);
        return archivoUtil.leerArchivoPersonas(";");
    }

    private void persistenciaEscribir(){
        ArchivoUtil archivoUtil = new ArchivoUtil<>(archivo,Persona.class);
        archivoUtil.escribirArchivo(this.personas,";");
    }

    public void addAll(List<Persona> listaAgregar){
        this.personas.addAll(listaAgregar);
        this.persistenciaEscribir();
    }
    public ArrayList<Persona> getPersonas() {
        return personas;
    }

    public void addPersonaClienteDuena(Persona p){
        p.setId(1);
        personas.add(p);
        this.persistenciaEscribir();
    }

    public void addPersonaProveedorDuena(Persona p){
        p.setId(0);
        personas.add(p);
        this.persistenciaEscribir();
    }

    public void addPersona(Persona p){
        p.setId(maxId()+1);
        personas.add(p);
        this.persistenciaEscribir();
    }
    public int maxId(){
        int maxId=0;
        for(Persona genetic:  this.personas){
            if(genetic.getId()>maxId){
                maxId=genetic.getId();
            }
        }
        return maxId;
    }

    public void mostrarPersonas() {
        for (Persona p : personas) {
            System.out.println(p);
        }
    }

    public Persona buscarPersonaPorIndex(int index){
        return this.personas.get(index);
    }

    public Persona buscarPersona(String nombre) {
        for (Persona p : personas) {
            if (Objects.equals(p.getNombre(), nombre)) {
                return p;
            }
        }
        return null;
    }

    public Persona buscarPersona(int id){
        for (Persona p: personas){
            if(p.getId() == id){
                return p;
            }
        }
        return null;
    }

    public int buscarIndexConIdPersona(int id){
        int contador =-1;
        for (Persona p: personas){
            contador++;
            if(p.getId() == id){
                return contador;
            }
        }
        return contador;
    }




    public int buscarIndexPorDNI(String dni){
        int contador = 0;
        for(Persona p: personas){
            if(p.getDni().equals(dni)){
                int respuesta = p.mostrarPersona(); // muestra y confirma si es la persona
                if (respuesta == 0)
                { return contador;}
            }
            contador++;
        }
        return -1;
    }

    public void ordenarPorNombre(){
        Collections.sort(personas);
    }



    public void darBajaPersona(int index) {
        if (index != -1) {
                Persona p = this.personas.get(index);
                p.setActive(false);
                this.personas.set(index,p);
            }
            else{
                JOptionPane.showMessageDialog(null, "Error no existe esa Persona");
            }
        this.persistenciaEscribir();
    }


    public void mostrarPersonasActivas(){
        for(Persona p: personas){
            if(p.getActive()){
                System.out.println(p);
            }
        }
    }

    public int personasIndexOf(Persona p){
        return this.personas.indexOf(p);
    }

    public void setPersonas(int index, Persona p){
        this.personas.set(index,p);
        this.persistenciaEscribir();
    }

    public Persona buscarPersonaConMensajito(){
        String DNIgenerico = Mensajes.mensajeReturnString("Ingrese el DNI de la persona a buscar:"); // TODO: Solo verificar que sea tipo String
        int index = this.buscarIndexPorDNI(DNIgenerico);
        if( index == -1 )
        {
            Mensajes.mensajeOut("No existe esa Persona");
            return null;
        }
        else {
            return this.buscarPersonaPorIndex(index);
        }
        }

}