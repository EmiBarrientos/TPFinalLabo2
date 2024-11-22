
package org.example;

import com.Menu.MenuGral;
import com.enums.CatProducto;
import com.enums.TipoCuenta;
import com.enums.TipoDeMovimiento;
import com.enums.TipoProveedor;
import com.models.*;
import com.models.funciones.Comercializar;
import com.models.funciones.Movimiento;
import com.models.funciones.Movimientos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.spec.MGF1ParameterSpec;
import java.sql.Array;
import java.sql.SQLOutput;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.

        System.out.println("Hello world!");


        // MenuGral.menuPrincipal();

        Scanner entrada = new Scanner(System.in);
        // todo esto va en una fucion inicializar
        Personas personas = new Personas();
        Cuentas cuentas = new Cuentas();
        Productos productos = new Productos();
        Comercializar.inicializarListas(personas,cuentas,productos); // corgo el usuario root y el producto movimiento
        // preguntar si se quiere usar el mock...
        List<Persona> personaArrayList = MockDataGenerator.generarPersonas2(1000);
        List<Cuenta> cuentaArrayList = MockDataGenerator.generateCuentas(personaArrayList);
        List<Producto> productoArrayList = MockDataGenerator.generarProductos2(5000,personaArrayList);
        List<Pedido> pedidosArrayList = MockDataGenerator.generarPedidos(cuentaArrayList.size(),productoArrayList,10,5000);
        personas.addAll(personaArrayList);
        cuentas.addAll(cuentaArrayList);

        productos.addAll(productoArrayList);
        PedidosList pedidosList = new PedidosList(new ArrayList<>(pedidosArrayList));
        Movimientos movimientos = MockDataGenerator.generateMovimientos(500,productos,cuentas,pedidosList);
        // todo esto termina

        System.out.println("termino de usar el moock ---------------------------------------");





        System.out.println("Empieza productos copia array a Archivo-------------------------------------------------------------------------------------------------------");
