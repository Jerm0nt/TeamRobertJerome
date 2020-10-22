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

  // runMeCheck()-Tests
  @Test
  public void checkExistingClass(){
    String existing= "RunMeMock";
    runMeMain.runMeCheck(existing);
  }

  @Test
  public void checkNonExistingClass(){
    String nonExisting = "blub";
    runMeMain.runMeCheck(nonExisting);
  }

  @Test
  public void checkJavaLangNumberClass(){
    String javaLangNumber = "java.lang.Number";
    runMeMain.runMeCheck(javaLangNumber);
  }

  @Test
  public void checkJavaIOCloseableClass(){
    String javaIoCloseable = "java.io.Closeable";
    runMeMain.runMeCheck(javaIoCloseable);
  }

  //main()-Tests
  @Test
  public void mainExistingClass(){
    String existing= "RunMeMock";
    String[] args = new String[1];
    args[0] = existing;
    runMeMain.main(args);
  }

  @Test
  public void mainNonExistingClass(){
    String nonExisting = "blub";
    String[] args = new String[1];
    args[0] = nonExisting;
    runMeMain.main(args);
  }

  @Test
  public void mainJavaLangNumberClass(){
    String javaLangNumber = "java.lang.Number";
    String[] args = new String[1];
    args[0] = javaLangNumber;
    runMeMain.main(args);
  }

  @Test
  public void mainJavaIOCloseableClass(){
    String javaIoCloseable = "java.io.Closeable";
    String[] args = new String[1];
    args[0] = javaIoCloseable;
    runMeMain.main(args);
  }
}
