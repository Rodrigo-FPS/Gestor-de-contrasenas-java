package codigo.datos;
import codigo.dominio.Credencial;
import java.io.*;

import java.util.LinkedList;

public class AlmacenCredencial {
    private final String RUTA = "codigo/datos/";
    private final String ARCHIVO = "CredencialesDB.txt";
    public void almacenarCredencial(Credencial credencial, String username) throws IOException{
        String archivo = RUTA  + username + ARCHIVO;
        try{
        credencial.setNombre(nombreCredencialRepetido(credencial.getNombre(), username));
        }catch(FileNotFoundException e){}
        BufferedWriter writer = new BufferedWriter(new FileWriter(archivo, true));
        writer.write(credencial.toString());
        writer.newLine();
        writer.flush();
            

        
    }
    
    private String nombreCredencialRepetido(String nombreCredencialActual, String username) throws FileNotFoundException, IOException{
        int contador = 2;
        String nuevoNombre = nombreCredencialActual;
        LinkedList<String> nombreCuentas = regresarCuentas(username);
        while(nombreCuentas.contains(nuevoNombre)){
            nuevoNombre = nombreCredencialActual + contador;
            contador++;
        }
        return nuevoNombre;
    }

    public Credencial regresarCredencial(String nombreCuenta, String username) throws FileNotFoundException, IOException{
        String archivo = RUTA  + username + ARCHIVO;
        BufferedReader reader = new BufferedReader(new FileReader(archivo));
        String linea;
        while((linea = reader.readLine()) != null){
            String[] datosCredencial = linea.split(",");
            if(datosCredencial[0].equals(nombreCuenta)){
                return new Credencial(datosCredencial[0], datosCredencial[1], datosCredencial[2], datosCredencial[3]);
            }
                
        }
        return null;
    }
    
    public LinkedList<String> regresarCuentas(String username) throws FileNotFoundException, IOException{
        LinkedList<String> nombresCuentas = new LinkedList<>();
        String archivo = RUTA  + username + ARCHIVO;
        BufferedReader reader = new BufferedReader(new FileReader(archivo));
        String linea;
        while((linea = reader.readLine()) != null){
            String nombreCuenta = linea.split(",")[0];
            nombresCuentas.add(nombreCuenta);
        }
            
        return nombresCuentas;
    }
}