// productos
        String archivoCSV = "productos.csv";


        if (!Files.exists(Paths.get(archivoCSV))) {
            try {
                Files.createFile(Paths.get(archivoCSV));
                System.out.println("Archivo creado: " + archivoCSV);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ArchivoUtil<Producto> archivoUtil = new ArchivoUtil<>(archivoCSV, Producto.class);

        archivoUtil.escribirArchivo(productos.getProductos(), ";");


        List<Producto> productoListas = new ArrayList<>();
                productoListas = archivoUtil.leerArchivo(";");
                productoArrayList= new ArrayList<>(productoListas);
        archivoUtil.escribirArchivo(productoArrayList, ";");


        //cuentas
        archivoCSV = "cuentas.csv";
        if (!Files.exists(Paths.get(archivoCSV))) {
            try {
                Files.createFile(Paths.get(archivoCSV));
                System.out.println("Archivo creado: " + archivoCSV);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ArchivoUtil<Cuenta> archivoUtilCuenta = new ArchivoUtil<>(archivoCSV, Cuenta.class);
        archivoUtilCuenta.escribirArchivo(cuentas.getCuentas(), ";");
        List<Cuenta> cuentasListas = archivoUtilCuenta.leerArchivo(";");
        cuentaArrayList= new ArrayList<>(cuentasListas);
        //archivoUtilCuenta.escribirArchivo(cuentaArrayList, ";");


        // personas
        archivoCSV = "personas.csv";
        if (!Files.exists(Paths.get(archivoCSV))) {
            try {
                Files.createFile(Paths.get(archivoCSV));
                System.out.println("Archivo creado: " + archivoCSV);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ArchivoUtil<Persona> archivoUtilPersona = new ArchivoUtil<>(archivoCSV, Persona.class);
        archivoUtilPersona.escribirArchivo(personas.getPersonas(), ";");
        //System.out.println("-------------------------- fin de escritura de persona------------------------------------");

        //List<Persona> personasListas = new ArrayList<>();
        //personasListas = archivoUtilPersona.leerArchivoPersonas(";");
        //System.out.println("-------------------------- fin de lectura de persona------------------------------------");
        //personaArrayList= new ArrayList<>(personasListas);
        //archivoUtilPersona.escribirArchivo(personaArrayList, ";");

        // fin de prueba de archivos
/*
        Domicilio domicilioModelo = new Domicilio("Jose Ingenieros",1765,0,'a');

        Cliente clienteModelo = new Cliente("Agustin","Malagutti","30196270",domicilioModelo);
        Proveedor proveedorModelo = new Proveedor("Agustin","Malagutti","30196271",domicilioModelo);
        personaArrayList.add(clienteModelo);
        personaArrayList.add(proveedorModelo);

        Cuenta cuentaModeloProvD = new Cuenta(proveedorModelo,TipoCuenta.DOLAR);
        Cuenta cuentaModeloProvP = new Cuenta(proveedorModelo,TipoCuenta.PESOS);
        Cuenta cuentaModeloProvL = new Cuenta(proveedorModelo,TipoCuenta.LOQUEPINTE);
        Cuenta cuentaModeloClieD = new Cuenta(clienteModelo,TipoCuenta.DOLAR);
        Cuenta cuentaModeloClieP = new Cuenta(clienteModelo,TipoCuenta.PESOS);
        Cuenta cuentaModeloClieL = new Cuenta(clienteModelo,TipoCuenta.LOQUEPINTE);

        cuentaArrayList.add(cuentaModeloProvD);
        cuentaArrayList.add(cuentaModeloProvP);
        cuentaArrayList.add(cuentaModeloProvL);
        cuentaArrayList.add(cuentaModeloClieD);
        cuentaArrayList.add(cuentaModeloClieP);
        cuentaArrayList.add(cuentaModeloClieL);

        Producto productoModelo1 = new Producto("P1", "M1", CatProducto.CAT1,300,100.0,10,proveedorModelo);
        Producto productoModelo2 = new Producto("P2", "M2", CatProducto.CAT1, 300, 100.0, 10, proveedorModelo);
        Producto productoModelo3 = new Producto("P3", "M3", CatProducto.CAT1, 300, 100.0, 10, proveedorModelo);
        Producto productoModelo4 = new Producto("P4", "M4", CatProducto.CAT1, 300, 100.0, 10, proveedorModelo);
        Producto productoModelo5 = new Producto("P5", "M5", CatProducto.CAT1, 300, 100.0, 10, proveedorModelo);
        Producto productoModelo6 = new Producto("P6", "M6", CatProducto.CAT1, 300, 100.0, 10, proveedorModelo);
        productoArrayList.add(productoModelo1);
        productoArrayList.add(productoModelo2);
        productoArrayList.add(productoModelo3);
        productoArrayList.add(productoModelo4);
        productoArrayList.add(productoModelo5);
        productoArrayList.add(productoModelo6);


        //System.out.println(personaArrayList.get(1));
        //System.out.println(cuentaArrayList.get(1));
  */
        System.out.println(cuentaArrayList.get(1).getPersona().getDni());
        System.out.println(cuentaArrayList.get(1).getPersona().getTipoPersona());
        System.out.println(cuentaArrayList.get(1).getTipoCuenta());
        System.out.println(productoArrayList.get(1).getNombreProd());


        //System.out.println(productoArrayList.get(0));
        //System.out.println(productoArrayList.get(1));
        //System.out.println(productoArrayList.get(2));


        Menu menu = new Menu(pedidosList,personas,cuentas,productos,movimientos);
        menu.mostrarMenuPrincipal();
        entrada.close();
/*
        Scanner entrada = new Scanner(System.in);

        Cliente generic = new Cliente();
        generic = (Cliente) generic.crearPersona();
        System.out.println(generic);

        Personas personas = new Personas();
        personas.addPersona(generic);
        Cuenta cuenta = new Cuenta(generic, TipoCuenta.PESOS);
        Cuentas cuentas= new Cuentas();
        cuentas.add(cuenta);


        //Producto genericproducto = Producto.cargarProducto(entrada);
        //System.out.println(genericproducto);

        //System.out.printf("Hello and welcome!");


        Productos inventario = new Productos();

        ArrayList<Movimiento> listaMovimientos = new ArrayList<>();
        Movimientos movimientos= new Movimientos(listaMovimientos);
        System.out.println("comienza el movimiento");
        Comercializar.modificarProductoNuevo(entrada, inventario, cuentas, movimientos,TipoDeMovimiento.COMPRA);

        for (Movimiento genericos : movimientos.getMovimientos()){
            System.out.println("saldo anterior: "+genericos.getSaldoAnterior());
            System.out.println("saldo modificado: "+genericos.getSaldoModificado());
        }

        for ( Producto genericos : inventario.getProductos()){
            System.out.println(genericos);
        }

        for (Cuenta genericos : cuentas.getCuentas()){
            System.out.println(genericos);
        }

        for (Persona genericos : personas.getPersonas()){
            System.out.println(genericos);
        }*/

    }
}