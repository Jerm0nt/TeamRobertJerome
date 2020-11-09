import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

  private String user;
  private String password;
  private String url;
  private Connection connection;
  private String driver;

  public DatabaseConnection(String user, String password, String url, String driver){
    setUser(user);
    setPassword(password);
    setUrl(url);
    setDriver(driver);
  }

  public void enableConnection() throws SQLException {
    try{
      Class.forName(driver);
      connection = DriverManager.getConnection(url, user, password);
      if(connection!=null){
        System.out.println("Connection established!");
      }else{
        System.out.println("Connection failed!");
      }
    }catch(Exception e) {
      e.printStackTrace();
    }
  }

  public void closeConnection() throws SQLException {
    connection.close();
    if(connection.isClosed()){
      System.out.println("Connection closed!");
    }else{
      System.out.println("Connection still established!");
    }
  }

  private void setUser(String user) {
    this.user = user;
  }

  private void setPassword(String password) {
    this.password = password;
  }

  private void setUrl(String url) {
    this.url = url;
  }

  public void setDriver(String driver) {
    this.driver = driver;
  }

  public Connection getConnection() {
    return connection;
  }
}
