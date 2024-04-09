package naves;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Properties;
import java.util.Vector;
import java.util.Hashtable;
/**
 *
 * @author Usuario
 */
public class Conexion {

    Properties props = new Properties();
    public Connection conn = null;
    public int status = -1;

    public Conexion() {
        getConexion();
    }

    public void getConexion() {
        try {
            props.load(new FileInputStream(new File("conexiones.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url = props.getProperty("db.url");
        String username = props.getProperty("db.username");
        String password = props.getProperty("db.password");
        Properties info = new Properties();
        info.put("user", username);
        info.put("password", password);
        String Driver = "oracle.jdbc.OracleDriver";
        getConnect(Driver, url, info);
    }

    private void getConnect(String Driver, String url, Properties info) {
        try {
            Class.forName(Driver);
            this.conn = DriverManager.getConnection(url, info);
            this.conn.setAutoCommit(false);
            this.status = 0;
        } catch (Exception e) {
            e.printStackTrace();
            this.status = -1;
        }
    }

    public Vector getSelectV(String query) {
        System.out.println(query);
        ResultSet results = null;
        ResultSetMetaData meta = null;
        Statement stat = null;
        Vector<Hashtable<Object, Object>> v = new Vector();
        if (this.status != 0) {
            error(this.status);
            return v;
        }
        try {
            stat = this.conn.createStatement();
            String query1 = query.substring(0, query.indexOf(';'));
            results = stat.executeQuery(query1);
            meta = results.getMetaData();
            while (results.next()) {
                Hashtable<Object, Object> h = new Hashtable<>();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    Object ob = results.getObject(i);
                    if (ob == null) {
                        h.put(meta.getColumnLabel(i).toLowerCase(), new String(""));
                    } else {
                        if (ob.getClass().getName().indexOf("BLOB") >= 0) {
                            Blob bin = (Blob) ob;
                            int size = (int) bin.length();
                            ob = bin.getBytes(1L, size);
                        }
                        if (ob.getClass().getName().indexOf("CLOB") >= 0) {
                            Clob bin = (Clob) ob;
                            int size = (int) bin.length();
                            ob = bin.getSubString(1L, size);
                        }
                        h.put(meta.getColumnLabel(i).toLowerCase(), ob);
                    }
                }
                v.addElement(h);
            }
            stat.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        v.trimToSize();
        return v;
    }
    
    public void getUpdate(String query) {

        Statement stat = null;
        if (this.status != 0) {
            error(this.status);
            return;
        }
        try {
            stat = this.conn.createStatement();
            String query1 = query.substring(0, query.indexOf(';'));
            stat.executeUpdate(query1);
            this.conn.commit();
            stat.close();
        } catch (Exception e) {
            endErrorConnect();
            System.out.println("Query del error: " + query);
            System.out.println("printStackTrace: ");
            e.printStackTrace();
            StringWriter writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            e.printStackTrace(printWriter);
            printWriter.flush();
            String stackTrace = writer.toString();
            System.out.println("ERROR DE BD2: " + e.getMessage());
            System.out.println("ERROR DE BD3: " + e.toString());
            System.out.println(e.toString());
        }
    }

    private void error(int error) {
        switch (error) {

        }
    }
    
    public void endErrorConnect() {
        if (this.status != 0) {
            error(this.status);
            return;
        }
        try {
            this.conn.close();
            this.conn = null;
            this.status = -1;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public boolean desconectado() {
        if (this.status != 0 || this.conn == null) {
            return true;
        } 
        return false;
    }
}
