package htwb.ai;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class RunMeMain {
    public static void main(String[] args){
        try {
            Class<?> clazz = Class.forName(args[0]);
            Method[] methods = clazz.getMethods();
            ArrayList<String> noRunMe = new ArrayList<String>();
            ArrayList<String> withRunMe = new ArrayList<String>();
            ArrayList<String> notInvokable = new ArrayList<String>();
            for(Method method : methods){
                if(method.isAnnotationPresent(RunMe.class)){
                    withRunMe.add(method.getName());
                }
                else if(!method.isAnnotationPresent(RunMe.class)){
                    noRunMe.add(method.getName());
                }
            }
            System.out.println("Analyzed class ‘"+args[0]+"’:");
            System.out.println("Methods without @RunMe:");
            for(String string : noRunMe){
                System.out.println(string);
            }
            System.out.println("Methods with @RunMe:");
            for( String string : withRunMe){
                System.out.println(string);
            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
