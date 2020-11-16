import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.hibernate.mapping.Array;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

public class SongsServlet extends HttpServlet {

  private String uriToDB = null;


  private static final String PERSISTENCE_UNIT_NAME = "songdb";
  EntityManager em;


  @Override
  public void init(ServletConfig servletConfig) throws ServletException {
// Datei persistence.xml wird automatisch eingelesen, beim Start der Applikation, einmalig
    HibernatePersistenceProvider hp;
    EntityManagerFactory factory = Persistence. createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
// EntityManager bietet Zugriff auf Datenbank
     em = factory.createEntityManager();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String responseStr = "";
    Enumeration<String> paramNames = req.getParameterNames();
    Map<String, String[]> parameterMap = req.getParameterMap();

    //hier wird geprüft, dass nur ein Parameter übergeben wird
    if(parameterMap.size()!=1){
      responseStr ="Falsche Eingabe (Mehrere Parameter übergeben)!";
    }else {

      if (parameterMap.containsKey("songid")) {

        System.out.println("songID gefunden");
        String[] values = parameterMap.get("songid");

        //hier wird geprueft, dass nur eine SongID übergeben wird
        if (values.length != 1) {
          responseStr = "Falsche Eingabe (Mehrere SongIDs übergeben)!";
        } else {
          String value = values[0];

          Songs song = em.find(Songs.class,Integer.parseInt(value));
          String songJsonString = new Gson().toJson(song);


          responseStr = songJsonString;

        }

      } else if (parameterMap.containsKey("all")) {

        System.out.println("All übergeben!");
        String[] values = parameterMap.get("all");

        //hier wird geprüft, dass "all" nur einmal übergeben wird
        if (values.length != 1) {
          responseStr = "Falsche Eingabe (all mehrfach übergeben))";
        } else {

          List<Songs> songs = em.createQuery("SELECT a FROM Songs a", Songs.class).getResultList();
          ListIterator<Songs> iterator = songs.listIterator();

          StringBuilder stringBuilder = new StringBuilder();
          while (iterator.hasNext()){
            stringBuilder.append(new Gson().toJson(iterator.next())+System.getProperty("line.separator"));
          }
          String songsJsonString = stringBuilder.toString();


          responseStr = songsJsonString;
        }

      }
      System.out.println("Anfrage reingekommen!");
    }
    resp.setContentType("text/plain");
    try (PrintWriter out = resp.getWriter()) {
      out.println(responseStr);
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    super.doPost(req, resp);
  }
}
