import java.sql.SQLException;

public class Main {

  private static final String user = "lguzzueocrapoa";
  private static final String password = "f78491f84f9232ce77bc030c7de90ec3a95acf907f10e408c8a965d262638bf8";
  private static final String url ="jdbc:postgresql://ec2-54-75-199-252.eu-west-1.compute.amazonaws.com:5432/dp9r2ed19b21a?" +
    "&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
  private static final String driver = "org.postgresql.Driver";
  private static final DatabaseConnection databaseConnection = new DatabaseConnection(user, password, url, driver);
  private static final DatabaseAccess databaseAccess = new DatabaseAccess(databaseConnection.getConnection());

  public static void main(String[] args) throws SQLException {
    databaseConnection.enableConnection();


    databaseConnection.closeConnection();
  }
}
