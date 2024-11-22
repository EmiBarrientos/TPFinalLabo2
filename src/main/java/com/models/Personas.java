package com.models;

import com.enums.TipoPersona;
import com.models.funciones.Mensajes;

import javax.swing.*;
import java.util.*;

public class Personas {
    private ArrayList<Persona> personas;

    public Personas(ArrayList<Persona> personas) {
        this.personas = personas;
    }

    public Personas() {
        this.personas = new ArrayList<>();
    }

    public void addAll(List<Persona> listaAgregar){
        this.personas.addAll(listaAgregar);
    }
    public ArrayList<Persona> getPersonas() {
        return personas;
    }

    public void addPersonaClienteDuena(Persona p){
        p.setId(1);
        personas.add(p);
    }

    public void addPersonaProveedorDuena(Persona p){
        p.setId(0);
        personas.add(p);
    }

    public void addPersona(Persona p){
        p.setId(maxId()+1);
        personas.add(p);
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

    public void eliminarPorNombre(String nombre){
        for (Persona p: personas) {
            personas.remove(buscarPersona(nombre));
        }
    }

    public void eliminarPorIndex(int index){
        for(Persona p: personas){
            personas.remove(buscarPersona(index));
        }
    }

    public Persona buscarPorDNI(String dni){
        int respuesta=1;
        for(Persona p: personas){
            if(p.getDni().equals(dni)){
                if(p.getTipoPersona() == TipoPersona.CLIENTE){
                    respuesta = ((Cliente) p).mostrarCliente();
                    }
                else { respuesta = ((Proveedor) p).mostrarProveedor();} // muestra y confirma si es la persona

                if (respuesta == 0) { return p;}
            }
        }
        return null;
    }


    public int buscarIndexPorDNI(String dni){
        int contador = 0;
        for(Persona p: personas){
            if(p.getDni().equals(dni)){
                contador++;
                int respuesta = p.mostrarPersona(); // muestra y confirma si es la persona
                if (respuesta == 0) { return contador;}
            }
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
    }

    public Persona buscarPersonaConMensajito(){
        String DNIgenerico = Mensajes.mensajeReturnString("Ingrese el DNI de la persona a buscar:");
        int index = this.buscarIndexPorDNI(DNIgenerico);
        if( index == -1 )
        {
            Mensajes.mensajeOut("No existe esa Persona");
        }
    return this.buscarPersonaPorIndex(index);
    }

}