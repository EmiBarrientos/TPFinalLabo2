package com.Menus;

import javax.swing.JOptionPane;
import com.enums.TipoPersona;
import com.models.*;
import com.models.funciones.*;
import org.example.Balances;

public class MenuProveedores extends MenuGral{


    private Cliente clienteGenerica=new Cliente();
    private Proveedor proveedorGenerico= new Proveedor();
    TipoPersona tipoPersona;
    private int indexGenerico;
    private String DNIgenerico="";
    private MenuGral menuGral;

    public MenuProveedores(Personas personas, Cuentas cuentas, Productos productos, Movimientos movimientos, PedidosList pedidosList, Balances balances) {
        super(personas, cuentas, productos, movimientos, pedidosList, balances);
    }

    public void menuProveedores(){
        String menuProveedor = "Ingrese una opción: \n\n" +
                "1. Alta.\n" +
                "2. Baja.\n" +
                "3. Modificación.\n" +
                "4. Buscar.\n" +
                "0. Volver al Menú Principal.\n";

        String input = JOptionPane.showInputDialog(null, menuProveedor, "Menú Proveedores.", JOptionPane.QUESTION_MESSAGE);

        if (input == null) {
            JOptionPane.showMessageDialog(null, "Vuelve al Menu Proveedores.");// No estoy segura a donde va
        }

        int option;

        try{
            option = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Opción no válida. Intente de nuevo.");
            menuProveedores();
            return;
        }

        switch (option){
            case 1:
                JOptionPane.showMessageDialog(null,"Alta de Proveedores.");
                altaProveedor();
                break;
            case 2:
                JOptionPane.showMessageDialog(null, "Baja de Proveedores.");
                bajaProveedor();
                break;
            case 3:
                JOptionPane.showMessageDialog(null, "Modificación de Proveedores.");
                modificarProveedor();
                break;
            case 4:
                JOptionPane.showMessageDialog(null,"Buscar Proveedor.");
                buscarProveedor();
                break;
            case 0:
                JOptionPane.showMessageDialog(null,"Vuelve al Menú Principal.");
                menuPrincipal();
                break;
            default:
                JOptionPane.showMessageDialog(null, "Opción inválida. Intente de nuevo.");
                menuProveedores();
                break;
        }

    }

    private void altaProveedor() {
        this.DNIgenerico = Mensajes.mensajeReturnString("Ingrese el DNI del Proveedor:");
        int index = this.personas.buscarIndexPorDNI(DNIgenerico);
        if(index == -1) {
            // ------------------- opcion cliente no esta (-1) ---------------
            proveedorGenerico = new Proveedor();
            proveedorGenerico = proveedorGenerico.crearProveedor();
            if (proveedorGenerico.mostrarProveedor() == JOptionPane.YES_OPTION) {
                personas.addPersona(proveedorGenerico);
                cuentas.cargarCuentasNuevaPersona(clienteGenerica);
                Mensajes.mensajeOut( "Proveedor registrado con éxito.");
            }

            if (Mensajes.mensajeYesNO("¿Quiere dar de Alta más Proveedores?") == JOptionPane.YES_OPTION) {
                altaProveedor();
            }
            //----------- fin de opcion Proovedor no esta
        }
        else {
            //--------- opcion index > -1 (esta) --------
            if(this.personas.buscarPersonaPorIndex(index).getTipoPersona() == TipoPersona.CLIENTE){
                if (Mensajes.mensajeYesNO("Existe un Proveedor con ese número. ¿Desea crear un Proveedor con ese número igual?") == JOptionPane.YES_OPTION) {
                    proveedorGenerico = new Proveedor();
                    proveedorGenerico = proveedorGenerico.crearProveedor();
                    if (proveedorGenerico.mostrarProveedor() == JOptionPane.YES_OPTION) {
                        personas.addPersona(proveedorGenerico);
                        Mensajes.mensajeOut( "Proveedor registrado con éxito.");
                    }
                    if (Mensajes.mensajeYesNO("¿Quiere dar de alta más Proveedores?") == JOptionPane.YES_OPTION) {
                        altaProveedor();
                    }
                }
            }
            else{ // Existe ese DNI y no es de un cliente entonces es de un proveedor
                Mensajes.mensajeOut("Ya existe un Proveedor con los datos ingresados.");
                if (Mensajes.mensajeYesNO("¿Desea modificar los datos?") == JOptionPane.YES_OPTION) {
                    modificarProveedor();
                }
            }
            //-------------------- fin de opcion cliente no null (esta)
        }
        // No olvidarme de inicializar las variables usadas
        proveedorGenerico = new Proveedor();
        DNIgenerico = "";
    }

    private void bajaProveedor() {
        DNIgenerico = Mensajes.mensajeReturnString("Ingrese el DNI del Proveedor que quiere dar de Baja:");
        int index = this.personas.buscarIndexPorDNI(DNIgenerico);
        if (index != -1) { // index >-1
            if (Mensajes.mensajeYesNO("¿Confirma la Baja del Proveedor?") == JOptionPane.YES_OPTION) {
                this.personas.darBajaPersona(index);
            }
            Mensajes.mensajeOut("Proveedor dado de Baja con éxito.");
        }
        else { // index = -1
            Mensajes.mensajeOut( "No existe Proveedor con ese DNI.");
        }
        // No olvidarme de inicializar las variables usadas
        DNIgenerico = "";
    }

    private void modificarProveedor() {
        DNIgenerico = Mensajes.mensajeReturnString("Ingrese el DNI del Proveedor a Modificar:");
        indexGenerico = this.personas.buscarIndexPorDNI(DNIgenerico);
        if (indexGenerico == -1) {
            // proveedor no encontrado (null)
            Mensajes.mensajeOut("Proveedor no encontrado.");
        }

        else {// proveedor encontrado (no -1)
            Mensajes.mensajeOut("Proveedor encontrado:");
            tipoPersona = personas.buscarPersonaPorIndex(indexGenerico).getTipoPersona();
            System.out.println(tipoPersona);
            if (tipoPersona == TipoPersona.PROVEEDOR){
                proveedorGenerico = (Proveedor) personas.buscarPersonaPorIndex(indexGenerico);
                proveedorGenerico = Proveedor.modificarProveedor(proveedorGenerico);
                if (proveedorGenerico.mostrarProveedor() == JOptionPane.YES_OPTION) {
                    personas.setPersonas(indexGenerico,proveedorGenerico);}
            }
            else{
                Mensajes.mensajeOut("Existe: No es un Proveedor, ES UN CLIENTE.");
            }

            // fin de Persona encontrado
        }
        // No olvidarme de inicializar las variables usadas
        proveedorGenerico = new Proveedor();
        DNIgenerico = "";
        indexGenerico=0;
    }

    private void buscarProveedor() {
        personas.buscarPersonaConMensajito();
    }
}