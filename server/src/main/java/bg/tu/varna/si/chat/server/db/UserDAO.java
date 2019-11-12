package bg.tu.varna.si.chat.server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import bg.tu.varna.si.chat.server.db.entity.UserEntity;

public class UserDAO {

	private static Map<String, UserEntity> users = createUsers();

	private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/USERS";

	private static final String DATABASE_USERNAME = "root";

	private static final String DATABASE_PASSWORD = "Estatiev95";

	private static final String INSERT_QUERY = "INSERT INTO account (userName,firstName, lastName, displayName, password) VALUES (?, ?, ?, ?, ?)";

	private static UserDAO INSTANCE_HOLDER;

	private UserDAO() {

	}

	public static UserDAO getInstance() {
		if (INSTANCE_HOLDER == null) {
			INSTANCE_HOLDER = new UserDAO();
		}

		return INSTANCE_HOLDER;
	}

	public void insertRecord(String userName,String firstName, String lastName, String displayName, String password) throws SQLException {
		// Step 1: Establishing a Connection and
		// try-with-resource statement wil auto close the connection.
		try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
			// preparedStatement.addUserEntity(1, firstName);
			preparedStatement.setString(1, userName);
			preparedStatement.setString(2, firstName);
			preparedStatement.setString(3, lastName);
			preparedStatement.setString(4, displayName);
			preparedStatement.setString(5, password);

			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// print SQL exception information
			printSQLException(e);
		}

	}

	public Collection<UserEntity> getAllUsers() {
		return users.values();
	}

	public UserEntity getUserEntity(String username) {
		return users.get(username);
	}

	public void addUserEntity(UserEntity userEntity) {
		users.put(userEntity.getUserName(), userEntity);
	}

	public static Map<String, UserEntity> createUsers() {
		// UserEntity user1 = new UserEntity("user1", "Loo", "Smith", "Popi", "111");
		// UserEntity user2 = new UserEntity("user2", "Doo", "Boo", "PPP", "222");
		// UserEntity user3 = new UserEntity("user3", "Dee", "Fee", "Fee", "333");

		Map<String, UserEntity> users = new HashMap<String, UserEntity>();

		// users.put(user1.getUserName(), user1);
		// users.put(user2.getUserName(), user2);
		// users.put(user3.getUserName(), user3);

		return users;
	}

	public static void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
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