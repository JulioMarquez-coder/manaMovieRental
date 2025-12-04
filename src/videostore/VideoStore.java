/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package videostore;

/**
*
 * Group Project: COMP-4400 - SYSTEMS DEVELOPMENT N IMPLEMEN
 * Participants: 
 * -Leandra Dorta; A00619185 Sección: 92065
 * -Julio Marquez Torres; A00622314 Sección: 75813
 * -Isaiah Vega Rodríguez; A00618913 Sección: 92065
 *
 * Usuario ADMIN: 
 *   - usuario: admin
 *   - password: admin123
 * 
 *
 *
 * @author julio
*
* 
 */
import Forms.LoginForm;
import database.DBConnection;
import java.sql.Connection;


public class VideoStore {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Connection cn = DBConnection.getConnection();
            System.out.println("Coneccion correcta a la base de datos!");
            cn.close();
        } catch (Exception e) {
            System.out.println("Error al conectar:");
            e.printStackTrace();
        }
        
        
        java.awt.EventQueue.invokeLater(new Runnable(){
            public void run() {
                new LoginForm().setVisible(true);
            }
        });
    }
    
}
