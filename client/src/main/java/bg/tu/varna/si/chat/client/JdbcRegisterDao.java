package bg.tu.varna.si.chat.client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcRegisterDao {

		private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/USERS";
	    private static final String DATABASE_USERNAME = "root";
	    private static final String DATABASE_PASSWORD = "Estatiev95";
	    private static final String INSERT_QUERY = "INSERT INTO registration (firstName, lastName, userName, password) VALUES (?, ?, ?, ?)";


	    public void insertRecord(String firstName,String lastName, String userName, String password) throws SQLException {

	        // Step 1: Establishing a Connection and 
	        // try-with-resource statement will auto close the connection.
	        try (Connection connection = DriverManager
	            .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

	            // Step 2:Create a statement using connection object
	            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
	        	//preparedStatement.addUserEntity(1, firstName);
	            preparedStatement.setString(1, firstName);
	            preparedStatement.setString(2, lastName);
	            preparedStatement.setString(3, userName);
	            preparedStatement.setString(4, password);

	            System.out.println(preparedStatement);
	            // Step 3: Execute the query or update query
	            preparedStatement.executeUpdate();
	        } catch (SQLException e) {
	            // print SQL exception information
	            printSQLException(e);
	        }
	    }

	    public static void printSQLException(SQLException ex) {
	        for (Throwable e: ex) {
	            if (e instanceof SQLException) {
	                e.printStackTrace(System.err);
	                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
	                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
	                System.err.println("Message: " + e.getMessage());
	                Throwable t = ex.getCause();
	                while (t != null) {
	                    System.out.println("Cause: " + t);
	                    t = t.getCause();
	                }
	            }
	        }
	    }
	}
