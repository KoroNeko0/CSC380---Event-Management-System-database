import java.sql.*;
public class MySQLConnection{
	
    private static final String URL = "jdbc:mysql://localhost:3306/event";
    private static final String USER = "root";
    private static final String PASSWORD = "Maha123123";
    private static Connection connection = null;
    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connected to database.");
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }//end getConnection
    public static void closeConnection(ResultSet rs, PreparedStatement stmt, Connection conn) {
        try {
            if (rs != null) rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (stmt != null) stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                connection = null; // Allow reconnect later
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//end closeConnection
}//end class