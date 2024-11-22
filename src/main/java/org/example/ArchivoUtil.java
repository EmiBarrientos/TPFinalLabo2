package org.example;
/*
forma de uso con Persona por ejemplo:
// el archivo debe existir previamente sino se puede poner la siguiente sentencias para informes:
        if (!Files.exists(Paths.get(archivoCSV))) {
            try {
                Files.createFile(Paths.get(archivoCSV));
                System.out.println("Archivo creado: " + archivoCSV);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
// crea el archivo si no existe

ArchivoUtil<Persona> archivo = new ArchivoUtil<>(archivoCSV, Persona.class); // ubicar archivo (archivo, Class)
List<Persona> personas = archivo.leerArchivo(";");
archivo.escribirArchivo(personas, ";");

// Escribir archivo
    archivoUtil.escribirArchivo(personas, ";");

Notas
La clases debe seguir el estándar JavaBean
(atributos privados, constructor sin parámetros, getters y setters).
Adaptar el separador si es necesario (;, ,, etc.).
 */

import com.enums.*;
import com.models.*;
import com.models.funciones.Movimiento;

import java.io.*;
        import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class ArchivoUtil<T> {

    private final String archivoCSV;
    private final Class<T> clazz; // Clase genérica para reflexiones
    private final AtomicInteger numeroDeVersion = new AtomicInteger(0);

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

                // Si el archivo contiene un número de versión en la última columna
                if (campos.length > datos.length && datos.length > 0) {
                    try {
                        numeroDeVersion.set(Integer.parseInt(datos[datos.length - 1]));
                    } catch (NumberFormatException e) {
                        System.out.println("Número de versión no válido: " + datos[datos.length - 1]);
                    }
                }
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

                Persona persona = null;

                if (datos[6].equals("CLIENTE")) {  // Supongamos que el primer campo identifica si es Cliente
                    persona = new Cliente();
                } else if (datos[6].equals("PROVEEDOR")) {  // O si es Proveedor
                    persona = new Proveedor();
                } else {
                    throw new IllegalArgumentException("Tipo de persona desconocido en los datos.");
                }

                // Reflexión para rellenar los campos del objeto
                Field[] campos = persona.getClass().getSuperclass().getDeclaredFields();
                System.out.println("cantidad de campos: " + campos.length);
                for (int i = 0; i < campos.length && i < datos.length; i++) {
                    Field campo = campos[i];
                    campo.setAccessible(true);
                    System.out.println(i+" |atributo: "+campo.getType().toString() + "|dato: "+datos[i]);
                    Object valor = convertirValor(campo.getType(), datos[i]);
                    if (valor != null) {
                        campo.set(persona, valor);
                    }
                }

                lista.add(persona);

                // Si el archivo contiene un número de versión en la última columna
                if (campos.length > datos.length && datos.length > 0) {
                    try {
                        numeroDeVersion.set(Integer.parseInt(datos[datos.length - 1]));
                    } catch (NumberFormatException e) {
                        System.out.println("Número de versión no válido: " + datos[datos.length - 1]);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;

    }

    public void escribirArchivo(List<T> lista, String separador) {
        String archivoCopia = archivoCSV.replace(".csv", "_backup_" +LocalDate.now() +"_"+ numeroDeVersion +"_"+ UUID.randomUUID().toString()+ ".csv");
        Path rutaOriginal = Paths.get(archivoCSV);
        Path rutaCopia = Paths.get(archivoCopia);

        try {
            Files.copy(rutaOriginal, rutaCopia);
        } catch (IOException e) {
            System.err.println("Error al generar el respaldo.");
            e.printStackTrace();
        }

        numeroDeVersion.incrementAndGet(); // Incrementar la versión

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

    //------------- convertir valores desde string

    private Object convertirValor(Class<?> tipo, String dato) {
        try {
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
            System.out.println("Error al convertir valor: " + dato + " a tipo " + tipo.getName());
        }
        return null;
    }
//--------- esto tengo que meterle ;




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
        try {
            // Dividir la cadena usando punto y coma como delimitador
            String[] partes = datos.split(";");

            // Verificar que la cadena tenga el número correcto de partes
            if (partes.length != 12) { // Esperamos 12 partes para todos los atributos
                throw new IllegalArgumentException("El formato del String no es válido. Se esperaban 12 atributos.");
            }

            // Extraer los valores correspondientes de cada parte
            int idProd = Integer.parseInt(partes[0].trim()); // ID del producto
            double precioCompra = Double.parseDouble(partes[1].trim()); // Precio de compra
            String nombre = partes[2].trim(); // Nombre del producto
            String marca = partes[3].trim();  // Marca del producto
            CatProducto categoria = crearEnumDesdeString(partes[4].trim(), CatProducto.class); // Categoría (Enum)
            int stock = Integer.parseInt(partes[5].trim()); // Stock del producto
            double precioVenta = Double.parseDouble(partes[6].trim()); // Precio de venta
            // Se pueden agregar otros datos si es necesario
            // String fechaVen = partes[7].trim(); // Fecha de vencimiento (si es necesario)
            // Algunos campos pueden estar vacíos, se manejan como "" o nulos
            String fechaVen = partes[7].trim().isEmpty() ? null : partes[7].trim(); // Se maneja el valor vacío
            int porcentajeVenta = Integer.parseInt(partes[8].trim()); // Porcentaje de venta

            // Crear el objeto Proveedor a partir de la última parte de la cadena
            System.out.println(partes[9].trim().toString());
            Proveedor proveedor = crearProveedorDesdeString(partes[9].trim());

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
        System.out.println("Entrando en crear cuenta");
        try {
            // Extraer contenido entre corchetes
            String contenido = datos.substring(datos.indexOf("[") + 1, datos.lastIndexOf("]"));
            String[] partes = contenido.split(", ");

            // Mapear atributos, eliminando espacios innecesarios
            int id = Integer.parseInt(partes[0].split("=")[1].trim());
            int idPersona = Integer.parseInt(partes[1].split("=")[1].trim());
            double saldo = Double.parseDouble(partes[2].split("=")[1].trim());

            // Conversión segura del tipo de cuenta
            String tipoCuentaStr = partes[3].split("=")[1].trim().toUpperCase();
            System.out.println("string de tipo cuenta");
            System.out.println(tipoCuentaStr);
            TipoCuenta tipoCuenta = TipoCuenta.valueOf(tipoCuentaStr);

            boolean activa = Boolean.parseBoolean(partes[4].split("=")[1].trim());

            // Crear la instancia de Cuenta
            Cuenta cuenta = new Cuenta();
            cuenta.setId(id); // Si tienes un setter para ID
            cuenta.setIdPersona(idPersona);
            cuenta.setSaldo(saldo);
            cuenta.setTipoCuenta(tipoCuenta);
            cuenta.setActiva(activa);

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
        return movimiento;
    }


    private Pedido crearPedidoDesdeString(String datos) {
        Pedido pedido = new Pedido();
        /*
        return getClass().getSimpleName() + " [id=" + id + ", idCuenta=" + idCuenta + ", tipoDePedido=" + tipoDePedido
                + ", montoTotal=" + montoTotal + ", ejecutado=" + ejecutado + ", eliminado=" + eliminado
                + ", lineasPedidoLineas=" + lineasPedidoLineas + "]";
                */
        // Extraer los valores de los atributos desde la cadena
        String id = datos.substring(datos.indexOf("id=") + 3, datos.indexOf(", idCuenta="));
        String idCuenta = datos.substring(datos.indexOf("idCuenta=") + 9, datos.indexOf(", tipoDePedido="));
        String tipoDePedidoStr = datos.substring(datos.indexOf("tipoDePedido=") + 13, datos.indexOf(", montoTotal="));
        String montoTotal = datos.substring(datos.indexOf("montoTotal=") + 11, datos.indexOf(", ejecutado="));
        String ejecutadoStr = datos.substring(datos.indexOf("ejecutado=") + 10, datos.indexOf(", eliminado="));
        String lineasPedidoLineasStr = datos.substring(datos.indexOf("lineasPedidoLineas=") + 19, datos.lastIndexOf("]"));

        // Mapear los valores extraídos a los atributos del objeto Pedido
        pedido.setId(Integer.parseInt(id));
        pedido.setIdCuenta(Integer.parseInt(idCuenta));
        pedido.setTipoDePedido(TipoDeMovimiento.valueOf(tipoDePedidoStr)); // Enum convertido desde String
        pedido.setMontoTotal(Double.parseDouble(montoTotal));
        pedido.setEjecutado(Boolean.parseBoolean(ejecutadoStr));


        List<PedidoLinea> lineasPedido = crearLineasPedidoDesdeString(lineasPedidoLineasStr);
        pedido.setLineasPedidos(lineasPedido);

        return pedido;
    }

    private List<PedidoLinea> crearLineasPedidoDesdeString(String lineasPedidoLineasStr) {
        List<PedidoLinea> lineasPedido = new ArrayList<>();

        // Eliminar los corchetes iniciales y finales de la lista si existen
        lineasPedidoLineasStr = lineasPedidoLineasStr.substring(1, lineasPedidoLineasStr.length() - 1);

        // Dividir la cadena en objetos PedidoLinea individuales
        String[] lineas = lineasPedidoLineasStr.split("], PedidoLinea \\[");

        // Procesar cada línea y crear el objeto PedidoLinea
        for (String linea : lineas) {
            // Reconstruir el formato esperado si es necesario
            linea = "PedidoLinea [" + linea.trim() + "]";
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

