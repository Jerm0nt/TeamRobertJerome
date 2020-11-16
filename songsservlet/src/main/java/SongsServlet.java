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
import java.util.stream.Collectors;

public class SongsServlet extends HttpServlet {

  private String uriToDB = null;


  private static final String PERSISTENCE_UNIT_NAME = "songdb";
  EntityManager em;


  @Override
  public void init(ServletConfig servletConfig) throws ServletException {
// Datei persistence.xml wird automatisch eingelesen, beim Start der Applikation, einmalig
    HibernatePersistenceProvider hp;
    EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
// EntityManager bietet Zugriff auf Datenbank
     em = factory.createEntityManager();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String responseStr = "";
    Enumeration<String> paramNames = req.getParameterNames();
    Map<String, String[]> parameterMap = req.getParameterMap();
    StringBuilder stringBuilder = new StringBuilder();


    //hier wird geprüft, dass nur ein Parameter übergeben wird
    if(parameterMap.size()!=1){
      resp.setStatus(400);
      responseStr = "HTTP/1.1 400 Bad Request"+System.getProperty("line.separator")
        +"Bitte übergeben sie eine 'SongID' oder 'All'.";
    }
    else {

      if (parameterMap.containsKey("songid")) {

        String[] values = parameterMap.get("songid");
        //hier wird geprueft, dass nur eine SongID übergeben wird
        if (values.length != 1) {
          resp.setStatus(400);
          responseStr = "HTTP/1.1 400 Bad Request"+System.getProperty("line.separator")
            +"Bitte übergeben sie genau eine 'SongID'.";
        }
        else {
          String value = values[0];
          Songs song = em.find(Songs.class,Integer.parseInt(value));
          if(song==null){
            //falls angefragte id nicht vorhanden
            resp.setStatus(404);
            responseStr = "HTTP/1.1 404 Not Found"+System.getProperty("line.separator")
              +"Die übergebene 'SongID' konnte nicht gefunden werden.";
          }else{
            //falls angefragte id vorhanden
            resp.setStatus(200);
            resp.setHeader("Content-Type","application/json");
           // resp.setHeader("X-Content-Type-Options","nosniff");
            stringBuilder.append(new Gson().toJson(song));
            responseStr = stringBuilder.toString();
          }
        }

      } else if (parameterMap.containsKey("all")) {

        System.out.println("All übergeben!");
        String[] values = parameterMap.get("all");

        //hier wird geprüft, dass "all" nur einmal übergeben wird
        if (values.length != 1) {
          resp.setStatus(400);
          responseStr = "HTTP/1.1 400 Bad Request"+System.getProperty("line.separator")
            +"Bitte übergeben sie 'All' nur einmal.";
        } else {
          if(values[0]!=""){
            resp.setStatus(400);
            responseStr = "HTTP/1.1 400 Bad Request"+System.getProperty("line.separator")
              +"Bitte übergeben sie 'All' ohne wert.";
          }else{

            //hole Liste mit allen gespeicherten Songs
            List<Songs> songs = em.createQuery("SELECT a FROM Songs a", Songs.class).getResultList();

            //falls keine Songs vorhanden
            if(songs==null){
              resp.setStatus(404);
              responseStr = "HTTP/1.1 404 Not Found"+System.getProperty("line.separator")
                +"Es konnten keine Songs gefunden werden.";
            }
            else {
              resp.setStatus(200);
              resp.setHeader("Content-Type","application/json");
              ListIterator<Songs> iterator = songs.listIterator();
              while (iterator.hasNext()) {
                stringBuilder.append(new Gson().toJson(iterator.next()) + System.getProperty("line.separator"));
              }
              responseStr = stringBuilder.toString();
            }
          }
        }
      }else{
        //falls falscher Parameter übergeben
        resp.setStatus(400);
        responseStr = "HTTP/1.1 400 Bad Request"+System.getProperty("line.separator")
          +"Parameter nicht zulässig!";
      }
      System.out.println("Anfrage reingekommen!");
    }
    //resp.setContentType("text/plain");
    try (PrintWriter out = resp.getWriter()) {
      out.println(responseStr);
    }
  }


  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    Gson gson = new Gson();
    if(req.getHeader("Content-Type").equals("application/json")){
      String body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
      if(isJSONValid(body)){
        Songs song = gson.fromJson(body, Songs.class);
        try{
          em.getTransaction().begin();
          em.persist(song);
          em.getTransaction().commit();
          //em.flush();
          //int id = song.getId();
          resp.setStatus(200);
          resp.setHeader("Location", "http://localhost:8080/songsservlet_war/songs?songid=");
        } catch (Exception ex) { //Aus Platzgründen, besser jede Exception einzeln fangen
          em.getTransaction().rollback();
          ex.printStackTrace();
        }
      }

    }
    else{
      resp.setStatus(406);
    }
  }

  private static boolean isJSONValid(String jsonInString) {
    try {
      Gson gson = new Gson();
      gson.fromJson(jsonInString, Object.class);
      return true;
    } catch(com.google.gson.JsonSyntaxException ex) {
      return false;
    }
  }
}
