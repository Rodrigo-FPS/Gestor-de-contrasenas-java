package codigo.dominio;

public class Credencial {
   String nombre;
   String usuario;
   String contraseniaCifrada;
   String url;
   
   public Credencial(String nombre, String usuario, String contraseniaCifrada, String url){
       this.nombre = nombre;
       this.usuario = usuario;
       this.contraseniaCifrada = contraseniaCifrada;
       this.url = url;
            
   }
   
   public void setNombre(String nombre){
       this.nombre = nombre;
   }
   
   public String getNombre(){
       return this.nombre;
   }
   
   public String getUsuario(){
       return this.usuario;
   }
   
   public String getContraseniaCifrada(){
       return this.contraseniaCifrada;
   }
   
   public String getUrl(){
       return this.url;
   }
   
   public String toString(){
       return this.nombre + "," + this.usuario + "," + this.contraseniaCifrada + "," + this.url;
   }
   
   
}
