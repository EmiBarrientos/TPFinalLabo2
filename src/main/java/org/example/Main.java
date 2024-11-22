
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
        //PedidosList pedidosList = new PedidosList();
        //Movimientos movimientos = new Movimientos();

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


        List<Producto> productoListas  = archivoUtil.leerArchivo(";");
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

        System.out.println(cuentaArrayList.get(12).getPersona().getDni());
        System.out.println(cuentaArrayList.get(12).getPersona().getTipoPersona());
        System.out.println(cuentaArrayList.get(12).getTipoCuenta());
        System.out.println(productoArrayList.get(1).getNombreProd());



        Menu menu = new Menu(pedidosList,personas,cuentas,productos,movimientos);
        menu.mostrarMenuPrincipal();


    }
}