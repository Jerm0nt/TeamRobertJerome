package htwb.ai;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class RunMeMain {
    public static void main(String[] args){
        try {
            Class<?> clazz = Class.forName(args[0]);
            Method[] methods = clazz.getDeclaredMethods();
            ArrayList<String> noRunMe = new ArrayList<String>();
            ArrayList<String> withRunMe = new ArrayList<String>();
            ArrayList<String> notInvokable = new ArrayList<String>();
            for(Method method : methods){
                try {
                    if (method.getAnnotation(htwb.ai.RunMe.class) != null) {
                        withRunMe.add(method.getName());
                    }

                    if (method.getAnnotation(htwb.ai.RunMe.class) == null) {
                        noRunMe.add(method.getName());
                    }
                }
                catch(Exception ex){
                    notInvokable.add(method.getName()+": "+ ex.getMessage());
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


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
