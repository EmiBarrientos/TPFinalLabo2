package com.models;
import com.enums.CatProducto;
import java.util.Objects;
import java.util.Scanner;

import com.models.funciones.Mensajes;
import com.validaciones.Validaciones;

import javax.swing.*;

// la estoy haciendo concreta
//public abstract class Producto implements Comparable<Producto> {
public class Producto implements Comparable<com.models.Producto> {

    private int idProd;
    private String nombreProd;
    private String marcaProd;
    private CatProducto categoriaProd;
    private int Stock;
    private double precioDeCompra;
    private double precioDeVenta;
    private String FechaVen; // esto deberia ser un PAR List<FechaVenc,Activo> cada vez que se compra se
                             // debe verificar si no esta se agrega y se carga activo, si esta se activa
                             // y cuando se agota un lote una metodo para desactivar  esa fecha de vencimiento
    private int porcentajeVenta;
    private Proveedor proveedor; // OJO QUE ESTE LO AGREGUE AHORA


    public Producto(String nombreProd, String marcaProd, CatProducto categoriaProd, int stock,
                    double precioDeCompra, int porcentajeVenta, Proveedor proveedor) {
        this.nombreProd = nombreProd;
        this.marcaProd = marcaProd;
        this.categoriaProd = categoriaProd;
        this.Stock = stock;
        this.precioDeCompra = precioDeCompra;
        this.porcentajeVenta = porcentajeVenta;
        this.precioDeVenta = precioDeCompra * (1 + porcentajeVenta / 100.0);
        this.proveedor = proveedor;
    }



    public Producto() {
    }



