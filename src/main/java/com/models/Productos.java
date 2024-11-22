package com.models;

import com.models.funciones.Listas;
import com.models.funciones.Mensajes;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Productos {
    private ArrayList<Producto> productos; // agregue que sea private

    public Productos(ArrayList<Producto> productos) {
        this.productos = productos;
    }


    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public Productos() {
        productos = new ArrayList<>(0);
    }

    public void agregarProductos(Producto p) {
        productos.add(p);
    }

    public Producto buscarProducto(String nombre) {
        for (Producto p : productos) {
            if (p.getNombreProd().equals(nombre)) {
                return p;
            }
        }
        return null;
    }



    public Producto getList(int index) {
        return this.productos.get(index);
    }

    public void eliminarProducto(String nombre) {
        productos.remove(buscarProducto(nombre));
    }

    public void ordenarProductosNombre() {
        Collections.sort(productos);
    }

    public Producto ultimoProductoAgregado() {
        return productos.get(productos.size() - 1); // Devuelve el último producto
    }


    //ordenar productos por fecha
    //Borrar productos por fecha
    //ordenamos por productos y que de stock total

//--------------------------- a partir de aca ver como convinar -------------------

    //buscar si existe producto iguales Sin tener en cuenta el id en productos antes de agregarlo,
// si existe devuelve el index
// si no existe -1
    public int checkearNoExisteProducto(Producto nuevoProducto) {
        int i = 0;
        for (Producto generic : this.productos) {
            if (generic.mismoProducto(nuevoProducto)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    // agregar producto nuevo sin id a lista
    // verifca que no esta
    // le agrega id
    // lo agrega

    public int maxId(){
        int maxId=0;
        for(Producto genetic:  this.productos){
            if(genetic.getIdProd()>maxId){
                maxId=genetic.getIdProd();
            }
        }
        return maxId;
    }

    public void addProducto(Producto producto) {
        if (this.checkearNoExisteProducto(producto) == -1) {
            producto.setIdProd(maxId()+1); // asigna id a producto e incrementa el contador
            productos.add(producto); // agrega el producto al inventario
        }
    }

    public void addAll(List <Producto> listaAgregar){
        this.productos.addAll(listaAgregar);
    }

    // le paso un productoConAtributosCambiado
    // busca el index para esa id
    // y modifica el producto si existe (0 estaba y fue modificado)
    // lo agrega si no existe (-1 no estaba y se agrego a la lista) (esto no deberia pasar nunca por
    // la forma que se crean productos)
// busca producto por id
// devuelve -1 si no esta o index si esta
    public int buscarProductoCriterioID(Producto productoBuscado) {
        int i = 0;
        for (Producto generic : this.productos) {
            if (generic.getIdProd() == productoBuscado.getIdProd()) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public void modificarProductosPorProducto(Producto nuevoProducto) {

        int index = this.buscarProductoCriterioID(nuevoProducto); // busca por id y devuelve index o -1
        this.productos.set(index, nuevoProducto);
    }

    public void actualizarStockPorPedidos(Pedido pedido) {
        for (PedidoLinea generic : pedido.getLineasPedidos()) {
            this.modificarProductosPorProducto(generic.getProducto());
        }
    }


    public int buscarProductoNombre(String nombre) {
        int i = 0;
        for (Producto generic : this.productos) {
            if (generic.getNombreProd().equals(nombre)) {
                if (generic.mostrarProducto() == JOptionPane.YES_OPTION) {
                    return i;
                }
            }
            i++;
        }
        return -1;
    }

    public Producto altaDeProducto (){
        Producto productoNuevo= new Producto();
        String nombre = Mensajes.mensajeReturnString("Ingrese el nombre del Producto");
        if(this.buscarProductoNombre(nombre)==-1){
            Mensajes.mensajeOut("Ya existe el producto");
            return null;
        }
        productoNuevo = Producto.cargarProducto();
        this.addProducto(productoNuevo);
        Mensajes.mensajeOut("Producto cargado");
        return productoNuevo;
    }

    public Producto bajaDeProducto (){
        Producto productoNuevo= new Producto();
        String nombre = Mensajes.mensajeReturnString("Ingrese el nombre del Producto");
        int index = this.buscarProductoNombre(nombre);
        if(index==-1){
            Mensajes.mensajeOut("Ya existe el producto");
            return null;
        }
        productoNuevo = Producto.cargarProducto();
        this.productos.remove(index);
        Mensajes.mensajeOut("Producto Dado de Baja");
        return productoNuevo;
    }

    public int buscarProductoMarca(String marca) {
        int i = 0;
        for (Producto generic : this.productos) {
            if (generic.getNombreProd().equals(marca)) {
                if (generic.mostrarProducto() == JOptionPane.YES_OPTION) {
                    ;
                    return i;
                }
            }
            i++;
        }
        return -1;
    }

    public Producto agregarProductoAInventario() {
        Producto producto;
        producto = Producto.cargarProducto();
        if (producto.mostrarProducto() == JOptionPane.YES_OPTION) {
            Mensajes.mensajeOut("Se agrega el producto nuevo al inventario SIN STOCK");
            this.addProducto(producto);
            return this.ultimoProductoAgregado();
        }
    return null;
    }


    public List<Listas> informeProductos() {
        Listas linea = new Listas();
        List<Listas> informe= new ArrayList<>();
        linea.setCampo1("Id Cuenta");
        linea.setCampo2("Categoria");
        linea.setCampo3("Nombre Proveedor");
        linea.setCampo4("Marca del Producto");
        linea.setCampo5("Nombre del Producto");
        linea.setCampo6("Stock del Producto");
        informe.add(linea);
        linea = new Listas();
        for (Producto generic : this.productos) {
            String idCuenta = String.valueOf(generic.getIdProd());
            String categoria = String.valueOf(generic.getCategoriaProd());
            String nombreProveedor = String.valueOf(generic.getProveedor().getNombre());
            String marca = String.valueOf(generic.getMarcaProd());
            String nombre = String.valueOf(generic.getNombreProd());
            String stock = String.valueOf(generic.getStock());

            linea.setCampo1(idCuenta);
            linea.setCampo2(categoria);
            linea.setCampo3(nombreProveedor);
            linea.setCampo4(marca);
            linea.setCampo5(nombre);
            linea.setCampo6(stock);

            informe.add(linea);
            linea = new Listas();
        }

        return informe;
    }


    public void mostrarProductos(String nombre){
        for(Producto generic : this.productos){
            if(nombre == generic.getNombreProd()){
            System.out.println(generic);}
        }
    }




    public void mostrarProductos(Proveedor proveedor){
        for(Producto generic : this.productos){
            if(proveedor == generic.getProveedor()){
                System.out.println(generic);}
        }
    }

    public void mostrarProductos(){
        for(Producto generic : this.productos){
                System.out.println(generic);
        }
    }




}