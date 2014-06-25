import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBConnection {
	public static Connection createConnection(){
		
		String jdbcURL = "jdbc:oracle:thin:@localhost:1521:XE";
		String user = "system";
		String password = "password";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Connection connection = null;
		Statement statement = null;
		ResultSet result = null;
		            
		try {
			connection =  DriverManager.getConnection(jdbcURL, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;            
	}
}
