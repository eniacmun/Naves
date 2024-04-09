/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package naves;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author Usuario
 */
public class Naves extends JPanel implements ActionListener {

    public static Color colorBG = new Color(255, 255, 255);
    private JTextField usernameF;
    private JPasswordField passwordF;
    private JButton loginButton;
    public Usuario usuario;
    public Conexion conexion;
    public static JFrame frame;
    
    
    public static void main(String[] args) {
                try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        frame = new JFrame("----ESCUELA");
        frame.setTitle("Inicio de Sesión");
        frame.setSize(500, 350);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().add(new Naves());
        frame.getContentPane().setBackground(colorBG);
        frame.pack();
        frame.setVisible(true);
    }
    
    public Naves(){
        this.conexion = new Conexion();
        this.usuario = new Usuario(this);
        this.usuario.inicializador(this.conexion, this);
        add(creaPanelLogin());
    }
    
    public JPanel creaPanelLogin() {

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        // Componentes
        JLabel usernameLabel = new JLabel("Usuario:");
        usernameF = new JTextField();
        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordF = new JPasswordField();
        loginButton = new JButton("Iniciar Sesión");

        // Agregar componentes al panel
        panel.add(usernameLabel);
        panel.add(usernameF);
        panel.add(passwordLabel);
        panel.add(passwordF);
        panel.add(new JLabel()); // Espacio vacío para separar
        panel.add(loginButton);

        // Agregar ActionListener al botón
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String user = usernameF.getText().toString();
                String pass = passwordF.getText().toString();
                if (!user.equals("") && !pass.equals("")) {
                    boolean sesion = validaUsuario(user, pass);
                    if (sesion) {
                        frame.setVisible(false);
                        frame.dispose();
                        SwingUtilities.invokeLater(() -> {
                            TabbedPanePrincipal window = new TabbedPanePrincipal();
                        });
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Favor de llenar los campos de inicio de sesion");
                }
            }
        });

        add(panel);

        return panel;
    }
    
    public boolean validaUsuario(String usuariol, String passl){
        int size = this.usuario.usuarios.size();
        for(int i = 0; i<size;i++){
            String usuariotem = (String)this.usuario.usuarios.get(String.valueOf(i+1));
            if(usuariotem.equals(usuariol)){
                String passtem = this.usuario.claves.get(String.valueOf(i+1)).toString();
                if(passtem.equals(passl)){
                    return true;
                }else{
                    JOptionPane.showMessageDialog(null, "Usuario y/o contraseña incorrectos");
                }
            }else{
                JOptionPane.showMessageDialog(null, "Usuario y/o contraseña incorrectos");
            }
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
