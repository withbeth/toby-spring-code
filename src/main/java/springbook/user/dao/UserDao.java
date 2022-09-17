package springbook.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import springbook.user.domain.User;

/**
 * Flow : Get or Create DB Connection Create Object which contains SQL(Statement or
 * PreparedStatement) Execute SQL Convert result object to Domain object (ResultSet) Close all
 * resources(connection, Statement, ResultSet) Handle exceptions which JDBC API throws
 */
public class UserDao {

    // Test without OBJC dynamic loading
    // Try close with resource from Java8
    //  -> try-with-resource block with AutoCloseable implmented object
    //  -> only one statement?
    //  -> NOPE
    // How to convert user to dao?
    //  -> Nope. create sql statement object and map user data
    // Statement vs PreparedStatement
    //  -> Prepared version : more efficient when execute same sql many times because of pre-compile
    public void add(User user) throws ClassNotFoundException, SQLException {

        try (
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                "insert into USERS (id, name, password) values (?, ?, ?)");
        ) {
            statement.setString(1, user.getId());
            statement.setString(2, user.getName());
            statement.setString(3, user.getPassword());

            statement.executeUpdate();
        }
    }

    public User get(String id) throws ClassNotFoundException, SQLException {

        try (
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                "select * from users where id = ?");
        ) {

            statement.setString(1, id);

            try (ResultSet resultSet = statement.executeQuery();) {

                resultSet.next();

                User user = new User();
                user.setId(resultSet.getString("id"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
                return user;

            }
        }
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        return DriverManager.getConnection("jdbc:oracle:thin:@localhost:1522:XE", "system",
            "Nightowl1");
    }

}
