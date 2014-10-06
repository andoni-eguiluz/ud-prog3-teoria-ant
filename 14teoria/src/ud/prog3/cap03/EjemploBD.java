package ud.prog3.cap03;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/** Ejemplo de uso de Base de Datos desde Java con JDBC
 * Utilizando sqlite - debe incluirse la librer�a sqlite-jdbc*.jar
 * @author Andoni Egu�luz Mor�n
 * Facultad de Ingenier�a - Universidad de Deusto
 */
public class EjemploBD {

  public static void main(String[] args) throws ClassNotFoundException
  {
    // Carga el sqlite-JDBC driver usando el cargador de clases
    Class.forName("org.sqlite.JDBC");

    Connection connection = null;
    try
    {
      // Crear una conexi�n de BD
      connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
      Statement statement = connection.createStatement();
      statement.setQueryTimeout(30);  // poner timeout 30 msg

      statement.executeUpdate("drop table if exists person");
      statement.executeUpdate("create table person (id integer, name string)");
      statement.executeUpdate("insert into person values(1, 'leo')");
      statement.executeUpdate("insert into person values(2, 'yui')");
      ResultSet rs = statement.executeQuery("select * from person");
      while(rs.next())
      {
        // Leer el resultset
        System.out.println("name = " + rs.getString("name"));
        System.out.println("id = " + rs.getInt("id"));
      }
    } catch(SQLException e) {
      System.err.println(e.getMessage());
    } finally {
      try
      {
        if(connection != null)
          connection.close();
      }
      catch(SQLException e)
      {
        // Cierre de conexi�n fallido
        System.err.println(e);
      }
    }
  }
  
}