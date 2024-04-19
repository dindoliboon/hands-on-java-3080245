package bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataSource {
  public static Connection connect() {
    String dbFile = "jdbc:sqlite:resources/bank.db";
    Connection connection = null;

    try {
      connection = DriverManager.getConnection(dbFile);
      System.out.println("Connected to " + dbFile);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return connection;
  }

  public static Customer getCustomer(String username) {
    String sql = "select * from Customers where username = ?";
    Customer customer = null;

    try (Connection connection = connect();
        PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setString(1, username);

      try (ResultSet resultSet = statement.executeQuery()) {
        customer = new Customer(
            resultSet.getInt("id"),
            resultSet.getString("name"),
            resultSet.getString("username"),
            resultSet.getString("password"),
            resultSet.getInt("account_Id"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return customer;
  }

  public static void main(String[] args) {
    Customer customer = getCustomer("twest8o@friendfeed.com");
    System.out.println(customer.getName());
  }
}
