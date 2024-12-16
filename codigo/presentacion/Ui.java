package codigo.presentacion;
import codigo.dominio.Usuario;
import codigo.dominio.Credencial;
import codigo.dominio.Contrasenia;
import codigo.datos.AlmacenUsuario;
import codigo.datos.AlmacenCredencial;

import java.util.Scanner;
import java.util.LinkedList;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Ui {
    private Scanner scanner = new Scanner(System.in);
    private AlmacenUsuario almacenUsuario = new AlmacenUsuario();
    private AlmacenCredencial almacenCredencial = new AlmacenCredencial();
    private Contrasenia password = new Contrasenia();
    private void menuSesion(){
        String opcion;
        boolean salir = false; 
        while(!salir){
            System.out.println("ingrese la opcion");
            System.out.println("1.- Registrar Usuario");
            System.out.println("2.- Iniciar sesion");
            System.out.println("3.- Salir");

            opcion = scanner.nextLine();
            System.out.println("\n\n");
            switch (opcion){
                    case "1": 
                        Usuario usuario = registrarUsuario();
                        try{
                            almacenUsuario.almacenarUsuario(usuario);
                        }catch(IOException e){
                                System.err.println("error al cargar el archivo:" + e);
                        }
                        break;
                    case "2":
                        Usuario usuarioActual = null;
                        usuarioActual = iniciarSesion();
                        if(usuarioActual != null){
                            menuSesionIniciada(usuarioActual);
                         }
                        break;
                    case "3": salir = true;
                        break;
                    default: System.out.println("Opcion incorrecta, intente de nuevo \n\n");
            }   
        }
        
    }
    private void menuSesionIniciada(Usuario usuarioActual){
        String opcion;
        boolean salir = false;
        boolean hayCredenciales = false;
        while(!salir){
            try {
                System.out.println("ingrese la opcion");
                System.out.println("1.- Guardar credencial");
                System.out.println("2.- ver cuentas");
                System.out.println("3.- Ver credencial");
                System.out.println("4.- Cerrar sesion");
                
                opcion = scanner.nextLine();
                System.out.println("\n\n");
                switch (opcion){
                    case "1": Credencial credencial = registrarCredencial(usuarioActual);
                    almacenCredencial.almacenarCredencial(credencial, usuarioActual.getUsername());
                    hayCredenciales = true;
                    break;
                    case "2": if(hayCredenciales){
                        verCuentas(usuarioActual.getUsername());
                    }else{
                        System.out.println("No hay credenciales registradas aun\n");
                    }
                    break;
                    case "3": if(hayCredenciales){
                        verCredencial(usuarioActual.getUsername(), usuarioActual.getContrasenia());
                    }else{
                        System.out.println("No hay credenciales registradas aun\n");
                    }
                    break;
                    case "4": salir = true;
                    break;
                    default: System.out.println("Opcion incorrecta, intente de nuevo \n\n ");
                }
            } catch (IOException e) {
                System.err.println("Error al cargar el archivo:" + e);
            }
        }
         
    }
    
    private void verCuentas(String username){
        try {
            LinkedList<String> cuentas = almacenCredencial.regresarCuentas(username);
            
            for(String c : cuentas){
                System.out.println(c);
            }
            System.out.println("\nPresione enter para continuar");
            scanner.nextLine();
            System.out.println("\n\n");
        } catch (FileNotFoundException e) {
            System.err.println("No hay credenciales registradas aun, error: " + e);
        } catch (IOException e) {
            System.err.println("Error al cargar el archivo:" + e);
        }
        
    }
    
    private void verCredencial(String username, String contraseniaUsuarioCifrada){
        boolean error = true;
        while(error){
            try {
                error = false;
                System.out.println("Ingrese el nombre de la cuenta: ");
                String nombreCredencial = scanner.nextLine();
                Credencial credencial = almacenCredencial.regresarCredencial(nombreCredencial, username);
                if(credencial == null){
                    error = true;
                    System.out.println("Nombre de cuenta no encontrado, intente de nuevo \n\n");
                }else{
                    System.out.println("Nombre : " + credencial.getNombre());
                    System.out.println("Usuario : " + credencial.getUsuario());
                    System.out.println("Contrasenia: " + password.decifrarContrasenia(credencial.getContraseniaCifrada(), contraseniaUsuarioCifrada));
                    System.out.println("url asociado: " + credencial.getUrl());
                    System.out.println("\nPresione enter para continuar");
                    scanner.nextLine();
                    System.out.println("\n\n");
                }
            } catch (FileNotFoundException e) {
            System.err.println("No hay credenciales registradas aun, error: " + e);
            } catch (IOException e) {
                System.err.println("Error al cargar el archivo:" + e);
            } catch (Exception ex) {
                Logger.getLogger(Ui.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
        
    private Usuario registrarUsuario(){
        try {
            System.out.println("Nombre completo:");
            String nombre = scanner.nextLine();
            System.out.println("Usuario:");
            String user = scanner.nextLine();
            System.out.println("Contrasenia: ");
            String contrasenia = scanner.nextLine();
            System.out.println("Correo electronico");
            String infoDeContacto =  scanner.nextLine();
            System.out.println("\n\n");
            
            String contraseniaHasheada = password.hashearContrasenia(contrasenia);
            return new Usuario(nombre, user, contraseniaHasheada, infoDeContacto);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Ui.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    private Usuario iniciarSesion(){
        
        try {
            System.out.println("usuario:");
            String user = scanner.nextLine();
            System.out.println("Contrasenia:");
            String contrasenia = scanner.nextLine();
            System.out.println("\n\n");
            String contraseniaHasheada = password.hashearContrasenia(contrasenia);
            Usuario usuarioActual = almacenUsuario.obtenerUsuario(user);
            if(usuarioActual == null){
                System.out.println("Usuario invalido, intente de nuevo \n\n");
                return null;
            }
            if(!usuarioActual.validarUsuario(user, contraseniaHasheada)){
                System.out.println("Contrasenia incorrecta, intente de nuevo\n\n");
                return null;
            }
            
            return usuarioActual;
        } catch (FileNotFoundException e) {
            System.err.println("No hay usuarios registradas aun, error: " + e);
        } catch (IOException e) {
            System.err.println("Error al cargar el archivo:" + e);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Ui.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private Credencial registrarCredencial(Usuario usuarioActual){
        try {
            boolean opcionValida = false;
            System.out.println("Nombre de la cuenta");
            String nombre = scanner.nextLine();
            System.out.println("Usuaro");
            String usuario = scanner.nextLine();
            System.out.println("\n\n");
            String contrasenia = null;
            while(!opcionValida){
                opcionValida = true;
                System.out.println("¿Generar contrasenia automaticamente?");
                System.out.println("1.-No");
                System.out.println("2.-Si");
                String contraseniaAutom = scanner.nextLine();
                System.out.println("\n\n");
                switch(contraseniaAutom){
                    case "1": System.out.println("Contrasenia");
                    contrasenia = scanner.nextLine();
                    break;
                    case "2": contrasenia = password.generarContraseniaAleatoria();
                    break;
                    default: System.out.println("opcion invalida, intente de nuevo \n\n");
                    opcionValida = false;

                }
            }
            String contraseniaCifrada = password.cifrarContrasenia(contrasenia, usuarioActual.getContrasenia());
            System.out.println("Url del sistema");
            String url = scanner.nextLine();
            System.out.println("\n\n");
            
            return new Credencial(nombre, usuario, contraseniaCifrada, url);
        } catch (Exception ex) {
            Logger.getLogger(Ui.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static void main(String[] args) {
        Ui ui = new Ui();
        ui.menuSesion();
        
        
    }
}
