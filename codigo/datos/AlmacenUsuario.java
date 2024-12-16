package codigo.datos;
import codigo.dominio.Usuario;
import java.util.LinkedList;
import java.io.*;

public class AlmacenUsuario {
    private static final String ARCHIVO = "codigo/datos/UsuariosDB.txt";
    
    public void almacenarUsuario(Usuario usuario) throws IOException{
        LinkedList<Usuario> usuarios = new LinkedList<>();
        usuarios.add(usuario);
        BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO, true));
        for(Usuario u : usuarios){
            writer.write(u.toString());
            writer.newLine();
        }
        writer.flush(); 

   
    }
    
    public Usuario obtenerUsuario(String usuario) throws FileNotFoundException, IOException{
        
        BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO));
        String line;
        while((line = reader.readLine()) != null){
            String[] datosUsuario = line.split(",");
            if(datosUsuario[1].equals(usuario)){
                return new Usuario(datosUsuario[0], datosUsuario[1], datosUsuario[2], datosUsuario[3]);
            }
        }
        return null;
    }
    
}
