package ca.jrvs.apps.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataConnectionManager{
   private String url;
   private Properties properties;

   public DataConnectionManager(String host_name , String database_name, String username, String password){
       this.url = "jdbc:postgresql://"+host_name+"/"+database_name;
       this.properties = new Properties();
       this.properties.setProperty("user",username);
       this.properties.setProperty("password",password);
   }

   public Connection getConnection() throws SQLException, ClassNotFoundException {
       //Class.forName("com.example.jdbc.Driver");
       return DriverManager.getConnection(this.url,this.properties);
   }

   /*
   public static void main(String[] args){
       System.out.println("I am here!");
   }
*/
}