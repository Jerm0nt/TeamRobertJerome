package htwb.ai.TeamRobertJerome.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication//(exclude = {DataSourceAutoConfiguration.class })
public class SongsListApp {

  public static void main(String[] args){
    SpringApplication.run(SongsListApp.class, args);
  }
}
