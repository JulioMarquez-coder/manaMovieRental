/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author julio
 */

package database;  

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL  = "jdbc:mysql://localhost:3306/video_store";
    private static final String USER = "root";           
    private static final String PASS = "roccO0802!";  

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
}
}
