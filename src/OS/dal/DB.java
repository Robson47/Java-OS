/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package OS.dal;
import java.sql.*;
/**
 *
 * @author Robson47
 */
public class DB {
    //Método que estabelece a conexão com o banco de dados
    public static Connection connector(){
        java.sql.Connection conn = null;

        String driver = "com.mysql.cj.jdbc.Driver";

        String url = "jdbc:mysql://127.0.0.1:3306/db_os";
        String user = "";
        String password = "";
        
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            return conn;
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao se conectar ao banco de dados: "+ e);
            return null;
        }
    }
}
