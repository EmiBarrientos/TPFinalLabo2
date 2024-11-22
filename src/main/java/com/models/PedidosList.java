package com.models;

import com.enums.TipoDeMovimiento;
import com.models.funciones.Listas;
import com.models.funciones.Mensajes;

import javax.swing.*;
import java.sql.SQLInvalidAuthorizationSpecException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PedidosList {
    ArrayList<Pedido> pedidosList;

    public PedidosList() {
        this.pedidosList=new ArrayList<>();
    }

    public PedidosList(ArrayList<Pedido> pedidosList) {
        this.pedidosList = pedidosList;
    }

    public  void addPedido(Pedido p){
        p.asignarId(this.maxId());
        this.pedidosList.add(p);
    }

    public int maxId(){
        if (pedidosList.isEmpty()==true){return 0;}
        int maximo = pedidosList.get(0).getId(); // Inicializar con el primer elemento
        for (Pedido generic : pedidosList) {
            if (generic.getId() > maximo) {
                maximo = generic.getId(); // Actualizar el máximo
            }
        }
    return maximo;
    }

    public Pedido ultimoPedidoAgregado() {
        return this.pedidosList.get(pedidosList.size()-1); // Devuelve el último pedido
    }

    public void cambiarEstadoPedido(Pedido p){
        int index = this.pedidosList.indexOf(p);
        p.setEjecutado(true);
        this.pedidosList.set(index,p);
    }

    //-------19/11 Agus
    // Metodo para generar una List<Objet> con los pedidos filtrados y que se envie como informe CSV

 ////case 1 -> buscarPedidos(); //escribirlo (abrir por fecha y persona) - agus

    public int buscarPedido(int idCuenta, boolean estado) {
        int index=0;
        for (Pedido generic : this.pedidosList) {
            if (generic.isEjecutado() == estado && generic.getIdCuenta() == idCuenta) {
                if(generic.mostrarPedido() == JOptionPane.YES_OPTION) {
                    return index;
                }
            }
            index++;
        }
            return -1;
    }

    public Pedido eliminarPedidoPendiente(int idCuenta){
        int index = buscarPedido(idCuenta,true);
        if (index>-1) {
            if(JOptionPane.YES_OPTION== Mensajes.mensajeYesNO("Confirma que lo quiere eliminar?")){
                this.pedidosList.remove(index);
            return null;}
            return this.pedidosList.get(index);
        }
        else{
            Mensajes.mensajeOut("No Existen Pendientes para esa Cuenta");
            return null;
        }
    }

    public void eliminarTodosLosPendientes(){
        int index =0;
        for (Pedido generic : this.pedidosList){
            if(generic.isEjecutado()==false){
                this.pedidosList.remove(index);
            }
            index ++;
        }
    }

    public List<Listas> informePendienteEjecutado(boolean estado) {
        Listas linea = new Listas();
        List<Listas> informe= new ArrayList<>();
        linea.setCampo1("Id Cuenta");
        linea.setCampo2("Marca");
        linea.setCampo3("Nombre");
        linea.setCampo4("Cantidad");
        informe.add(linea);
        linea = new Listas();
        for (Pedido generic : this.pedidosList) {
            if (generic.isEjecutado() == estado) {
                String numeroCuenta = String.valueOf(generic.getIdCuenta());
                for (PedidoLinea lineaP : generic.getLineasPedidos()) {
                    String marcaProducto = lineaP.getProducto().getMarcaProd();
                    String nombreProducto = lineaP.getProducto().getNombreProd();
                    String cantidad = String.valueOf(lineaP.getCantidad());
                    linea.setCampo1(numeroCuenta);
                    linea.setCampo2(marcaProducto);
                    linea.setCampo3(nombreProducto);
                    linea.setCampo4(cantidad);
                }
                informe.add(linea);
                linea = new Listas();
            }
        }
        return informe;
    }

    public Pedido getPedido(int index){
        return this.pedidosList.get(index);
    }


}
