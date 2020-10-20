package htwb.ai;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class RunMeMain {
    public static void main(String[] args){
        try {
            Class<?> klass = Class.forName(args[0]);
            Object clazz = (Object) klass.getDeclaredConstructor().newInstance();
            Method[] methods = clazz.getClass().getDeclaredMethods();
            ArrayList<String> noRunMe = new ArrayList<String>();
            ArrayList<String> withRunMe = new ArrayList<String>();
            ArrayList<String> notInvokable = new ArrayList<String>();
            for(Method method : methods){
                try {


                    method.invoke(clazz);
                    if (method.getAnnotation(htwb.ai.RunMe.class) != null) {
                        withRunMe.add(method.getName());
                    }

                    if (method.getAnnotation(htwb.ai.RunMe.class) == null) {
                        noRunMe.add(method.getName());
                    }
                }
               catch(IllegalAccessException exc) {
                   notInvokable.add(method.getName() + ": " + exc.getMessage());
               }
                catch(IllegalArgumentException exce){
                    //exce.getMessage();
                    //exce.printStackTrace();
                    withRunMe.add(method.getName());
                }
                catch(InvocationTargetException invoEx ){
                    invoEx.getMessage();
                    invoEx.printStackTrace();
                }
            }
            System.out.println("Analyzed class ‘"+args[0]+"’:");
            System.out.println("Methods without @RunMe:");
            for(String string : noRunMe){
                System.out.println(string);
            }
            System.out.println("Methods with @RunMe:");
            for(String string : withRunMe){
                System.out.println(string);
            }
            System.out.println("not invocable:");
            for(String string : notInvokable){
                System.out.println(string);
            }


        } catch (ClassNotFoundException | NoSuchMethodException e) {
            System.out.printf("Error: Could not find class "+ args[0]);
            System.out.println("Usage: java -jar runmerunner-TeamRobertJerome.jar className");
        } catch (IllegalAccessException e) {
            System.out.printf("Error: Could not acces class "+ args[0]);
            e.printStackTrace();
        } catch (InstantiationException e) {
            System.out.printf("Error: Could not instantiate class "+args[0]);
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            System.out.printf("Error: Could not invoce class "+ args[0]);
            e.printStackTrace();
        }
    }
}
