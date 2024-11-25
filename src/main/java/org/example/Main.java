
package org.example;

import com.Menus.MenuGral;
import com.Menus.Menus;
import com.models.*;
import com.models.funciones.*;

import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.

        System.out.println("Hello world!");


        // MenuGral.menuPrincipal();

        // todo esto va en una fucion inicializar
        List<Persona> personaArrayListPrueba = MockDataGenerator.generarPersonas2(15000);
        List<Cuenta> cuentaArrayListPrueba = MockDataGenerator.generateCuentas(personaArrayListPrueba);
        PruebaListas pruebaListas = new PruebaListas();
        pruebaListas.pruebaListas(cuentaArrayListPrueba);
        Mensajes.mensajeOut("Desea seguir?");

        Personas personas = new Personas();
        Cuentas cuentas = new Cuentas();
        Productos productos = new Productos();
        Movimientos movimientos = new Movimientos();
        PedidosList pedidosList = new PedidosList();
        //System.out.println("fin pedido");
        Balances balances = new Balances();



        /*Comercializar.inicializarListas(personas,cuentas,productos); // corgo el usuario root y el producto movimiento

        //preguntar si se quiere usar el mock...

        List<Persona> personaArrayList = MockDataGenerator.generarPersonas2(1000);
        personas.addAll(personaArrayList);
        //personaArrayList.get(0).mostrarPersona();

        List<Cuenta> cuentaArrayList = MockDataGenerator.generateCuentas(personas.getPersonas());
        cuentas.addAll(cuentaArrayList);
        //cuentaArrayList.get(0).mostrarCuenta();

        List<Producto> productoArrayList = MockDataGenerator.generarProductos2(5000,personas.getPersonas());
        productos.addAll(productoArrayList);
        //productoArrayList.get(0).mostrarProducto();


        List<Pedido> pedidosArrayList = MockDataGenerator.generarPedidos(
                cuentas.getCuentas().size(),productos.getProductos(),10,5000);
        pedidosList.addAll(pedidosArrayList);
        //pedidosArrayList.get(1).mostrarPedido();


        Movimientos movimientosMock = MockDataGenerator.generateMovimientos(500,
                productos,cuentas,pedidosList,balances);
        //movimientosMock.getMovimientos().get(5).mostrarMovimiento();
        //System.out.println(movimientosMock.getMovimientos().get(5));
        movimientos.addAll(movimientosMock.getMovimientos());
        //movimientos.getMovimientos().get(5).mostrarMovimiento();





        // todo esto termina
        //PedidosList pedidosList = new PedidosList();
        //Movimientos movimientos = new Movimientos();

        System.out.println("termino de usar el moock ---------------------------------------");
        */

        MenuGral menuGral = new MenuGral(personas, cuentas, productos, movimientos, pedidosList, balances);
        menuGral.menuPrincipal();

        //Menu menu = new Menu(pedidosList,personas,cuentas,productos,movimientos,balances);
        //menu.mostrarMenuPrincipal();


    }
}