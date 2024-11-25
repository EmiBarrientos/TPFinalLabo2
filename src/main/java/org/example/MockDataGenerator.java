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
            producto.setPrecioDeCompra(Math.round((random.nextDouble() * 10000+100) * 10) / 10.0);
            producto.setPrecioDeVenta(Math.round(producto.getPrecioDeCompra() * 1.25 * 10)/ 10.0);
            producto.setFechaVen((random.nextInt(28) + 1)+"/"+(random.nextInt(12) + 1) + "/2024");
            producto.setPorcentajeVenta(25);
            producto.setProveedor(proveedores.get(random.nextInt(proveedores.size())));

            productos.add(producto);
        }

        return productos;
    }

    private String nombresPersonas(){
        String[] nombres = {
                "Juan", "María", "Carlos", "Sofía", "Luis", "Ana", "Miguel", "Carmen",
                "Jorge", "Lucía", "Pedro", "Valentina", "Diego", "Laura", "Roberto",
                "Isabel", "Fernando", "Paula", "Alberto", "Natalia", "Gabriel", "Andrea",
                "Javier", "Martina", "Manuel", "Elena", "Ricardo", "Clara", "David",
                "Victoria", "Andrés", "Sara", "Enrique", "Julia", "Pablo", "Mariana",
                "Santiago", "Verónica", "Antonio", "Rocío", "Francisco", "Patricia",
                "Daniel", "Emilia", "Adrián", "Florencia", "Hugo", "Alejandra", "Tomás",
                "Gabriela"
        };

        // Elegir un índice aleatorio
        int indiceAleatorio = (int) (Math.random() * nombres.length);

        // Devolver el nombre aleatorio
        return nombres[indiceAleatorio];
    }


    private String apellidosPersonas() {
        String[] apellidos = {
                "González", "Rodríguez", "López", "Martínez", "Hernández", "Pérez", "Gómez", "Sánchez",
                "Díaz", "Alvarez", "Torres", "Romero", "Ramírez", "Flores", "Cruz", "Morales",
                "Ortiz", "Delgado", "Castro", "Ramos", "Guerrero", "Vargas", "Medina", "Cortez",
                "Mendoza", "Silva", "Reyes", "Fernández", "Rivas", "García", "Velázquez", "Álvarez",
                "Chávez", "Fuentes", "Navarro", "Paredes", "Herrera", "Aguilar", "Ibarra", "Salinas",
                "Cabrera", "Acosta", "Montoya", "Vega", "Campos", "Santana", "Valdez", "Peña", "Luna", "Esquivel"
        };

        int indiceAleatorio = (int) (Math.random() * apellidos.length);
        return apellidos[indiceAleatorio];
    }

    private String productosNombres() {
        String[] productos = {"YERBA AGROECOLOGICA CON PALO", "YERBA AGROECOLOGICA SIN PALO", "YERBA BOLSON GRANEL CON PALO", "YERBA BOLSON GRANEL SIN PALO", "TE VERDE ORGANICO GRANEL", "TE ROJO ORGANICO GRANEL", "TE NEGRO ORGANICO GRANEL", "TE VERDE SAQUITO", "TE ROJO SAQUITO", "TE MIX HIERBAS SAQUITO", "TE MANZANILLA SAQUITO", "TE BOLDO SAQUITO", "BARRA PROTEICA VAINILLA Y MACA PERUANA", "BARRA PROTEICA COCO NATURAL", "BARRA PROTEICA MANZANA Y ALGARROBA", "BARRA PROTEICA CACAO ESPAÑOL", "BARRA PROTEICA CAFÉ CON LECHE DE COCO", "BARRA DE CACAO Y AVELLANAS X 45GR", "BARRA DE MANZANA Y ARANDANO X 45GR", "BARRA DE PASAS DE UVA Y ALMENDRAS X 45GR", "BARRA DE PASAS DE UVA Y ARANDANOS  X 45GR", "BARRA DE BANANA Y DULCE DE LECHE X 45GR", "BARRA PROTEICA VEGANA X 70GR PONT", "ALFAJOR VEGANO CACAO, DDL DE COCO Y PASTA DE MANÍ X 60GR", "CHOCOLATE VEGANO 71% CACAO, ALMENDRAS Y SAL MARINA X 80GR", "CHOCOLATE 55% CACAO CON ALMENDRAS", "CHOCOLATE 80% CACAO", "CHOCOLATE 100% CACAO SIN AZUCAR", "BOMBON DE NUEZ CON DDL Y CHOCOLATE", "BOMBON DE NUEZ CON DDL Y CHOCOLATE", "ALMENDRA NON PAREIL 25/27", "NUEZ MARIPOSA EXTRA LIGHT", "CASTAÑA DE CAJU W4", "PISTACHO C/ CASCARA", "MIX SECO TRADICIONAL", "BARBACOA SIN TACC", "BRAVA SIN TACC", "PIMIENTA TABASCO SIN TACC", "PASAS DE UVA JUMBO", "PERA WILLIAMS GRANDE", "DURAZNO EN MITADES", "CIRUELA D´AGEN S/C (BOMBON)", "CIRUELA PRESIDENTE S/C", "DATIL CON CAROZO EGIPTO", "DATIL MEDJOOL ISRAEL CON CAROZO", "AMARGON (DIENTE DE LEON)", "MANZANILLA EN FLORES DE EGIPTO", "MENTA PIPERITA (FUERTE)", "AVENA INSTANTANEA 3 ARROYOS", "AVENA TRADICIONAL 3 ARROYOS", "AVENA GRUESA 3 ARROYOS", "ARROZ CARNAROLI GRANEL", "ARROZ BASMATI INDIA CROWN-BOLSA 5KG", "HARINA DE ALMENDRA CON PIEL", "HARINA DE ALMENDRA SIN PIEL", "HARINA DE MAIZ. PAN", "HARINA DE MAIZ. MORIXE", "HARINA DE MAIZ. COSACO", "COUS COUS", "CASCARILLA DE CACAO GRUESA", "CACAO ALCALINO. MARCA CIVEN. ORIGEN VENEZUELA.", "AZUCAR MASCABO BA-LA-JU", "AZUCAR MASCABO BA-LA-JU", "COCO RALLADO HIGH FAT", "POROTO PYTAI", "POROTO SOJA", "POROTO ALUBIA", "PANKO TASSYA", "TAHINI. AL KANATER ORIGEN LIBANO. SIN TACC", "SAL ROSADA FINA DEL HIMALAYA", "ALGAS P/SUSHI YAKI NORI", "MAPLE SYRUP. BERNARD", "SIROPE DE DATIL. LYNA S/TACC", "KALLPA. MACA NEGRA", "KALLPA. MACA"};
        int indiceAleatorio = (int) (Math.random() * productos.length);
        return productos[indiceAleatorio];
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
            Double montoIndividualDeCompra = productos.get(numeroRandomDeProducto).getPrecioDeCompra();
            pedidoLinea.setMontoIndividualCompra((double) Math.round(montoIndividualDeCompra));
            Double montoIndividualVenta = productos.get(numeroRandomDeProducto).getPrecioDeVenta();
            pedidoLinea.setMontoIndividualVenta((double)Math.round(montoIndividualVenta));
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


    public static Movimientos generateMovimientos(int numeroDeMovimientos, Productos inventario,// no modifica stock
                                                  Cuentas cuentas,
                                                  PedidosList pedidosList, Balances balances) {
        Movimientos movimientos = new Movimientos("mock");// para que no reescriba el archivo y genere un movimiento temporal
        for (int i = 0; i < numeroDeMovimientos; i++) {
            Pedido pedido = pedidosList.getPedido(random.nextInt(pedidosList.getPedidosList().size()));
            if(pedido.getIdCuenta()>10){// cuentas usuario root
            Cuenta cuentaAModificar = cuentas.buscarCuentaPorId(pedido.getIdCuenta());
            Movimiento movimiento = new Movimiento(pedido.getTipoDePedido(), cuentaAModificar,
                    pedido, "mock", LocalDate.now());
            movimientos.addMock(movimiento); // lo cargo al listado de movimientos
            cuentaAModificar = movimiento.getCuenta(); // trae la cuenta nueva con el nuevo saldo
            cuentas.modificarCuentaPorCuentaMock(cuentaAModificar);// setea el nuevo saldo en el arreglo de cuentas
            //inventario.actualizarStockPorPedidos(movimiento.getProductosComercializados());
            pedidosList.cambiarEstadoPedidoMock(pedido);

                if(pedido.getTipoDePedido()==TipoDeMovimiento.VENTA) {
                    Balance balance = new Balance(LocalDate.now(), movimiento.getMontoTotal(), 0.0, cuentaAModificar.getTipoCuenta());
                    balances.addMock(balance);
                }
                if(pedido.getTipoDePedido()==TipoDeMovimiento.COMPRA) {
                    Balance balance = new Balance(LocalDate.now(), 0.0, movimiento.getMontoTotal(),cuentaAModificar.getTipoCuenta());
                    balances.addMock(balance);
                }

            }

        }
        cuentas.persistenciaEscribirMock();
        pedidosList.persistenciaEscribirMock();
        balances.persistenciaEscribirMock();

        return movimientos;
    }
}


