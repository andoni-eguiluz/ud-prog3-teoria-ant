package ud.prog3.cap03;

import java.sql.*;
import java.util.ArrayList;

/**  Prueba de inserción SQL con caracteres especiales.
 * Siempre es más seguro el preparedstatement y necesita menos proceso
 */
public class TestCaracteresEscSQL {
	
	private static String safeSQL( String string ) {
		return "'" + string.replaceAll( "'",  "''" ) + "'";
	}
	
	public static void main(String[] args) {
		String string = "Prueba\nSegunda línea\nTercera con\ttab\nCuarta con 'comillas'\nQuinta con \"dobles comillas\"";
		try {
			Class.forName( "org.sqlite.JDBC" );
			Connection con = DriverManager.getConnection( "jdbc:sqlite:prueba-escs.bd" );
			Statement stat = con.createStatement();
			try {
				stat.executeUpdate( "create table strings (valor string)");
			} catch (SQLException e) {}
			stat.executeUpdate( "delete from strings" );

			String sql = "insert into strings values(" + safeSQL(string) + ")";
			System.out.println( sql );
		    stat.executeUpdate( sql );
		    System.out.println();

		    sql = "INSERT INTO strings VALUES (?)";
		    PreparedStatement prepStat = con.prepareStatement(sql);
		    prepStat.setString( 1, string );
			System.out.println( "prepared statement: " + prepStat.toString() );
		    prepStat.execute();
		    System.out.println();
		    
			ResultSet rs = stat.executeQuery("select * from strings");
			while (rs.next())
			{
				System.out.println( "Valor leído: \n" + rs.getString( "valor" ) );
			    System.out.println();
			}
			rs.close();
		    
			stat.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
