package com.Menus;

import com.enums.TipoPersona;
import com.models.*;
import com.models.Cliente;
import com.models.funciones.Mensajes;
import com.models.funciones.Movimientos;
import org.example.Balances;

import javax.swing.JOptionPane;

public class MenuCliente extends Menus{

    Cliente clienteGenerica;
    TipoPersona tipoPersona;
    private int indexGenerico;
    private String DNIgenerico="";
    private MenuGral menuGral;

    public MenuCliente(Personas personas, Cuentas cuentas, Productos productos, Movimientos movimientos, PedidosList pedidosList, Balances balances) {
        super(personas, cuentas, productos, movimientos, pedidosList, balances);
    }

    public void menuClientes(){
        String menuClientes = "Ingrese una opción: \n\n" +
                "1. Alta. \n" +
                "2. Baja. \n" +
                "3. Modificación. \n" +
                "4. Buscar. \n" +
                "0. Volver al Menú Principal. \n";

        String input = JOptionPane.showInputDialog(null, menuClientes, "Menú Clientes.", JOptionPane.QUESTION_MESSAGE);

        if (input == null){
            JOptionPane.showMessageDialog(null, "Error: Vuelve al menú anterior.."); // No estoy segura si vuelve a menu pedidos o menu principal.
            return;
        }

        int option;

        try {
            option = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Opción no válida. Intente de nuevo.");
            menuClientes();
            return;
        }

        switch (option){
            case 1:
                JOptionPane.showMessageDialog(null, "Alta de Clientes.");
                altaCliente();
                break;
            case 2:
                JOptionPane.showMessageDialog(null, "Baja de Clientes.");
                bajaCliente();
                break;
            case 3:
                JOptionPane.showMessageDialog(null, "Modificación de Clientes.");
                modificarCliente();
                break;
            case 4:
                JOptionPane.showMessageDialog(null, "Buscar Cliente.");
                buscarCliente();
                break;
            case 0:
                JOptionPane.showMessageDialog(null, "Vuelve al Menú Principal.");
                menuGral.menuPrincipal();
                break;
            default:
                JOptionPane.showMessageDialog(null, "Opción no válida. Intente de nuevo.");
                menuClientes();
                break;
        }

    }

    public void altaCliente() {
        this.DNIgenerico = Mensajes.mensajeReturnString("Ingrese el DNI del cliente:");
        int index = this.personas.buscarIndexPorDNI(DNIgenerico);
        if(index == -1) {
            // ------------------- opcion cliente no esta (-1) ---------------
            clienteGenerica = new Cliente();
            clienteGenerica = clienteGenerica.crearCliente();
            if (clienteGenerica.mostrarCliente() == JOptionPane.YES_OPTION) {
                personas.addPersona(clienteGenerica);
                cuentas.cargarCuentasNuevaPersona(clienteGenerica);
                Mensajes.mensajeOut("Cliente registrado con éxito.");
                // No olvidarme de inicializar las variables usadas
            }

            int masClientes = Mensajes.mensajeYesNO("¿Quiere dar de alta más clientes?");
            if (masClientes == JOptionPane.YES_OPTION) {
                altaCliente();
            }
            //----------- fin de opcion cliente no esta

        }
        else {
            //--------- opcion index > -1 (esta) --------
            if(this.personas.buscarPersonaPorIndex(index).getTipoPersona() == TipoPersona.PROVEEDOR){
                if (Mensajes.mensajeYesNO("Existe un Cliente con ese número. ¿Desea crear un Cliente con ese número igual?")
                        == JOptionPane.YES_OPTION) {
                    clienteGenerica = new Cliente();
                    clienteGenerica = clienteGenerica.crearCliente();
                    clienteGenerica.mostrarCliente();
                    if (Mensajes.mensajeYesNO("¿Confirma que los datos son correctos?") == JOptionPane.YES_OPTION) {
                        personas.addPersona(clienteGenerica);
                        Mensajes.mensajeOut("Cliente registrado con éxito.");
                    }
                    if (Mensajes.mensajeYesNO("¿Quiere dar de alta más Clientes?") == JOptionPane.YES_OPTION) {
                        altaCliente();
                    }
                }
            }
            else{
                Mensajes.mensajeOut("Ya existe un Cliente con los datos ingresados.");
                if (Mensajes.mensajeYesNO("¿Desea modificar los datos?") == JOptionPane.YES_OPTION) {
                    modificarCliente();
                }
            }
            //-------------------- fin de opcion cliente no null (esta)
        }
        clienteGenerica = new Cliente();
        DNIgenerico = "";
    }

    private void bajaCliente() {
        DNIgenerico = Mensajes.mensajeReturnString("Ingrese el DNI del Cliente que quiere dar de Baja:");
        int index = this.personas.buscarIndexPorDNI(DNIgenerico);
        if (index != -1) { // index >-1
            if (Mensajes.mensajeYesNO("¿Confirma la Baja del Cliente?") == JOptionPane.YES_OPTION) {
                this.personas.darBajaPersona(index);
            }
            Mensajes.mensajeOut("Cliente dado de Baja con éxito.");
        }
        else { // index = -1
            Mensajes.mensajeOut( "No existe registro con ese DNI.");
        }
        // No olvidarme de inicializar las variables usadas
        DNIgenerico = "";
    }

    private void modificarCliente() {
        DNIgenerico = Mensajes.mensajeReturnString("Ingrese el DNI del Cliente a Modificar:");
        indexGenerico = this.personas.buscarIndexPorDNI(DNIgenerico);
        if (indexGenerico == -1) {
            // cliente no encontrado (null)
            Mensajes.mensajeOut("Cliente no encontrado.");
        }

        else {// cliente encontrado (no -1)
            Mensajes.mensajeOut("Cliente encontrado:");
            tipoPersona = personas.buscarPersonaPorIndex(indexGenerico).getTipoPersona();
            System.out.println(tipoPersona);
            if (tipoPersona == TipoPersona.CLIENTE){
                clienteGenerica = (Cliente) personas.buscarPersonaPorIndex(indexGenerico);
                clienteGenerica = Cliente.modificarCliente(clienteGenerica);
                if (Mensajes.mensajeYesNO("¿Confirma la Modificación?") == JOptionPane.YES_OPTION) {
                    personas.setPersonas(indexGenerico,clienteGenerica);
                    Mensajes.mensajeOut("Modificado con éxito.");}
            }
            else{
                Mensajes.mensajeOut( "Existe: No es un Cliente, ES UN PROVEEDOR.");
            }

            // fin de Persona encontrado
        }
        // No olvidarme de inicializar las variables usadas
        clienteGenerica = new Cliente();
        DNIgenerico = "";
        indexGenerico=0;
    }

    private void buscarCliente() {
        personas.buscarPersonaConMensajito();
    }




}