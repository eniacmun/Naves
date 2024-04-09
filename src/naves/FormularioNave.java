/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package naves;

import com.toedter.calendar.JDateChooser;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Usuario
 */
public class FormularioNave {
    
    private JTextField txtMaximo = new JTextField(20);
    private JTextField txtCodigo = new JTextField(20);
    private JButton btnAceptar;
    public Conexion conexion;
    
    public FormularioNave() {
        this.conexion = new Conexion();
    }
    
    public JFrame FormularioActualizarNave(Hashtable nave) {
        
        JFrame panel = new JFrame("Actualiza nave");
        panel.setTitle("Formulario de actualización");
        panel.setSize(400, 250);
        panel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        panel.setLayout(new GridBagLayout());
        txtCodigo = new JTextField(nave.get("codigo").toString());
        txtMaximo = new JTextField(nave.get("maximo").toString());
        txtCodigo.setColumns(20);
        txtMaximo.setColumns(20);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Cantidad máxima de tripulantes:"),gbc);
        gbc.gridy++;
        panel.add(new JLabel("Codigo de nave:"),gbc);
        gbc.gridy++;
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(txtMaximo,gbc);
        gbc.gridy++;
        panel.add(txtCodigo,gbc);
        gbc.gridy++;
        gbc.gridy++;
        
        JButton guardarButton = new JButton("Guardar");
        guardarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object idNaveObj = nave.get("idnave");
                if(txtCodigo.getText().isEmpty() || txtCodigo.getText().length()<=0 || txtMaximo.getText().isEmpty() || txtMaximo.getText().length()<=0){
                    JOptionPane.showMessageDialog(null, "La información a registrar no puede ser vacia");
                    return;
                }
                Formato.guardarDatos("UPDATE NAVE SET codigo = '" + txtCodigo.getText() + "', maximo =" + txtMaximo.getText() + ""
                        + "WHERE IDNAVE = "+ Integer.parseInt(idNaveObj.toString()) +";",conexion,true);
                panel.setVisible(false);
                panel.dispose();
            }
        });
        
        panel.add(guardarButton,gbc);
        panel.setLocationRelativeTo(panel);
        panel.setVisible(true);
        return panel;

    }
    
}
