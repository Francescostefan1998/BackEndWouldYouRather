
package it.betacom.connection;

import java.sql.*; 
import java.util.Properties;

public class Database {

  private static String url;
  private static String user;
  private static String password;  

  static {
    
    try {

      // Load properties
      Properties prop = new Properties();
      prop.load(Database.class.getClassLoader().getResourceAsStream("dbConf.properties"));

      // Load driver class
      Class.forName("com.mysql.cj.jdbc.Driver");
      
      // Get properties 
      url = prop.getProperty("db.url");
      user = prop.getProperty("db.user");
      password = prop.getProperty("db.password");
      
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(url, user, password); 
  }

}