package codigo.dominio;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Contrasenia {
    
    public String hashearContrasenia(String password) throws NoSuchAlgorithmException{
        MessageDigest hash = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = hash.digest(password.getBytes());
        StringBuilder hashHexadecimal = new StringBuilder();
        for (byte b : hashBytes){
            String hexadecimal = Integer.toHexString(0xff & b);
            if(hexadecimal.length() == 1){
                hashHexadecimal.append('0');
                    
            }
            hashHexadecimal.append(hexadecimal);
                
        }
        return hashHexadecimal.toString();     
    }
    
    public String generarContraseniaAleatoria(){
        String CARACTERESVALIDOS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_+=<>?";
        SecureRandom numeroRandom = new SecureRandom();
        StringBuilder contraseniaAleatoria = new StringBuilder();
        
        for(int i = 0; i < 13; i++){
            int posicion = numeroRandom.nextInt(CARACTERESVALIDOS.length());
            contraseniaAleatoria.append(CARACTERESVALIDOS.charAt(posicion));
        }
                
        return contraseniaAleatoria.toString();
    }
    
    public String cifrarContrasenia(String contrasenia, String contraseniaUsuario)throws Exception{
        String contraseniaUsuarioTruncada = contraseniaUsuario.substring(0, 16);
        SecretKeySpec secretKey = new SecretKeySpec(contraseniaUsuarioTruncada.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] bytes = cipher.doFinal(contrasenia.getBytes());
        return Base64.getEncoder().encodeToString(bytes);
    }
    
    public String decifrarContrasenia(String contraseniaCifrada, String contraseniaUsuario)throws Exception{

        String contraseniaUsuarioTruncada = contraseniaUsuario.substring(0, 16);
        SecretKeySpec secretKey = new SecretKeySpec(contraseniaUsuarioTruncada.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] contraseniaDescifrada = cipher.doFinal(Base64.getDecoder().decode(contraseniaCifrada));
        return new String(contraseniaDescifrada);

    }
    
}
