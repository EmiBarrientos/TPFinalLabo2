package com.models;

import com.enums.TipoDeMovimiento;
import com.models.funciones.Listas;
import com.models.funciones.Mensajes;
import org.example.ArchivoUtil;

import javax.swing.*;
import java.sql.SQLInvalidAuthorizationSpecException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PedidosList {
    private ArrayList<Pedido> pedidosList;
    private static final String archivo = "pedidos.csv";

    public PedidosList() {
        this.pedidosList=new ArrayList<>();
        List<Pedido> listas = persistenciaLeer();
        if(listas != null){
            pedidosList= new ArrayList<>(listas);}
    }

    private List<Pedido> persistenciaLeer(){
        ArchivoUtil.crearArchivo(archivo);
        ArchivoUtil archivoUtil = new ArchivoUtil<>(archivo,Pedido.class);
        return archivoUtil.leerArchivoPedidos(";");
    }

    private void persistenciaEscribir(){
        ArchivoUtil archivoUtil = new ArchivoUtil<>(archivo,Pedido.class);
        archivoUtil.escribirArchivo(this.pedidosList,";");
    }

    public void persistenciaEscribirMock(){ // public solo para usar con el mock
        ArchivoUtil archivoUtil = new ArchivoUtil<>(archivo,Pedido.class);
        archivoUtil.escribirArchivo(this.pedidosList,";");
    }


    public ArrayList<Pedido> getPedidosList() {
        return pedidosList;
    }

    public void setPedidosList(ArrayList<Pedido> pedidosList) {
        this.pedidosList = pedidosList;
    }

    public PedidosList(ArrayList<Pedido> pedidosList) {
        this.pedidosList = pedidosList;
    }

    public  void addPedido(Pedido p){
        p.asignarId(this.maxId());
        this.pedidosList.add(p);
        this.persistenciaEscribir();
    }

    public void addAll(List <Pedido> listaAgregar){
        this.pedidosList.addAll(listaAgregar);
        this.persistenciaEscribir();
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

    public void cambiarEstadoPedidoMock(Pedido p){
        int index = this.pedidosList.indexOf(p);
        p.setEjecutado(true);
        this.pedidosList.set(index,p);
    }
    public void cambiarEstadoPedido(Pedido p){
        int index = this.pedidosList.indexOf(p);
        p.setEjecutado(true);
        this.pedidosList.set(index,p);
        this.persistenciaEscribir();
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
                this.persistenciaEscribir();
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
        this.persistenciaEscribir();
    }

    public List<Listas> informePendienteEjecutado(boolean estado) {
        Listas linea = new Listas();
        List<Listas> informe= new ArrayList<>();

        linea.setCampo1("Id Pedido");
        linea.setCampo2("Id Cuenta");
        linea.setCampo3("Marca");
        linea.setCampo4("Nombre");
        linea.setCampo5("Cantidad");
        linea.setCampo6("precioCompra");
        linea.setCampo7("precioVenta");
        linea.setCampo8("tipoPedido");
        linea.setCampo9("montoTotal");
        linea.setCampo10("Estado");
        informe.add(linea);
        linea = new Listas();
        for (Pedido generic : this.pedidosList) {
            if (generic.isEjecutado() == estado || generic.isEjecutado() != estado) {// para que me saque todos
                String numeroPedido = String.valueOf(generic.getId());
                String numeroCuenta = String.valueOf(generic.getIdCuenta());
                String tipoPedido = String.valueOf(generic.getTipoDePedido());
                String estados = String.valueOf(generic.isEjecutado());
                String montoTotal = String.valueOf(generic.getMontoTotal());
                for (PedidoLinea lineaP : generic.getLineasPedidos()) {
                    String marcaProducto = lineaP.getProducto().getMarcaProd();
                    String nombreProducto = lineaP.getProducto().getNombreProd();
                    String cantidad = String.valueOf(lineaP.getCantidad());
                    String precioCompra = String.valueOf(lineaP.getMontoIndividualCompra());
                    String precioVenta = String.valueOf(lineaP.getMontoIndividualVenta());

                    linea.setCampo1(numeroPedido);
                    linea.setCampo2(numeroCuenta);
                    linea.setCampo3(marcaProducto);
                    linea.setCampo4(nombreProducto);
                    linea.setCampo5(cantidad);
                    linea.setCampo6(precioCompra);
                    linea.setCampo7(precioVenta);
                    linea.setCampo8(tipoPedido);
                    linea.setCampo9(montoTotal);
                    linea.setCampo10(estados);
                    informe.add(linea);
                }
                linea = new Listas();
            }
        }
        return informe;
    }

    public Pedido getPedido(int index){
        return this.pedidosList.get(index);
    }


}
