import java.sql.Connection;

public class DatabaseAccess {

  private Connection connection;

  public DatabaseAccess(Connection connection){
    setConnection(connection);
  }

  public void createSong(){

  }

  public void getSong(){

  }

  public void getAllSongs(){

  }

  private void setConnection(Connection connection) {
    this.connection = connection;
  }
}

