package htwb.ai;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RunMeMainTest {

  RunMeMain runMeMain;

  @Before
  public void setup(){
    runMeMain = new RunMeMain();
  }

  @Test
  public void checkExistingClass(){
    String test = "RunMeMock";
    runMeMain.runMeCheck(test);
  }

  @Test
  public void checkNonExistingClass(){
    String test = "blub";
    runMeMain.runMeCheck(test);
  }

  @Test
  public void checkJavaLangNumberClass(){
    String test = "java.lang.Number";
    runMeMain.runMeCheck(test);
  }

  @Test
  public void checkJavaIOCloseableClass(){
    String test = "java.io.Closeable";
    runMeMain.runMeCheck(test);
  }


}
