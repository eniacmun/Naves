package naves;

import java.util.Random;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuario
 */
public class Formato {
    
    public static boolean guardarDatos(String query, Conexion conexion, boolean muestraMensaje){
        if (conexion != null && !conexion.desconectado()) {
            try {
                conexion.getUpdate(query);
                if(muestraMensaje)
                JOptionPane.showMessageDialog(null, "Datos guardados correctamente.");
                return true;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error al guardar los datos: " + ex.getMessage());
                ex.printStackTrace();
                return false;
            }
        } else {
            conexion.getConexion();
            if (conexion != null && !conexion.desconectado()) {
                try {
                    conexion.getUpdate(query);
                    if(muestraMensaje)
                    JOptionPane.showMessageDialog(null, "Datos guardados correctamente.");
                    return true;
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al guardar los datos: " + ex.getMessage());
                    ex.printStackTrace();
                    return false;
                }
            }
        }
        return false;
    }
    
    public static boolean esNumeroEntero(String numero) {
        try {
            int temporal = (new Integer(numero)).intValue();
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
     
     
    public static boolean esNombre(String nombre) {
        nombre = nombre.replace(' ', 'a');
        for (int i = 0; i < nombre.length(); i++) {
            if (!nombre.substring(i, i + 1).matches("[a-zA-Z]") && !nombre.substring(i, i + 1).equals("ñ") && !nombre.substring(i, i + 1).equals("Ñ")) {
                return false;
            }
        }
        if (nombre.length() <= 2) {
            return false;
        }
        return true;
    }
    
    public static int obtenEntero(String cantidad) {
        int regresa = -1;
        try {
            Double w = Double.valueOf(cantidad);
            regresa = w.intValue();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return regresa;
    }
    
    public static String estadoString(String estado) {
        if(obtenEntero(estado) == 1){
            estado = "Activo";
        }else if(obtenEntero(estado) == 2){
            estado = "En vuelo";
        }else{
            estado = "Cancelado";
        }
        return estado;
    }
    
    public static String numeroConLetra(int posicion, Random random){
        
        int numero = random.nextInt(9000) + 1000;
        char letra = (char) (random.nextInt(26) + 'a');
        String numeroConLetra = Integer.toString(numero);
        StringBuilder numeroConLetraBuilder = new StringBuilder(numeroConLetra);
        numeroConLetraBuilder.setCharAt(posicion, letra);
        
        return numeroConLetraBuilder.toString();
    }
}
