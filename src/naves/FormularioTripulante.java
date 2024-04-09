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
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
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
public class FormularioTripulante {
    
    private JTextField txtNombre = new JTextField(20);
    private JButton btnAceptar;
    public Conexion conexion;
    
    public FormularioTripulante() {
        this.conexion = new Conexion();
    }
    
    public JFrame FormularioTripulanteNuevo(){
        JFrame frameFrom = new JFrame("Registro de tripulante");
        frameFrom.setTitle("Formulario");
        frameFrom.setSize(400, 350);
        frameFrom.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameFrom.setLayout(new GridBagLayout());
        txtNombre.setColumns(20);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        btnAceptar = new JButton("Aceptar");
        btnAceptar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(!Formato.esNombre(txtNombre.getText().toString())){
                    JOptionPane.showMessageDialog(null,"El nombre tiene carácteres no válidos \n el nombre solo puede tener letras");
                    return;
                }
                if(txtNombre.getText() != ""){
                    if(Formato.guardarDatos("INSERT INTO TRIPULANTE (idtripulante, nombre, estado) "
                            + "VALUES (NULL,'" + txtNombre.getText() + "',1);",conexion,true)){
                        frameFrom.setVisible(false);
                        frameFrom.dispose();
                    }else{
                        JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
                    }
                }
            }
        });
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        frameFrom.add(new JLabel("Nombre:"),gbc);
        gbc.gridy++;
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        frameFrom.add(txtNombre,gbc);
        gbc.gridy++;
        frameFrom.add(btnAceptar,gbc);

        frameFrom.setLocationRelativeTo(frameFrom);
        frameFrom.setVisible(true);
        
        return frameFrom;
    }
    
    public JFrame FormularioActualizaTripulante(Hashtable tripulante){
        
        JFrame panel = new JFrame("Actualiza tripulante");
        panel.setTitle("Formulario");
        panel.setSize(400, 350);
        panel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        panel.setLayout(new GridBagLayout());
        txtNombre = new JTextField(tripulante.get("nombre").toString());
        txtNombre.setColumns(20);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Nombre:"),gbc);
        gbc.gridy++;
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(txtNombre,gbc);
        gbc.gridy++;
        
        JButton guardarButton = new JButton("Guardar");
        guardarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Object idTripulanteObj = tripulante.get("idtripulante");
                if(!Formato.esNombre(txtNombre.getText().toString())){
                    JOptionPane.showMessageDialog(null, "El nombre incluye carácteres no válidos \n debe incluir solo letras");
                    return;
                }
                Formato.guardarDatos("UPDATE TRIPULANTE SET nombre = '" + txtNombre.getText() + "'"
                        + "WHERE IDTRIPULANTE = "+ Integer.parseInt(idTripulanteObj.toString()) +";",conexion,true);
                panel.setVisible(false);
                panel.dispose();
            }
        });
        panel.add(guardarButton,gbc);
        panel.setLocationRelativeTo(panel);
        panel.setVisible(true);
        
        return panel;
    }
    
    public JFrame FormularioAsignarTripulante(Hashtable tripulante, Hashtable naves, Hashtable navestripulantes){
        
        JFrame panel = new JFrame("Asigna tripulante");
        panel.setTitle("Formulario");
        panel.setSize(400, 350);
        panel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        panel.setLayout(new GridBagLayout());
        JComboBox<String> comboBox = convertirAComboBox(naves);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Nave disponible:"),gbc);
        gbc.gridy++;
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(comboBox,gbc);
        gbc.gridy++;
        
        JButton guardarButton = new JButton("Guardar");
        guardarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (comboBox.getSelectedIndex() >= 0) {
                    int indiceSeleccionado = comboBox.getSelectedIndex();
                    String valorSeleccionado = comboBox.getItemAt(indiceSeleccionado);
                    String idSeleccionado = (String) comboBox.getClientProperty(valorSeleccionado);
                    System.out.println("Se ha seleccionado: " + idSeleccionado);
                    Object idTripulanteObj = tripulante.get("idtripulante");
                    if(Formato.guardarDatos("INSERT INTO NAVE_TRIPULANTE (idrelacion, idnave, idtripulante)"
                            +" VALUES (NULL,"+Formato.obtenEntero(idSeleccionado)+","+Formato.obtenEntero(idTripulanteObj.toString())+");", conexion,false)){
                        if(Formato.guardarDatos("UPDATE TRIPULANTE SET ESTADO = 2"
                        + "WHERE IDTRIPULANTE = "+ Integer.parseInt(idTripulanteObj.toString()) +";",conexion,false)){
                            if(navestripulantes.containsKey(idSeleccionado)){
                                Object cantidadT = navestripulantes.get(idSeleccionado);
                                int cantidadFinal = Formato.obtenEntero(cantidadT.toString())+1;
                                String query = "UPDATE NAVE SET TRIPULANTES = "+cantidadFinal+" WHERE IDNAVE = "+idSeleccionado+";";
                                Formato.guardarDatos(query, conexion,true);
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione un vuelo disponible");
                }
                panel.setVisible(false);
                panel.dispose();
            }
        });
        panel.add(guardarButton,gbc);
        panel.setLocationRelativeTo(panel);
        panel.setVisible(true);
        
        return panel;
    }
    
public static JComboBox<String> convertirAComboBox(Hashtable hashtable) {
    JComboBox<String> comboBox = new JComboBox<>();
    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

    Enumeration<String> keys = hashtable.keys();
    while (keys.hasMoreElements()) {
        String key = keys.nextElement();
        String value = hashtable.get(key).toString();
        model.addElement(value);
        comboBox.putClientProperty(value, key); 
    }

    comboBox.setModel(model);

    return comboBox;
}
    
}
