package naves;

import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author Usuario
 */
public class Usuario {
    
    public Hashtable usuarios;
    public Hashtable claves;
    public Naves InterfPrincipal;
    
    public Usuario(Naves InterfPrincipal) {
        this.InterfPrincipal = InterfPrincipal;
    }
    
    public void inicializador(Conexion c, Naves InterfPrincipal) {
        this.usuarios = new Hashtable<String,String>();
        this.claves = new Hashtable<String,String>();
        Vector vector= c.getSelectV("SELECT * from USUARIO;");
        
        for(int i=0; i < vector.size(); i++){
            Hashtable h = (Hashtable) vector.get(i);
            String clave = h.get("idusuario").toString();
            String password = h.get("password").toString();
            String usuario = h.get("usuario").toString();
            
            this.claves.put(clave,password);
            this.usuarios.put(clave, usuario);
        }
    }
    
}
