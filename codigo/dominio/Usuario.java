package codigo.dominio;

public class Usuario {
    private String nombre;
    private String username;
    private String contraseniaCifrada;
    private String informacionDeContacto;
    
    public Usuario(String nombre, String username, String contraseniaCifrada, String informacionDeContacto){
        this.nombre = nombre;
        this.username = username;
        this.contraseniaCifrada = contraseniaCifrada;
        this.informacionDeContacto = informacionDeContacto;
        
    }
    
    public String getContrasenia(){
        return this.contraseniaCifrada;
    }
    
    public String getUsername(){
        return this.username;
    }
    
    public boolean validarUsuario(String usuario, String contraseniaCifrada){ 
        
        return this.contraseniaCifrada.equals(contraseniaCifrada) && this.username.equals(usuario);
    }
    
    public String toString(){
        return this.nombre + "," + this.username + "," + this.contraseniaCifrada + "," + this.informacionDeContacto;
    }
    
    
}
