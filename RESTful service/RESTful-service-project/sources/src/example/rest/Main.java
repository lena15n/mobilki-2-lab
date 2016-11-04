package example.rest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        try{
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/lena", "sa", "admin");
            Statement st = connection.createStatement();
            ResultSet r = st.executeQuery("select * from pawn");//create table pawn(name varchar(20)) ");
            System.out.println("sokiable");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
