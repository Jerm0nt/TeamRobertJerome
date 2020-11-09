import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;

public class SongsServlet extends HttpServlet {

  private String uriToDB = null;

  @Override
  public void init(ServletConfig servletConfig) throws ServletException {

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
          responseStr = "Korrekte Ausführung (1 SongID übergeben)!";
        }

      } else if (parameterMap.containsKey("all")) {

        System.out.println("All übergeben!");
        String[] values = parameterMap.get("all");

        //hier wird geprüft, dass "all" nur einmal übergeben wird
        if (values.length != 1) {
          responseStr = "Falsche Eingabe (all mehrfach übergeben))";
        } else {
          responseStr = "Korrekte Ausführung (all übergeben))";
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
