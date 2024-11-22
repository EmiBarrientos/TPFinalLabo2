package org.example;

import com.enums.*;
import com.models.*;
import com.models.funciones.Comercializar;
import com.models.funciones.Movimiento;
import com.models.funciones.Movimientos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MockDataGenerator {
    private static Random random = new Random();


    public static List<Cuenta> generateCuentas(List<Persona> personas) {
        ArrayList<Cuenta> cuentas = new ArrayList<>();
        int id = 10; // los primeros 10 reservado para el ROOT
        for (Persona persona : personas) {
            if(persona.getId()!=0 && persona.getId()!=1){
            for (TipoCuenta tipoCuenta : TipoCuenta.values()) {
                Cuenta cuenta = new Cuenta(persona, tipoCuenta);
                cuenta.setId(id+1);
                cuentas.add(cuenta);
                id++;
            }
            }
        }
        return cuentas;
    }

    //*************************************************************************

    public static List<Producto> generarProductos2(int n, List<Persona> personas) {
        List<Producto> productos = new ArrayList<>();
        Random random = new Random();
        List<Proveedor> proveedores = new ArrayList<>();
        for (Persona persona : personas) {
            if (persona instanceof Proveedor) {
                proveedores.add((Proveedor) persona);
            }
        }

        for (int i = 1; i <= n; i++) { // producto id 0 reservado para movimiento interno
            Producto producto = new Producto();
            producto.setIdProd(i);
            producto.setNombreProd("Producto_" + i);
            producto.setMarcaProd("Marca_" + (random.nextInt(5) + 1));
            producto.setCategoriaProd(CatProducto.values()[random.nextInt(CatProducto.values().length)]);
            producto.setStock(random.nextInt(100) + 1);
            //producto.setPrecioDeCompra(random.nextDouble() * 100 + 10);
            producto.setPrecioDeCompra(11200300.4);
            producto.setPrecioDeVenta(producto.getPrecioDeCompra() * 1.2);
            producto.setFechaVen("2024-" + (random.nextInt(12) + 1) + "-" + (random.nextInt(28) + 1));
            producto.setPorcentajeVenta(random.nextInt(100) + 1);
            producto.setProveedor(proveedores.get(random.nextInt(proveedores.size())));

            productos.add(producto);
        }

        return productos;
    }

    public static List<Persona> generarPersonas2(int cantidad) {
        List<Persona> personas = new ArrayList<>();
        Random random = new Random();

        for (int i = 2; i <= cantidad; i++) {
            Persona persona;
            if (random.nextBoolean()) {
                persona = new Cliente();
                persona.setTipoPersona(TipoPersona.CLIENTE);
            } else {
                persona = new Proveedor();
                persona.setTipoPersona(TipoPersona.PROVEEDOR);
            }

            persona.setId(i);
            persona.setNombre("Nombre_" + i);
            persona.setApellido("Apellido_" + i);

            // Configurar domicilio
            Domicilio domicilio = new Domicilio();
            domicilio.setCalle("Calle_" + (random.nextInt(100) + 1));
            domicilio.setAltura(random.nextInt(5000) + 1);
            domicilio.setPiso(random.nextInt(10) + 1);
            domicilio.setDepto((char) (random.nextInt(26) + 'A'));
            persona.setDomicilio(domicilio);

            persona.setDni(String.format("%08d", random.nextInt(99999999)));
            persona.setEmail("persona" + i + "@example.com");
            persona.setActive(random.nextBoolean());

            personas.add(persona);
        }

        return personas;
    }

    public static List<PedidoLinea> generarPedidoLineas(List<Producto> productos, int maxCantidad) {
        List<PedidoLinea> pedidoLineas = new ArrayList<>();
        Random random = new Random();
        if (maxCantidad > productos.size()) {
            maxCantidad = productos.size();
        }
        int numeroRandomDeProducto;
        for (int i = 0; i < maxCantidad; i++) {
            PedidoLinea pedidoLinea = new PedidoLinea();
            numeroRandomDeProducto = random.nextInt(productos.size());
            pedidoLinea.setProducto(productos.get(numeroRandomDeProducto));
            int cantidad = random.nextInt(productos.get(numeroRandomDeProducto).getStock()) + 1;
            pedidoLinea.setCantidad(cantidad);
            pedidoLinea.setMontoIndividualCompra(productos.get(numeroRandomDeProducto).getPrecioDeCompra());
            pedidoLinea.setMontoIndividualVenta(productos.get(numeroRandomDeProducto).getPrecioDeVenta());
            pedidoLineas.add(pedidoLinea);
        }
        return pedidoLineas;
    }

    public static List<Pedido> generarPedidos(int numeroMaxCuentas, List<Producto> productos, int maxCantidadLineas, int maxCantPedidos) {
        List<Pedido> pedidos = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < maxCantPedidos; i++) {
            Pedido pedido = new Pedido();
            pedido.setId(i + 1); // ID incremental
            pedido.setIdCuenta(random.nextInt(numeroMaxCuentas)); // ID de cuenta aleatorio (0 a numeroMaxCuentas - 1)
            pedido.setEjecutado(false); // Siempre falso

            // Generar tipo de pedido aleatorio
            TipoDeMovimiento tipoDeMovimiento = TipoDeMovimiento.values()[random.nextInt(TipoDeMovimiento.values().length)];
            pedido.setTipoDePedido(tipoDeMovimiento);
            if(tipoDeMovimiento==TipoDeMovimiento.INTERNO){pedido.setTipoDePedido(TipoDeMovimiento.ALTA);}

            // Generar líneas de pedido usando el mock anterior
            List<PedidoLinea> lineasPedido = generarPedidoLineas(productos, maxCantidadLineas);
            pedido.setLineasPedidos(lineasPedido);

            // Calcular monto total
            pedido.calcularMontoTotal();

            pedidos.add(pedido);
        }

        return pedidos;
    }

    // ***********************************************************************


    public static Movimientos generateMovimientos(int numeroDeMovimientos, Productos inventario,
                                                  Cuentas cuentas,
                                                  PedidosList pedidosList) {

        Movimientos movimientos = new Movimientos();
        for (int i = 0; i < numeroDeMovimientos; i++) {
            Pedido pedido = pedidosList.getPedido(random.nextInt(pedidosList.getPedidosList().size()));
            if(pedido.getIdCuenta()>10){// cuentas usuario root
            Cuenta cuentaAModificar = cuentas.buscarCuentaPorId(pedido.getIdCuenta());
            Movimiento movimiento = new Movimiento(pedido.getTipoDePedido(), cuentaAModificar,
                    pedido, "mock", LocalDate.now());
            movimientos.add(movimiento); // lo cargo al listado de movimientos
            cuentaAModificar = movimiento.getCuenta(); // trae la cuenta nueva con el nuevo saldo
            cuentas.modificarCuentaPorCuenta(cuentaAModificar);// setea el nuevo saldo en el arreglo de cuentas
            //inventario.actualizarStockPorPedidos(movimiento.getProductosComercializados());
            pedidosList.cambiarEstadoPedido(pedido);}
        }

        return movimientos;
    }
}


