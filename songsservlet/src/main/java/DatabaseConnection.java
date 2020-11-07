import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseConnection {

  public static void main(String[] args) {


    Connection connection=null;
    String url ="jdbc:postgresql://ec2-54-75-199-252.eu-west-1.compute.amazonaws.com:5432/dp9r2ed19b21a?" +
      "&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
    Properties props = new Properties();
    String user = "lguzzueocrapoa";
    String password = "f78491f84f9232ce77bc030c7de90ec3a95acf907f10e408c8a965d262638bf8";
    //props.setProperty("ssl","true");

    try{
      Class.forName("org.postgresql.Driver");
      connection = DriverManager.getConnection(url, user, password);
      if(connection!=null){
        System.out.println("Connection established!");
      }else{
        System.out.println("Connection failed!");
      }

    }catch(Exception e){
      e.printStackTrace();
    }
  }
}
