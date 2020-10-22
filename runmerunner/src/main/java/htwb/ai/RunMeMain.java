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
    } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException |
      InvocationTargetException e) {
      exceptionPrinter(e, classToCheck);
    }
  }

  private static void exceptionPrinter(ReflectiveOperationException e, String classToCheck) {
    String eMessage;
    if (e.getClass() == ClassNotFoundException.class) {
      eMessage = "Could not find class ";
    }else if(e.getClass() == NoSuchMethodException.class) {
      eMessage = "No such mehtod in ";
    }else if(e.getClass() == IllegalAccessException.class){
      eMessage = "Could not acces class ";
    }else if(e.getClass() == InstantiationException.class){
      eMessage = "Could not instantiate class ";
    }else if(e.getClass() == InvocationTargetException.class){
      eMessage = "Could not invoce class ";
    }else{
      eMessage = "Something went terribly wrong with ";
    }
    System.out.println("------------------------------------------------------------------------");
    System.out.println("Error: "+ eMessage + classToCheck);
    System.out.println("Usage: java -jar runmerunner-TeamRobertJerome.jar classname");
    System.out.println("------------------------------------------------------------------------");
  }

  private static void runMeOutput(String classToCheck) {
     try {
       if(noRunMe != null || withRunMe != null || notInvokable !=null){
         System.out.println("------------------------------------------------------------------------");
         System.out.println("Analyzed class ‘" + classToCheck + "’:");
         System.out.println();
         System.out.println();
       }
       if(noRunMe  != null){
         System.out.println("Methods without @RunMe:");
       }
       for (String string : noRunMe) {
         System.out.println(string);
       }
       if(withRunMe !=null && noRunMe != null) {
         System.out.println();
       }
       if(withRunMe != null){
         System.out.println("Methods with @RunMe:");
       }
       for (String string : withRunMe) {
         System.out.println(string);
         }
      if (notInvokable != null && withRunMe != null) {
        System.out.println();
      }
       if(notInvokable != null){
         System.out.println("not invocable:");
       }
       for (String string : notInvokable) {
         System.out.println(string);
       }
     }catch (NullPointerException e) {}
    if(noRunMe != null || withRunMe != null || notInvokable !=null){
      System.out.println("------------------------------------------------------------------------");
    }
  }
}
