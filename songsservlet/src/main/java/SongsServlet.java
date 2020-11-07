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

    if(parameterMap.size()!=1){
      responseStr ="Falsch leider! trink ein cay und versuch nochmal!";
    }else {
      if (parameterMap.containsKey("songid")) {
        System.out.println("songID gefunden");
        String[] values = parameterMap.get("songid");
        if (values.length != 1) {
          responseStr = "Falsche Eingabe, bitte nur einen song anfordern!";
        } else {
          String value = values[0];
          responseStr = "Korrekte Ausführung Bruder!";
        }
      } else if (parameterMap.containsKey("all")) {
        System.out.println("all gefunden");
        String[] values = parameterMap.get("all");
        if (values.length != 1) {
          responseStr = "Falsche Eingabe bei all!";
        } else {
          responseStr = "Korrekte Ausführung Bruder bei all!";
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
