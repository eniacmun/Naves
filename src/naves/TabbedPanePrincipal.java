/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package naves;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Random;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Usuario
 */
public class TabbedPanePrincipal {
    
    Conexion conexion;
    JPanel panelNaves = new JPanel();
    JPanel panelTripulantes = new JPanel();    
    Vector dataNaves = new Vector();
    Vector dataTripulantes = new Vector();
    JTable tablaDeTripulantes;
    JTable tablaDeNaves;
    Vector<String> columnasNaves = new Vector();
    Vector<Vector<String>> filasNaves = new Vector();
    Vector<String> columnasTripulantes = new Vector();
    Vector<Vector<String>> filasTripulantes = new Vector();
    Hashtable NavesInfo = new Hashtable();
    Hashtable NavesDisponibles = new Hashtable();
    Hashtable NavesTripulantes = new Hashtable();
    Hashtable TripulantesInfo = new Hashtable();
    Hashtable TripulantesDisponibles = new Hashtable();
    
    public TabbedPanePrincipal() {
        
        JFrame frame = new JFrame("Información");

        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Naves", panelNaves);
        tabbedPane.addTab("Tripulantes", panelTripulantes);
        
        JButton registerNave = new JButton("Registrar nueva nave");
        registerNave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        
        CargaPanelNaves();
        CargaPanelTripulantes();
        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.setVisible(true);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void CargaPanelNaves() {
        consultaNaves();
        
        panelNaves.setLayout(new BorderLayout());
        
        JButton registerButton = new JButton("Registra nueva nave");
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                NaveNueva();
                actualizarTablaDeNaves();
            }
        });
        
        
        columnasNaves.addElement("Clave nave");
        columnasNaves.addElement("Máximo de tripulantes");
        columnasNaves.addElement("Capacidad máxima");
        columnasNaves.addElement("Tripulantes");
        columnasNaves.addElement("Estado");
        
        
        for (int i = 0; i < dataNaves.size(); i++) {
            Vector<String> fila = new Vector<>();
            fila = procesaNaves(i);
            filasNaves.addElement(fila);
        }
        this.tablaDeNaves = new JTable(filasNaves, columnasNaves);    
        this.tablaDeNaves.getTableHeader().setReorderingAllowed(false);
        this.tablaDeNaves.setRowHeight(40);
        this.tablaDeNaves.setAutoResizeMode(4);
        this.tablaDeNaves.setSelectionMode(0);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int ancho = 0, alto = 0;
        ancho = (int)screenSize.getWidth() * 10 / 100;
        alto = (int)screenSize.getHeight() * 10 / 100;
        this.tablaDeNaves.setPreferredScrollableViewportSize(new Dimension(ancho, alto));
        Dimension d = this.tablaDeNaves.getPreferredScrollableViewportSize();
        JScrollPane scroll = new JScrollPane(this.tablaDeNaves);
        

        JPanel buttonPanel = new JPanel(new GridLayout(0, 2));

        JButton updateButton = new JButton("Actualizar");
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actualizarNave(); 
            }
        });


        JButton deleteButton = new JButton("Eliminar");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarNave();
            }
        });
        
        JButton despegarButton = new JButton("Despegar");
        despegarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                despegarNave();
            }
        });

        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(despegarButton);

        panelNaves.add(registerButton, BorderLayout.NORTH);
        panelNaves.add(scroll, BorderLayout.CENTER);
        panelNaves.add(buttonPanel, BorderLayout.SOUTH);
        
    }
    
    public void CargaPanelTripulantes() {
        consultaTripulantes();
        
        panelTripulantes.setLayout(new BorderLayout());
        
        JButton registerButton = new JButton("Registra nuevo tripulante");
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tripulanteNuevo();
                actualizarTablaDeTripulantes();
            }
        });
        
        
        columnasTripulantes.addElement("ID tripulante");
        columnasTripulantes.addElement("Nombre tripulantes");
        columnasTripulantes.addElement("Estado");
        
        
        for (int i = 0; i < dataTripulantes.size(); i++) {
            Vector<String> fila = new Vector<>();
            fila = procesaTripulantes(i);
            filasTripulantes.addElement(fila);
        }
        this.tablaDeTripulantes = new JTable(filasTripulantes, columnasTripulantes);    
        this.tablaDeTripulantes.getTableHeader().setReorderingAllowed(false);
        this.tablaDeTripulantes.setRowHeight(40);
        this.tablaDeTripulantes.setAutoResizeMode(4);
        this.tablaDeTripulantes.setSelectionMode(0);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int ancho = 0, alto = 0;
        ancho = (int)screenSize.getWidth() * 10 / 100;
        alto = (int)screenSize.getHeight() * 10 / 100;
        this.tablaDeTripulantes.setPreferredScrollableViewportSize(new Dimension(ancho, alto));
        Dimension d = this.tablaDeTripulantes.getPreferredScrollableViewportSize();
        JScrollPane scroll = new JScrollPane(this.tablaDeTripulantes);
        

        JPanel buttonPanel = new JPanel(new GridLayout(0, 2));

        JButton updateButton = new JButton("Actualizar");
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actualizarTripulante(); 
            }
        });


        JButton deleteButton = new JButton("Eliminar");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                eliminarTripulante();
            }
        });
        
        JButton asignaButton = new JButton("Asignar vuelo");
        asignaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                asignarTripulante();
            }
        });

        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(asignaButton);

        panelTripulantes.add(registerButton, BorderLayout.NORTH);
        panelTripulantes.add(scroll, BorderLayout.CENTER);
        panelTripulantes.add(buttonPanel, BorderLayout.SOUTH);
        
    }
    
    public void consultaNaves(){
        this.conexion = new Conexion();
        
        if(!this.conexion.desconectado() && this.conexion != null){
            try{
                dataNaves = conexion.getSelectV("Select * from nave where estado != 'BAJA' order by idnave asc;");
            }catch(Exception e){
                System.out.print(e);            
            }
        }
    }
    
    public void consultaTripulantes(){
        this.conexion = new Conexion();
        
        if(!this.conexion.desconectado() && this.conexion != null){
            try{
                dataTripulantes = conexion.getSelectV("Select * from tripulante where estado != 0 order by idtripulante asc;");
            }catch(Exception e){
                System.out.print(e);            
            }
        }
    }
    
    public Vector<String> procesaNaves(int i){
        
        Hashtable hnave = new Hashtable();
        hnave = (Hashtable) dataNaves.get(i);
        String idnave = hnave.get("idnave").toString();
        String nombre = hnave.get("codigo").toString();
        String maximo = hnave.get("maximo").toString();
        String tripulantes = hnave.get("tripulantes").toString();
        String estado = hnave.get("estado").toString();

        Vector<String> navescarga = new Vector<>();
        navescarga.addElement(idnave);
        navescarga.addElement(nombre);
        navescarga.addElement(maximo);
        navescarga.addElement(tripulantes);
        navescarga.addElement(estado);
        NavesInfo.put(idnave, tripulantes);
        if(estado.equals("EN ESPERA") && Formato.obtenEntero(tripulantes)<Formato.obtenEntero(maximo)){
            NavesDisponibles.put(idnave, nombre);
            NavesTripulantes.put(idnave, Formato.obtenEntero(tripulantes));
        }
        if(Formato.obtenEntero(tripulantes)==Formato.obtenEntero(maximo) && NavesDisponibles.containsKey(idnave)){
            if (Formato.guardarDatos("UPDATE NAVE SET ESTADO = 'CUPO LLENO' WHERE IDNAVE = "+Formato.obtenEntero(idnave)+" ;", conexion,false)) {
                System.out.println("Nave llena, se actualiza estado");
                NavesDisponibles.remove(idnave);
            }
        }
        return navescarga;
    }
    
    public Vector<String> procesaTripulantes(int i){
        
        Hashtable htripulantes = new Hashtable();
        htripulantes = (Hashtable) dataTripulantes.get(i);
        String idtripulante = htripulantes.get("idtripulante").toString();
        String nombre = htripulantes.get("nombre").toString();
        String estado = htripulantes.get("estado").toString();
        String estadoString = Formato.estadoString(estado);

        Vector<String> tripulantescarga = new Vector<>();
        tripulantescarga.addElement(idtripulante);
        tripulantescarga.addElement(nombre);
        tripulantescarga.addElement(estadoString);
        TripulantesInfo.put(idtripulante, nombre);
        if(estado.equals("1")){
            TripulantesDisponibles.put(idtripulante, nombre);
        }
        return tripulantescarga;
    }
    
    public void actualizarTablaDeNaves() {
        consultaNaves();
        filasNaves.clear();
        for (int i = 0; i < dataNaves.size(); i++) {
            Vector<String> fila = new Vector<>();
            fila = procesaNaves(i);
            filasNaves.addElement(fila);
        }
        DefaultTableModel model = (DefaultTableModel) tablaDeNaves.getModel();
        model.setDataVector(filasNaves, columnasNaves);
    }
    
    public void actualizarTablaDeTripulantes() {
        consultaTripulantes();
        filasTripulantes.clear();
        for (int i = 0; i < dataTripulantes.size(); i++) {
            Vector<String> fila = new Vector<>();
            fila = procesaTripulantes(i);
            filasTripulantes.addElement(fila);
        }

        DefaultTableModel model = (DefaultTableModel) tablaDeTripulantes.getModel();
        model.setDataVector(filasTripulantes, columnasTripulantes);
    }
    
    public void NaveNueva() {
        Random rand = new Random();
        int posicionLetra = rand.nextInt(4);
        
        if (Formato.guardarDatos("INSERT INTO NAVE (idnave,codigo, maximo, tripulantes, estado) "
                + "VALUES (NULL,'NAV" + Formato.numeroConLetra(posicionLetra, rand) + "', " + 3 + ", " + 0 + ", 'EN ESPERA');", conexion,true)) {
        } else {
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
        }
    }
    
    private void actualizarNave() {
        int filaSeleccionada = tablaDeNaves.getSelectedRow();
        if (filaSeleccionada != -1) {
            Hashtable datosFila = (Hashtable) dataNaves.get(filaSeleccionada);
            FormularioNave window = new FormularioNave();
            JFrame newWindow = window.FormularioActualizarNave(datosFila);
            newWindow.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                actualizarTablaDeNaves(); // Llama al método solo después de que se cierre la ventana de actualización
            }
        });
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona una fila para actualizar");
        }
    }
    
    private void actualizarTripulante() {
        int filaSeleccionada = tablaDeTripulantes.getSelectedRow();
        if (filaSeleccionada != -1) {
            Hashtable datosFila = (Hashtable) dataTripulantes.get(filaSeleccionada);
            FormularioTripulante window = new FormularioTripulante();
            JFrame newWindow = window.FormularioActualizaTripulante(datosFila);
            newWindow.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                actualizarTablaDeTripulantes();
            }
        });
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona una fila para actualizar");
        }
    }
    
    private void eliminarNave() {
        int filaSeleccionada = tablaDeNaves.getSelectedRow();
        if (filaSeleccionada != -1) {
            Hashtable<String, String> datosFila = (Hashtable<String, String>) dataNaves.get(filaSeleccionada);
            Object idNaveObj = datosFila.get("idnave");
            this.conexion.getUpdate("UPDATE NAVE SET ESTADO = 'BAJA' WHERE IDNAVE = "+ Integer.parseInt(idNaveObj.toString()) +";");
            actualizarTablaDeNaves();
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona una fila para actualizar");
        }
    }
    
    private void eliminarTripulante() {
        int filaSeleccionada = tablaDeTripulantes.getSelectedRow();
        if (filaSeleccionada != -1) {
            Hashtable<String, String> datosFila = (Hashtable<String, String>) dataTripulantes.get(filaSeleccionada);
            Object idTripulanteObj = datosFila.get("idtripulante");
            this.conexion.getUpdate("UPDATE TRIPULANTE SET ESTADO = 0 WHERE IDTRIPULANTE = "+ Integer.parseInt(idTripulanteObj.toString()) +";");
            actualizarTablaDeTripulantes();
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona una fila para actualizar");
        }
    }
    
    private void despegarNave() {
        int filaSeleccionada = tablaDeNaves.getSelectedRow();
        if (filaSeleccionada != -1) {
            Hashtable<String, String> datosFila = (Hashtable<String, String>) dataNaves.get(filaSeleccionada);
            Object idNaveObj = datosFila.get("idnave");
            Object tripulantesObj = datosFila.get("tripulantes");
            Object estadoObj = datosFila.get("tripulantes");
            int cantidad = Formato.obtenEntero(tripulantesObj.toString());
            if(cantidad > 0){
                if(estadoObj.equals("EN VIAJE")){
                    this.conexion.getUpdate("UPDATE NAVE SET ESTADO = 'EN VIAJE' WHERE IDNAVE = "+ Integer.parseInt(idNaveObj.toString()) +";");
                    actualizarTablaDeNaves();
                }else{
                    JOptionPane.showMessageDialog(null, "La nave seleccioanda ya está en viaje");
                    return;
                }
            }else {
                JOptionPane.showMessageDialog(null, "La nave no puede despegar sin tripulantes");
            }
            actualizarTablaDeNaves();
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona una fila para actualizar");
        }
    }
    
    private void tripulanteNuevo() {
            FormularioTripulante window = new FormularioTripulante();
            JFrame newWindow = window.FormularioTripulanteNuevo();
            newWindow.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                actualizarTablaDeTripulantes(); // Llama al método solo después de que se cierre la ventana de actualización
            }
        });
    }
    
    private void asignarTripulante() {
        int filaSeleccionada = tablaDeTripulantes.getSelectedRow();
        if (filaSeleccionada != -1) {
            Hashtable<String, String> datosFila = (Hashtable<String, String>) dataTripulantes.get(filaSeleccionada);
            Object estadoString = datosFila.get("estado");
            if(estadoString.toString().equals("2")){
                JOptionPane.showMessageDialog(null, "El tripulante ya está en una nave");
                return;
            }
            FormularioTripulante window = new FormularioTripulante();
            JFrame newWindow = window.FormularioAsignarTripulante(datosFila,NavesDisponibles,NavesTripulantes);
            newWindow.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                    actualizarTablaDeTripulantes();
                    actualizarTablaDeNaves();
                }
            });
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona una fila para actualizar");
        }
    }
    
}
