package com.validaciones;

import com.excepciones.EmailFormatExcepcion;
import com.excepciones.StringTooLongException;
import com.models.funciones.Mensajes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validaciones {

    public static boolean validarFecha(String fechaStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            // Intentar parsear la cadena
            if(fechaStr.contains("-")){
                LocalDate fecha = LocalDate.parse(fechaStr, formatter2);
                return true;
            }
            LocalDate fecha = LocalDate.parse(fechaStr, formatter);
            return true;
            // Fecha válida
        } catch (DateTimeParseException e) {
            System.out.println("Validaciones: Formato no válido. Formato correcto: dd/MM/yyyy " + e);
            return false; // Fecha no válida
        }
    }

    public static boolean validarDNI(String dni) {
        try {
            if (dni.length() == 8 || dni.length() == 11) {
                //Intenta parsear la cadena
                Integer.parseInt(dni);
                return true;
            } else {
                throw new StringTooLongException("Not valid input");
            }
        }
        catch (NullPointerException e) {
            Mensajes.mensajeOut("Invalid input. Input Null" + e.getMessage());
            return false;
        }
        catch (NumberFormatException e) {
            Mensajes.mensajeOut("Invalid input. Input MUST be only numbers " + e.getMessage());
            return false;
        } catch (StringTooLongException e) {
            Mensajes.mensajeOut("ERR" + e.getMessage());
            return false;
        }
        catch (Exception e) {
            Mensajes.mensajeOut("Invalid input" + e.getMessage());
            return false;
        }

    }

    public static boolean validarEmail(String email){
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        try {
            Matcher matcher = pattern.matcher(email);
            if(!matcher.matches()){
                throw new EmailFormatExcepcion();
            }
            return true;
        }catch (EmailFormatExcepcion e){
            System.out.println(e.getMessage());
            return false;
        }

    }









}