    public void setIdProd(int idProd) {
        this.idProd = idProd;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public int getIdProd() {
        return idProd;
    }


    public String getNombreProd() {
        return nombreProd;
    }

    public Producto setNombreProd(String nombreProd) {
        this.nombreProd = nombreProd;
        return this;
    }

    public String getMarcaProd() {
        return marcaProd;
    }

    public Producto setMarcaProd(String marcaProd) {
        this.marcaProd = marcaProd;
        return this;
    }

    public CatProducto getCategoriaProd() {
        return categoriaProd;
    }

    public Producto setCategoriaProd(CatProducto categoriaProd) {
        this.categoriaProd = categoriaProd;
        return this;
    }

    public int getStock() {
        return Stock;
    }

    public Producto setStock(int stock) {
        Stock = stock;
        return this;
    }

    public double getPrecioDeCompra() {
        return precioDeCompra;
    }

    public Producto setPrecioDeCompra(double precio) {
        this.precioDeCompra = precio;
        return this;
    }
    public double getPrecioDeVenta() {
        return precioDeVenta;
    }

    public Producto setPrecioDeVenta(double precio) {
        this.precioDeVenta = precio;
        return this;
    }

    public int getPorcentajeVenta() {
        return porcentajeVenta;
    }

    public Producto setPorcentajeVenta(int porcentajeVenta) {
        this.porcentajeVenta = porcentajeVenta;
        return this;
    }

    public String getFechaVen() {
        return FechaVen;
    }

    public Producto setFechaVen(String fechaVen) {
        if(Validaciones.validarFecha(fechaVen)){
            FechaVen = fechaVen;
        }
        return this;
    }




    public void vender(int cant){
        if(this.Stock>cant){
            this.Stock -= cant;
        }else{
            System.out.println("No hay stock suficiente");
        }
    }

    public void updatePrecio(double precioNuevo){ // Actualiza los dos se necesita tener primero el porcentaje
        this.setPrecioDeCompra(precioNuevo);
        this.precioDeVenta = precioDeCompra + (precioDeCompra*this.porcentajeVenta/100);
    }

   public void updatePorcentaje(int nuevoPorcenaje){
        this.setPorcentajeVenta(nuevoPorcenaje);
   }

   //---------------- a partir de aca ver como convinar ---------


   // metodo que pregunta los atributos y crea un producto sin id
    // estp es para cargar despues en produtos pero antes de cargarlo verificar que no exista uno con
    // los mismos atributos asi no se duplica (se necesita un metodo en productos)


    public static Producto cargarProducto(){
        Producto producto= new Producto();
        producto.setMarcaProd(Mensajes.mensajeReturnString("Ingrese la marca del producto:"));
        producto.setNombreProd(Mensajes.mensajeReturnString("Ingrese el nombre del producto:"));
        CatProducto categoria = Mensajes.mensajeReturnEnumConOpciones(CatProducto.class,"Elija una Categoria");
        producto.setCategoriaProd(categoria);

        String precDeCompraStr = Mensajes.mensajeReturnString("Ingrese el precio de compra:");
        double precDeCompra = Double.parseDouble(precDeCompraStr); // Convertir a double
        String porcentajeVentaStr = Mensajes.mensajeReturnString("Ingrese el porcentaje de ganancia:");
        int porcentajeVenta = Integer.parseInt(porcentajeVentaStr); // Convertir a int
        producto.setPorcentajeVenta(porcentajeVenta);
        producto.updatePrecio(precDeCompra); // actualiza el precio de venta

        producto.setFechaVen(Mensajes.mensajeReturnString("Ingrese la fecha de vencimiento (ej. 31/12/2024):"));
        return producto;
    }

    public int mostrarProducto(){
        int respuesta = JOptionPane.showConfirmDialog(null,
                        this.getIdProd() + "\n" +
                        this.getCategoriaProd() + "\n" +
                        this.getMarcaProd() + "\n" +
                        this.getNombreProd() + "\n" +
                        this.getPrecioDeVenta() + "\n" +
                        this.getPrecioDeCompra() + "\n"+
                        this.getStock() + "\n"+
                        this.getFechaVen(),"Es correcto este Producto?", JOptionPane.YES_NO_OPTION);
        return respuesta;
    }


   // metodo para chequear que los atributos de un producto sin tener encuenta el id no sean iguales

    public boolean mismoProducto(Producto otroProducto){
       if( // no poner el atributo id entre los que compara
                  this.getMarcaProd().equals(otroProducto.getMarcaProd())
               && this.getNombreProd().equals(otroProducto.getNombreProd())
               // && this.getFechaVen().equals(otroProducto.getFechaVen()) // pensar lo de la fecha de vencimiento
               && this.getCategoriaProd().equals(otroProducto.getCategoriaProd())
       )
       {return true;}
       return false;
   }



    // para cambiar los atributos de un producto
    public void modificarProducto(Producto nuevoProducto){
        this.Stock = nuevoProducto.Stock;
        this.precioDeCompra = nuevoProducto.precioDeCompra; // y asi con cada atributo
    }

    // metodo para chequear que un Producto no tenga atributos nulos
    public boolean noNull(){
 // OJO: BORRE EN ESTA LINEA son int y double no pueden ser nulos los parametros que se estan viendo
        return false;
    }



 // cosas nuevas



//--------------- hasta aca -----------------



    @Override
    public int compareTo(Producto o) {
        return this.getMarcaProd().compareTo(o.getMarcaProd());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producto producto = (Producto) o;
        return idProd == producto.idProd && Stock == producto.Stock && Double.compare(precioDeCompra, producto.precioDeCompra) == 0 && Double.compare(precioDeVenta, producto.precioDeVenta) == 0 && porcentajeVenta == producto.porcentajeVenta && Objects.equals(nombreProd, producto.nombreProd) && Objects.equals(marcaProd, producto.marcaProd) && categoriaProd == producto.categoriaProd;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombreProd, marcaProd, categoriaProd, Stock, precioDeCompra, precioDeVenta, porcentajeVenta);
    }

    @Override
    public String toString() {
        return "Producto [idProd=" + idProd + ", nombre=" + nombreProd + ", marca=" + marcaProd +
                ", categoria=" + categoriaProd + ", stock=" + Stock + ", precioCompra=" + precioDeCompra +
                ", precioVenta=" + precioDeVenta + ", proveedor=" + proveedor + "]";
    }

}
