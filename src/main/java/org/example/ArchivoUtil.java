package org.example;


import com.enums.*;
import com.models.*;
import com.models.funciones.Movimiento;

import java.io.*;
        import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.*;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class ArchivoUtil<T> {

    private final String archivoCSV;
    private final Class<T> clazz; // Clase genérica para reflexiones

    public ArchivoUtil(String archivoCSV, Class<T> clazz) {
        this.archivoCSV = archivoCSV;
        this.clazz = clazz;
    }

    public List<T> leerArchivo(String separador) {
        List<T> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(archivoCSV), "ISO-8859-1"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(separador);

                    T obj = clazz.getDeclaredConstructor().newInstance();

                Field[] campos = clazz.getDeclaredFields();


                for (int i = 0; i < campos.length && i < datos.length; i++) {
                    Field campo = campos[i];
                    campo.setAccessible(true);
                    Object valor = convertirValor(campo.getType(), datos[i]);
                    if (valor != null) {
                        campo.set(obj, valor);
                    }
                }

                lista.add(obj);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }


    public List<Persona> leerArchivoPersonas(String separador) {// abstracta
        List<Persona> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(archivoCSV), "ISO-8859-1"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(separador);

                /*for(int i = 0 ; i< datos.length; i++){
                    System.out.println(i+": "+ datos[i]);
                }*/

                Persona persona;


                if (datos[5].equals("CLIENTE")) {  // Supongamos que el primer campo identifica si es Cliente
                    persona = new Cliente();
                } else if (datos[5].equals("PROVEEDOR")) {  // O si es Proveedor
                    persona = new Proveedor();
                } else {
                    throw new IllegalArgumentException("Tipo de persona desconocido en los datos.");
                }

                // Reflexión para rellenar los campos del objeto
                Field[] campos = persona.getClass().getSuperclass().getDeclaredFields();
                for (int i = 0; i < campos.length && i < datos.length; i++) {
                    Field campo = campos[i];
                    campo.setAccessible(true);
                    Object valor = convertirValor(campo.getType(), datos[i]);
                    if (valor != null) {
                        campo.set(persona, valor);
                    }
                }

                lista.add(persona);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;

    }

    public List<Pedido> leerArchivoPedidos(String separador) {// List<PedidoLinea> no es class asi que complica la lectura generica
        List<Pedido> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(archivoCSV), "ISO-8859-1"))) {
            String linea;
            while ((linea = br.readLine()) != null) {// lee lineas hasta que se acaben
                String[] partes = linea.split(separador);
                /*for(int i = 0 ; i< datos.length; i++){
                    System.out.println(i+": "+ datos[i]);
                }*/
                Pedido pedido = new Pedido();
                // Extraer contenido entre corchetes
                //String contenido = datos.substring(datos.indexOf("[") + 1, datos.lastIndexOf("]"));
                //        + ", id="+id
                //        + " [idCuenta=" + idCuenta
                //        + ", tipoDePedido=" + tipoDePedido
                //        + ", montoTotal=" + montoTotal
                //        + ", ejecutado=" + ejecutado
                //        +  ", lineasPedidoLineas="
                //        + lineasPedidoLineas
                // Extraer los valores de los atributos desde la cadena
                String id = partes[0];
                String idCuenta = partes[1];
                String tipoDePedidoStr = partes[2];
                String montoTotal = partes[3];
                String ejecutadoStr = partes[4];;
                String lineasPedidoLineasStr = partes[5];
                List<PedidoLinea> lineasPedidos = crearLineasPedidoDesdeString(lineasPedidoLineasStr);;

                // Mapear los valores extraídos a los atributos del objeto Pedido
                pedido.setId(Integer.parseInt(id));
                pedido.setIdCuenta(Integer.parseInt(idCuenta));
                pedido.setTipoDePedido(TipoDeMovimiento.valueOf(tipoDePedidoStr)); // Enum convertido desde String
                pedido.setMontoTotal(Double.parseDouble(montoTotal));
                pedido.setEjecutado(Boolean.parseBoolean(ejecutadoStr));
                pedido.setLineasPedidos(lineasPedidos);
                lista.add(pedido);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;

    }


    public void escribirArchivo(List<T> lista, String separador) {
        String archivoCopia = archivoCSV.replace(".csv", "_backup_" +LocalDate.now() + ".csv");
        Path rutaOriginal = Paths.get(archivoCSV);
        Path rutaCopia = Paths.get(archivoCopia);

        try {
            Files.copy(rutaOriginal, rutaCopia, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println("Error al generar el respaldo.");
            e.printStackTrace();
        }


        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(archivoCSV), "ISO-8859-1"))) {
            for (T obj : lista) {
                Field[] campos =clazz.getDeclaredFields();
                    StringBuilder linea = new StringBuilder();
                for (Field campo : campos) {
                    campo.setAccessible(true);
                    Object valor = campo.get(obj);
                    if (valor != null) {
                        linea.append(valor.toString()).append(separador);
                    } else {
                        linea.append("").append(separador);
                    }
                }
                // Eliminar el último separador
                if (linea.length() > 0) {
                    linea.setLength(linea.length() - separador.length());
                }
                pw.println(linea.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void escribirArchivoPedido(List<Pedido> listaPedido, String separador) {
        // Generar el archivo de respaldo
        String archivoCopia = archivoCSV.replace(".csv", "_backup_" + LocalDate.now() + ".csv");
        Path rutaOriginal = Paths.get(archivoCSV);
        Path rutaCopia = Paths.get(archivoCopia);

        try {
            Files.copy(rutaOriginal, rutaCopia, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println("Error al generar el respaldo del archivo.");
            e.printStackTrace();
        }

        // Verificar si la lista está vacía
        if (listaPedido == null || listaPedido.isEmpty()) {
            System.err.println("La lista de movimientos está vacía. No se generará el archivo.");
            return;
        }

        // Escribir los datos en el archivo CSV
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(archivoCSV), "UTF-8"))) {
            for (Pedido obj : listaPedido) {
                StringBuilder linea = new StringBuilder();

                // Agregar los campos del objeto Movimiento
                linea.append(obj.getIdCuenta()).append(separador)
                        .append(obj.getTipoDePedido()).append(separador)
                        .append(obj.getLineasPedidos()).append(separador)
                        .append(obj.getMontoTotal()).append(separador)
                        .append(obj.isEjecutado()).append(separador)
                        .append(obj.getId());

                // Escribir la línea en el archivo
                pw.println(linea.toString());
            }
        } catch (Exception e) {
            System.err.println("Error al escribir en el archivo CSV.");
            e.printStackTrace();
        }
    }



    public void escribirArchivoMov(List<Movimiento> listaMovimientos, String separador) {
        // Generar el archivo de respaldo
        String archivoCopia = archivoCSV.replace(".csv", "_backup_" + LocalDate.now() + ".csv");
        Path rutaOriginal = Paths.get(archivoCSV);
        Path rutaCopia = Paths.get(archivoCopia);

        try {
            Files.copy(rutaOriginal, rutaCopia, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println("Error al generar el respaldo del archivo.");
            e.printStackTrace();
        }

        // Verificar si la lista está vacía
        if (listaMovimientos == null || listaMovimientos.isEmpty()) {
            System.err.println("La lista de movimientos está vacía. No se generará el archivo.");
            return;
        }

        // Escribir los datos en el archivo CSV
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(archivoCSV), "UTF-8"))) {
            for (Movimiento obj : listaMovimientos) {
                StringBuilder linea = new StringBuilder();

                // Agregar los campos del objeto Movimiento
                linea.append(obj.getId()).append(separador)
                        .append(obj.getFecha()).append(separador)
                        .append(obj.getCuenta()).append(separador)
                        .append(obj.getProductosComercializados()).append(separador)
                        .append(obj.getMontoTotal()).append(separador)
                        .append(obj.getSaldoAnterior()).append(separador)
                        .append(obj.getSaldoModificado()).append(separador)
                        .append(obj.getDescripcion());

                // Escribir la línea en el archivo
                pw.println(linea.toString());
            }
        } catch (Exception e) {
            System.err.println("Error al escribir en el archivo CSV.");
            e.printStackTrace();
        }
    }





    //------------- convertir valores desde string

    private Object convertirValor(Class<?> tipo, String dato) {
        try {
            if (tipo == LocalDate.class ) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                System.out.println(dato);
                return LocalDate.parse(dato,formatter);}
            if (tipo == int.class || tipo == Integer.class) {
                return Integer.parseInt(dato);
            } else if (tipo == float.class || tipo == Float.class) {
                return Float.parseFloat(dato);
            } else if (tipo == double.class || tipo == Double.class) {
                return Double.parseDouble(dato);
            } else if (tipo == boolean.class || tipo == Boolean.class) {
                return Boolean.parseBoolean(dato);
            } else if (tipo == String.class) {
                return dato;
            } else if (tipo == Persona.class) {
                // Crear un objeto Persona desde un String (implementa lógica aquí)
                return crearPersonaDesdeString(dato);
            } else if (tipo == Domicilio.class) {
                // Crear un objeto Domicilio desde un String (implementa lógica aquí)
                return crearDomicilioDesdeString(dato);
            } else if (tipo == Movimiento.class) {
                // Crear un objeto Movimiento desde un String (implementa lógica aquí)
                return crearMovimientoDesdeString(dato);
            } else if (tipo == Pedido.class) {
                return crearPedidoDesdeString(dato);
            } else if (tipo == PedidoLinea.class) {
                return crearPedidoLineaDesdeString(dato);
            } else if (tipo == Producto.class) {
                return crearProductoDesdeString(dato);
            } else if (tipo == Cliente.class) {
                return crearClienteDesdeString(dato);
            } else if (tipo == Proveedor.class) {
                return crearProveedorDesdeString(dato);
            }
            else if (tipo == Cuenta.class) {
                return crearCuentaDesdeString(dato);
            }
            else if (tipo.isEnum()) {
                @SuppressWarnings("unchecked")
                Class<? extends Enum> enumClass = (Class<? extends Enum>) tipo;
                return crearEnumDesdeString(dato, enumClass);
            }

        } catch (Exception e) {
            System.out.println("ArchivoUtil Error al convertir valor: " + dato + " a tipo " + tipo.getName());
        }
        return null;
    }




  private Domicilio crearDomicilioDesdeString(String datos) {
        try {
            // Extraer contenido entre corchetes
            String contenido = datos.substring(datos.indexOf("[") + 1, datos.lastIndexOf("]"));
            String[] partes = contenido.split(", ");

            // Mapear atributos, eliminando espacios innecesarios
            String calle = partes[0].split("=")[1].trim();
            int altura = Integer.parseInt(partes[1].split("=")[1].trim());
            int piso = Integer.parseInt(partes[2].split("=")[1].trim());
            char depto = partes[3].split("=")[1].trim().charAt(0);

            return new Domicilio(calle, altura, piso, depto);
        } catch (Exception e) {
            System.err.println("Error al crear Domicilio desde String: " + e.getMessage());
            return null;
        }
    }

    private Producto crearProductoDesdeString(String datos) {

        /*
        Producto [idProd=2
                , nombre=Producto_2
                , marca=Marca_5
                , categoria=CAT1
                , stock=34
                , precioCompra=7695.7
                , precioVenta=9619.6
                , FechaVen=FechaVen
                , porcentajeVenta=porcentajeVenta
                , proveedor=Proveedor [id=78, nombre=Nombre_78, apellido=Apellido_78, dni=67692798, domicilio=Domicilio [calle=Calle_33, altura=2361, piso=10, depto=K], tipoPersona=PROVEEDOR, email=persona78@example.com, active=false]]
                */
        try {
        int inicio = datos.indexOf("Producto [idProd=") + "Producto [idProd=".length();
        int fin = datos.indexOf(", nombre=");
        String idProdStr = datos.substring(inicio, fin);
        int idProd = Integer.parseInt(idProdStr);

        inicio = datos.indexOf(", nombre=") + ", nombre=".length();
        fin = datos.indexOf(", marca=");
        String nombre = datos.substring(inicio, fin);

        inicio = datos.indexOf(", marca=") + ", marca=".length();
        fin = datos.indexOf(", categoria=");
        String marca = datos.substring(inicio, fin);

        inicio = datos.indexOf(", categoria=") + ", categoria=".length();
        fin = datos.indexOf(", stock=");
        String categoriaStr = datos.substring(inicio, fin);
        CatProducto categoria = crearEnumDesdeString(categoriaStr, CatProducto.class); // Categoría (Enum)

        inicio = datos.indexOf(", stock=") + ", stock=".length();
        fin = datos.indexOf(", precioCompra=");
        String stockSrt = datos.substring(inicio, fin);
        int stock = Integer.parseInt(stockSrt);

        inicio = datos.indexOf(", precioCompra=") + ", precioCompra=".length();
        fin = datos.indexOf(", precioVenta=");
        String precioCompraSrt = datos.substring(inicio, fin);
        Double precioCompra = Double.parseDouble(precioCompraSrt);

        inicio = datos.indexOf(", precioVenta=") + ", precioVenta=".length();
        fin = datos.indexOf(", FechaVen=");
        String precioVentaSrt = datos.substring(inicio, fin);
        Double precioVenta = Double.parseDouble(precioVentaSrt);

        inicio = datos.indexOf(", FechaVen=") + ", FechaVen=".length();
        fin = datos.indexOf(", porcentajeVenta=");
        String fechaVen = datos.substring(inicio, fin);

        inicio = datos.indexOf(", porcentajeVenta=") + ", porcentajeVenta=".length();
        fin = datos.indexOf(", proveedor=");
        String porcentajeVentaStr = datos.substring(inicio, fin);
        int porcentajeVenta = Integer.parseInt(porcentajeVentaStr);

        inicio = datos.indexOf(", proveedor=") + ", proveedor=".length();
        fin = (datos.length()-1);
        String proveedorStr = datos.substring(inicio, fin);
        Proveedor proveedor = crearProveedorDesdeString(proveedorStr);

         // Crear el objeto Producto con el constructor por defecto
            Producto producto = new Producto();
            // Asignar los valores a los atributos del Producto
            producto.setIdProd(idProd);
            producto.setNombreProd(nombre);
            producto.setMarcaProd(marca);
            producto.setCategoriaProd(categoria);
            producto.setStock(stock);
            producto.setPrecioDeCompra(precioCompra);
            producto.setPrecioDeVenta(precioVenta);
            producto.setFechaVen(fechaVen);
            producto.setPorcentajeVenta(porcentajeVenta);
            producto.setProveedor(proveedor);

            // Retornar el objeto Producto con los valores asignados
            return producto;

        } catch (Exception e) {
            System.err.println("Error al crear Producto desde String: " + e.getMessage());
            return null; // En caso de error, devolver null
        }
    }

    private Cuenta crearCuentaDesdeString(String datos) {

        /*
        *
        *Cuenta [id=4008
        //, persona=Cliente [id=1001, nombre=Pedro, apellido=M, dni=30196270, domicilio=Domicilio [calle=siempre, altura=136, piso=0, depto=z], tipoPersona=CLIENTE, email=da@da, active=true]
        //, idPersona=1001
        //, saldo=324545.0
        //, tipoCuenta=PESOSEFECTIVO
        //, activa=true] */
        try {
        int inicio = datos.indexOf("Cuenta [id=") + "Cuenta [id=".length();
        int fin = datos.indexOf(", persona=");
        String idStr = datos.substring(inicio, fin);
        int id = Integer.parseInt(idStr);

        inicio = datos.indexOf(", persona=") + ", persona=".length();
        fin = datos.indexOf(", idPersona=");
        String personaStr = datos.substring(inicio, fin);

        inicio = datos.indexOf(", idPersona=") + ", idPersona=".length();
        fin = datos.indexOf(", saldo=");
        String idPersonaStr = datos.substring(inicio, fin);
        int idPersona = Integer.parseInt(idPersonaStr);

        inicio = datos.indexOf(", saldo=") + ", saldo=".length();
        fin = datos.indexOf(", tipoCuenta=");
        String saldoStr = datos.substring(inicio, fin);
        Double saldo = Double.parseDouble(saldoStr);

        inicio = datos.indexOf(", tipoCuenta=") + ", tipoCuenta=".length();
        fin = datos.indexOf(", activa=");
        String tipoCuentaStr = datos.substring(inicio, fin);
        TipoCuenta tipoCuenta = TipoCuenta.valueOf(tipoCuentaStr);

        inicio = datos.indexOf(", activa=") + ", activa=".length();
        fin = datos.length()-1;
        String activaStr = datos.substring(inicio, fin);
        boolean activa = Boolean.parseBoolean(activaStr);

            // Crear la instancia de Cuenta
            Cuenta cuenta = new Cuenta();
            cuenta.setId(id); // Si tienes un setter para ID
            cuenta.setIdPersona(idPersona);

            if(personaStr.contains("Cliente")==true){
                Cliente persona = new Cliente();
                persona = crearClienteDesdeString(personaStr);
                cuenta.setPersona(persona);
            }
            else{
                Proveedor persona = new Proveedor();
                persona = crearProveedorDesdeString(personaStr);
                cuenta.setPersona(persona);
            }

            cuenta.setSaldo(saldo);
            cuenta.setTipoCuenta(tipoCuenta);
            cuenta.setActiva(activa);
            if(cuenta.getIdPersona()!=cuenta.getPersona().getId()){
                throw new IllegalArgumentException("no coinciden los id");} // integridad del dato persona
            return cuenta;
        } catch (IllegalArgumentException e) {
            System.err.println("Error al convertir tipoCuenta: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("Error al crear Cuenta desde String: " + e.getMessage());
            return null;
        }
    }



    private Movimiento crearMovimientoDesdeString(String datos) {
        Movimiento movimiento = new Movimiento();
        /*return getClass().getSimpleName() + " [id=" + id + ", fecha=" + fecha + ", cuenta=" + cuenta
                + ", productosComercializados=" + productosComercializados
                + ", montoTotal=" + montoTotal + ", saldoAnterior=" + saldoAnterior
                + ", saldoModificado=" + saldoModificado + ", descripcion=" + descripcion + "]";*/
        try{
        String id = datos.substring(datos.indexOf("id=") + 3, datos.lastIndexOf(","));
        String fecha = datos.substring(datos.indexOf("fecha=") + 6, datos.lastIndexOf(","));
        String montoTotal = datos.substring(datos.indexOf("montoTotal=") + 11, datos.lastIndexOf(","));
        String saldoAnterior = datos.substring(datos.indexOf("saldoAnterior=") + 14, datos.lastIndexOf(","));
        String saldoModificado = datos.substring(datos.indexOf("saldoModificado=") + 16, datos.lastIndexOf(","));
        String descripcion = datos.substring(datos.indexOf("descripcion=") + 12, datos.lastIndexOf("]"));

        String cuentaStr = datos.substring(datos.indexOf("cuenta=") + 7, datos.lastIndexOf(", productosComercializados="));
        Cuenta cuenta = crearCuentaDesdeString(cuentaStr);
        String productosComercializadosStr = datos.substring(datos.indexOf("productosComercializados=") + 24, datos.lastIndexOf(", montoTotal="));
        Pedido productosComercializados = crearPedidoDesdeString(productosComercializadosStr);

        movimiento.setId(Integer.parseInt(id));
        movimiento.setFecha(LocalDate.parse(fecha));
        movimiento.setDescripcion(descripcion);
        movimiento.setMontoTotal(Double.parseDouble(montoTotal));
        movimiento.setSaldoAnterior(Double.parseDouble(saldoAnterior));
        movimiento.setSaldoModificado(Double.parseDouble(saldoModificado));
        movimiento.setProductosComercializados(productosComercializados);
        movimiento.setCuenta(cuenta);

        if(movimiento.getCuenta().getId()!=movimiento.getProductosComercializados().getIdCuenta()){
            throw new IllegalArgumentException("no coinciden los id");} // integridad del dato cuenta y pedido
        return movimiento;
        } catch (IllegalArgumentException e) {
        System.err.println("Error al convertir tipoCuenta: " + e.getMessage());
        return null;
        } catch (Exception e) {
        System.err.println("Error al crear Cuenta desde String: " + e.getMessage());
        return null;
        }
    }


    private Pedido crearPedidoDesdeString(String datos) {
        Pedido pedido = new Pedido();
        // Extraer contenido entre corchetes
        //String contenido = datos.substring(datos.indexOf("[") + 1, datos.lastIndexOf("]"));
        /*        Pedido, id=0 listo
                 [idCuenta=4008 listo
                        , tipoDePedido=VENTA
                        , montoTotal=57810.0
                        , ejecutado=false
                        , lineasPedidoLineas=[PedidoLinea [producto=Producto [idProd=1, nombre=Producto_1, marca=Marca_5, categoria=CAT3, stock=11, precioCompra=2312.4, precioVenta=2890.5, FechaVen=null, porcentajeVenta=25, proveedor=Proveedor [id=661, nombre=Nombre_661, apellido=Apellido_661, dni=09840321, domicilio=Domicilio [calle=Calle_5, altura=4352, piso=4, depto=L], tipoPersona=PROVEEDOR, email=persona661@example.com, active=false]], cantidad=20, montoCompra=2312.4, montoVenta=2890.5]
        ]]*/
        int inicio = datos.indexOf("Pedido, id=") + "Pedido, id=".length();
        int fin = datos.indexOf(" [idCuenta="); //listo
        String idStr = datos.substring(inicio, fin);
        int id = Integer.parseInt(idStr);

        inicio = datos.indexOf(" [idCuenta=") + " [idCuenta=".length();
        fin = datos.indexOf(", tipoDePedido="); //listo
        String idCuentaStr = datos.substring(inicio, fin);
        int idCuenta = Integer.parseInt(idCuentaStr);

        inicio = datos.indexOf(", tipoDePedido=") + ", tipoDePedido=".length();
        fin = datos.indexOf(", montoTotal="); // listo
        String tipoDePedidoStr = datos.substring(inicio, fin);
        TipoDeMovimiento tipoDePedido = TipoDeMovimiento.valueOf(tipoDePedidoStr);

        inicio = datos.indexOf(", montoTotal=") + ", montoTotal=".length();
        fin = datos.indexOf(", ejecutado="); // listo
        String montoTotalStr = datos.substring(inicio, fin);
        Double montoTotal = Double.parseDouble(montoTotalStr);

        inicio = datos.indexOf(", ejecutado=") + ", ejecutado=".length();
        fin = datos.indexOf(", lineasPedidoLineas="); //
        String ejecutadoStr = datos.substring(inicio, fin);
        boolean ejecutado = Boolean.parseBoolean(ejecutadoStr);

        inicio = datos.indexOf(", lineasPedidoLineas=") + ", lineasPedidoLineas=".length();
        fin = datos.length()-1; //
        String lineasPedidosStr = datos.substring(inicio, fin);
        List<PedidoLinea> lineasPedidos = crearLineasPedidoDesdeString(lineasPedidosStr);

        // Mapear los valores extraídos a los atributos del objeto Pedido
        pedido.setId(id);
        pedido.setIdCuenta(idCuenta);
        pedido.setTipoDePedido(tipoDePedido); // Enum convertido desde String
        pedido.setMontoTotal(montoTotal);
        pedido.setEjecutado(ejecutado);
        pedido.setLineasPedidos(lineasPedidos);
        return pedido;
    }

    private List<PedidoLinea> crearLineasPedidoDesdeString(String lineasPedidoLineasStr) {
        List<PedidoLinea> lineasPedido = new ArrayList<>();

        // Eliminar los corchetes iniciales y finales de la lista si existen
        lineasPedidoLineasStr = lineasPedidoLineasStr.substring(13, lineasPedidoLineasStr.length() - 1);
        // Dividir la cadena en objetos PedidoLinea individuales

                String[] lineas = lineasPedidoLineasStr.split(", PedidoLinea \\[");
            int inicio = lineas[0].indexOf("producto=") + ("producto=").length();
            int fin = lineas[0].indexOf(", cantidad=");
            String producto = lineas[0].substring(inicio, fin);

            inicio = lineas[0].indexOf(", cantidad=") + ", cantidad=".length();
            fin = lineas[0].indexOf(", montoCompra=");
            String cantidad = lineas[0].substring(inicio, fin);

            inicio = lineas[0].indexOf(", montoCompra=") + ", montoCompra=".length();
            fin = lineas[0].indexOf(", montoVenta=");
            String montoCompra = lineas[0].substring(inicio, fin);

            inicio = lineas[0].indexOf(", montoVenta=") + ", montoVenta=".length();
            fin = lineas[0].length()-1;
            String montoVenta = lineas[0].substring(inicio, fin);

            ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(lineas));

        // Procesar cada línea y crear el objeto PedidoLinea
        for (String linea : arrayList) {
            // Reconstruir el formato esperado si es necesario
            // Posición de la primera frase delimitadora
            PedidoLinea pedidoLinea = crearPedidoLineaDesdeString(linea);
            lineasPedido.add(pedidoLinea);
        }

        return lineasPedido;
    }


        private PedidoLinea crearPedidoLineaDesdeString(String datos) {
            PedidoLinea pedidoLinea = new PedidoLinea();

            // Extraer los valores de los atributos desde la cadena
            String productoStr = datos.substring(datos.indexOf("producto=") + 9, datos.indexOf(", cantidad="));
            String cantidad = datos.substring(datos.indexOf("cantidad=") + 9, datos.indexOf(", montoCompra="));
            String montoCompra = datos.substring(datos.indexOf("montoCompra=") + 12, datos.indexOf(", montoVenta="));
            String montoVenta = datos.substring(datos.indexOf("montoVenta=") + 11, datos.lastIndexOf("]"));

            // Mapear los valores extraídos a los atributos del objeto PedidoLinea
            Producto producto = crearProductoDesdeString(productoStr); // Método auxiliar para procesar Producto
            pedidoLinea.setProducto(producto);
            pedidoLinea.setCantidad(Integer.parseInt(cantidad));
            pedidoLinea.setMontoIndividualCompra(Double.parseDouble(montoCompra));
            pedidoLinea.setMontoIndividualVenta(Double.parseDouble(montoVenta));

            return pedidoLinea;
        }



    private Cliente crearClienteDesdeString(String datos) {
        // Extraer contenido entre corchetes
        String contenido = datos.substring(datos.indexOf("[") + 1, datos.lastIndexOf("]"));
        String[] partes = contenido.split(", ");

        // Mapear atributos
        int id = Integer.parseInt(partes[0].split("=")[1]);
        String nombre = partes[1].split("=")[1];
        String apellido = partes[2].split("=")[1];
        String dni = partes[3].split("=")[1];
        String tipoPersona = partes[8].split("=")[1];
        String email = partes[9].split("=")[1];
        String active = partes[10].split("=")[1];


        // Crear Domicilio desde su parte de la cadena PARTE 4
        String domicilioStr = contenido.substring(contenido.indexOf("Domicilio ["), contenido.lastIndexOf("]") + 1);
        Domicilio domicilio = crearDomicilioDesdeString(domicilioStr);
        String tipoProveedor = partes[8].split("=")[1];
        Cliente cliente = new Cliente(nombre, apellido, dni, domicilio);
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setDni(dni);
        cliente.setDomicilio(domicilio);
        cliente.setId(id);
        cliente.setEmail(email);
        cliente.setTipoPersona(crearEnumDesdeString(tipoPersona, TipoPersona.class));
        cliente.setActive(Boolean.parseBoolean(active));
        return cliente;
    }

    private Proveedor crearProveedorDesdeString(String datos) {
        // Extraer contenido entre corchetes
        String contenido = datos.substring(datos.indexOf("[") + 1, datos.lastIndexOf("]"));
        String[] partes = contenido.split(", ");

        // Mapear atributos
        /*System.out.println("el original: ");
        for (int i=0 ; i<partes.length; i++){
            System.out.print(i + ": " + partes[i]+"\n");
        }*/
        int id = Integer.parseInt(partes[0].split("=")[1]);
        String nombre = partes[1].split("=")[1];
        String apellido = partes[2].split("=")[1];
        String dni = partes[3].split("=")[1];
        String tipoPersona = partes[8].split("=")[1];
        String email = partes[9].split("=")[1];
        String active = partes[10].split("=")[1];


        // Crear Domicilio desde su parte de la cadena PARTE 4
        String domicilioStr = contenido.substring(contenido.indexOf("Domicilio ["), contenido.lastIndexOf("]") + 1);
        Domicilio domicilio = crearDomicilioDesdeString(domicilioStr);
        Proveedor proveedor = new Proveedor();
        proveedor.setNombre(nombre);
        proveedor.setApellido(apellido);
        proveedor.setDni(dni);
        proveedor.setDomicilio(domicilio);
        proveedor.setId(id);
        proveedor.setEmail(email);
        proveedor.setTipoPersona(crearEnumDesdeString(tipoPersona,TipoPersona.class));
        proveedor.setActive(Boolean.parseBoolean(active));
        return proveedor;
        // Crear y devolver objeto Persona específico

    }

    private Persona crearPersonaDesdeString(String datos) {
        try {
            // Determina si es Cliente o Proveedor
            boolean esCliente = datos.startsWith("Cliente");
            boolean esProveedor = datos.startsWith("Proveedor");

            if (!esCliente && !esProveedor) {
                throw new IllegalArgumentException("Tipo de persona no identificado en: " + datos);
            }

// Crear y devolver objeto Persona específico
            if (esCliente) {
                return crearClienteDesdeString(datos);

            } else {
                return crearProveedorDesdeString(datos);
            }
        } catch (Exception e) {
            System.err.println("Error al crear Persona desde String: " + e.getMessage());
            return null;
        }
    }

    private <E extends Enum<E>> E crearEnumDesdeString(String datos, Class<E> enumClass) {
        try {
            // Convertimos el string a mayúsculas y eliminamos espacios
            String valorNormalizado = datos.trim().toUpperCase();
            return Enum.valueOf(enumClass, valorNormalizado);
        } catch (IllegalArgumentException e) {
            System.err.println("Error: El valor '" + datos + "' no corresponde a ningún elemento del enumerador " + enumClass.getSimpleName());
            return null; // O puedes lanzar una excepción personalizada si prefieres
        } catch (NullPointerException e) {
            System.err.println("Error: El valor proporcionado es nulo.");
            return null;
        }
    }

    public static void crearArchivo(String archivCSV) {

        if (!Files.exists(Paths.get(archivCSV))) {
            try {
                Files.createFile(Paths.get(archivCSV));
                System.out.println("Archivo creado: " + archivCSV);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}

