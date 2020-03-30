package app;

import com.mysql.cj.jdbc.MysqlDataSource;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class Database {

    static Connection con;

    private static MysqlDataSource dataSource;

     static void InitializeData() {

        dataSource = new MysqlDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("zxcvbnm@123");
        Connection con = getConnection();
        try {
            Statement st0 = con.createStatement();
            st0.execute("CREATE USER IF NOT EXISTS 'Quiz'@'localhost' IDENTIFIED BY 'password';");

            Statement st1 = con.createStatement();
            st1.execute("GRANT ALL PRIVILEGES ON * . * TO 'Quiz'@'localhost';");
            dataSource.setUser("Quiz");

            Statement st2 = con.createStatement();
            st2.execute("CREATE DATABASE IF NOT EXISTS QUIZDATA");

            Statement st3 = con.createStatement();
            st3.execute("USE QUIZDATA");
            dataSource.setDatabaseName("QUIZDATA");

            Statement st4 = con.createStatement();
            st4.execute("CREATE TABLE IF NOT EXISTS Subject1(Id INT AUTO_INCREMENT PRIMARY KEY, Question VARCHAR(5000), Option1  VARCHAR(500), Option2 VARCHAR(500), Option3 VARCHAR(500), Option4 VARCHAR(500), Answer VARCHAR(500));");

            Statement st5 = con.createStatement();
            st5.execute("CREATE TABLE IF NOT EXISTS Subject2(Id INT AUTO_INCREMENT PRIMARY KEY, Question VARCHAR(5000), Option1  VARCHAR(500), Option2 VARCHAR(500), Option3 VARCHAR(500), Option4 VARCHAR(500), Answer VARCHAR(500));");

            Statement st6 = con.createStatement();
            st6.execute("CREATE TABLE IF NOT EXISTS Subject3(Id INT AUTO_INCREMENT PRIMARY KEY, Question VARCHAR(5000), Option1  VARCHAR(500), Option2 VARCHAR(500), Option3 VARCHAR(500), Option4 VARCHAR(500), Answer VARCHAR(500));");

            Statement st7 = con.createStatement();
            st7.execute("CREATE TABLE IF NOT EXISTS Subject4(Id INT AUTO_INCREMENT PRIMARY KEY, Question VARCHAR(5000), Option1  VARCHAR(500), Option2 VARCHAR(500), Option3 VARCHAR(500), Option4 VARCHAR(500), Answer VARCHAR(500));");

            Statement st8 = con.createStatement();
            st8.execute("CREATE TABLE IF NOT EXISTS Namess(Id INT, Sub1 VARCHAR(50), Sub2 VARCHAR(50), Sub3 VARCHAR(50), Sub4 VARCHAR(50));");

            Statement st9 = con.createStatement();
            ResultSet rs = st9.executeQuery("SELECT Id FROM Namess;");
            if(!rs.next())
            {
                Statement st10 = con.createStatement();
                st10.execute("INSERT INTO Namess(Id) VALUE(1);");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Connection getConnection()
    {
        con = null;
        try
        {
            con = dataSource.getConnection();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return con;
    }
}
