package htwb.ai;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class RunMeMain {

  static ArrayList<String> noRunMe;
  static ArrayList<String> withRunMe;
  static ArrayList<String> notInvokable;

  public static void main(String[] args) {
    runMeCheck(args[0]);
    runMeOutput(args[0]);
  }

  public static void runMeCheck(String classToCheck) {
    try {
      Class<?> klass = Class.forName(classToCheck);
      Object clazz = klass.getDeclaredConstructor().newInstance();
      Method[] methods = clazz.getClass().getDeclaredMethods();
      noRunMe = new ArrayList<>();
      withRunMe = new ArrayList<>();
      notInvokable = new ArrayList<>();
      for (Method method : methods) {
        try {
          method.invoke(clazz);
          if (method.getAnnotation(RunMe.class) != null) {
            withRunMe.add(method.getName());
          }

          if (method.getAnnotation(RunMe.class) == null) {
            noRunMe.add(method.getName());
          }
        } catch (IllegalAccessException exc) {
          notInvokable.add(method.getName() + ": " + exc.getMessage());
        } catch (IllegalArgumentException exce) {
          //exce.getMessage();
          //exce.printStackTrace();
          withRunMe.add(method.getName());
        } catch (InvocationTargetException invoEx) {
          invoEx.getMessage();
          invoEx.printStackTrace();
        }
      }
    } catch (ClassNotFoundException | NoSuchMethodException e) {
      System.out.println("Error: Could not find class " + classToCheck);
      System.out.println("Usage: java -jar runmerunner-TeamRobertJerome.jar "+classToCheck);
    } catch (IllegalAccessException e) {
      System.out.println("Error: Could not acces class " + classToCheck);
      System.out.println("Usage: java -jar runmerunner-TeamRobertJerome.jar "+classToCheck);
    } catch (InstantiationException e) {
      System.out.println("Error: Could not instantiate class " + classToCheck);
      System.out.println("Usage: java -jar runmerunner-TeamRobertJerome.jar "+classToCheck);
    } catch (InvocationTargetException e) {
      System.out.println("Error: Could not invoce class " + classToCheck);
      System.out.println("Usage: java -jar runmerunner-TeamRobertJerome.jar "+classToCheck);
    }
  }

  public static void runMeOutput(String classToCheck) {
     try {
       if(noRunMe != null || withRunMe != null || notInvokable !=null){
         System.out.println("Analyzed class ‘" + classToCheck + "’:");
       }
       if(noRunMe  != null){
         System.out.println("Methods without @RunMe:");
       }
       for (String string : noRunMe) {
         System.out.println(string);
       }
       if(withRunMe != null){
         System.out.println("Methods with @RunMe:");
       }
       for (String string : withRunMe) {
         System.out.println(string);
       }
       if(notInvokable != null){
         System.out.println("not invocable:");
       }
       for (String string : notInvokable) {
         System.out.println(string);
       }
     }catch (NullPointerException e) {}
  }
}
