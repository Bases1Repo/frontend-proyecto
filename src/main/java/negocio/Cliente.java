/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author asus
 */
public class Cliente {
    public String agregarRepresentante(Connection conn, String nombres, String apellidos, String correo, String fecha_nacimiento) throws SQLException {

        PreparedStatement pstUser = null;
        PreparedStatement pstRole = null;
        PreparedStatement pstUsuario = null;

        
        
        
        String query = "select * from INTERFAZ.CLIENTE";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        int cantidad=201;
        while (rs.next()) {
           cantidad++;
        }
        String usuario="";
        String nombreArray[] = nombres.split("");
        usuario=nombreArray[0]+nombreArray[1];
        
        String apellidoArray[] = apellidos.split("");
        usuario+=apellidoArray[apellidoArray.length-3]+apellidoArray[apellidoArray.length-2]+apellidoArray[apellidoArray.length-1];
        usuario+=cantidad;
          
        
        System.out.println(usuario+"*******************************************");
        
        
        
        String sqlUser = "CREATE USER " + usuario + " IDENTIFIED  BY \"" + usuario+"\"";
        String sqlRole = "GRANT CONNECT TO " + usuario;
        String sqlUsuario = "INSERT INTO INTERFAZ.CLIENTE VALUES('" + nombres + "','" + apellidos + "',TO_TIMESTAMP('" + fecha_nacimiento + "'),'" + correo +"','"+usuario+"')";
        
       

        System.out.println(sqlUser);
        System.out.println(sqlUsuario);
        
        try {
            pstUser = conn.prepareStatement(sqlUser);
            pstUser.execute();
            pstUser.close();

            pstRole = conn.prepareStatement(sqlRole);
            pstRole.execute();
            pstRole.close();

            pstUsuario = conn.prepareStatement(sqlUsuario);
            pstUsuario.execute();
            pstUsuario.close();


            return usuario;

        } catch (SQLException e) {

            PreparedStatement pstRollback = null;
            String sqlRollback = "ROLLBACK"; 
            pstRollback = conn.prepareStatement(sqlRollback);
            pstRollback.execute();
            pstRollback.close();

            System.out.println(e);
            return e.getMessage();
        }
//        return cantidad+" ";
    }
    
    public static void cambiarContrasena(String usuario,String contrasena, String confirmacion){
        
        Connection conn = Conexion.getConnection();
        if(contrasena.equals(confirmacion)){
            try {
                PreparedStatement pstConfirmar = null;
                String sqlConfirmar = "ALTER USER " + usuario + " IDENTIFIED  BY \"" + contrasena+"\"";
                pstConfirmar = conn.prepareStatement(sqlConfirmar);
                pstConfirmar.execute();
                pstConfirmar.close();
                System.out.println("Contraseña confirmada");
            } catch (SQLException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    
}
