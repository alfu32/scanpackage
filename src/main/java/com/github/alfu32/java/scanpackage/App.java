package com.github.alfu32.java.scanpackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Set;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }
    static HashMap<String,PrintWriter> logcache=new HashMap<>();
    static PrintWriter getPrintWriter(String ref) throws FileNotFoundException{
        if(!logcache.containsKey(ref)){
            logcache.put(ref,new PrintWriter(new File(ref)));   
        }
        return logcache.get(ref);
    }
    static void println(String filename,String message) throws FileNotFoundException{
        getPrintWriter(filename).println(message);
    }
    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     * @throws IOException
     * @throws FileNotFoundException
     * @throws ClassNotFoundException
     * @throws SecurityException
     */
    public static void main(String[] args) throws FileNotFoundException,IOException, SecurityException, ClassNotFoundException {
        
        println("report.xml","<packages>");
        println("report.json","[");
        Package[] packages = Package.getPackages();
        for(Package p : packages){
            String packageName=p.getName();
            System.out.printf("scanning package %s",packageName);
            String filename = packageName+".xml";
            String filename2 = packageName+".json";
            if(packageName.startsWith("com.google")){
                continue;
            }
            println(filename,"  <package name=\""+packageName+"\">");
            println(filename2,"\""+packageName+"\":{");
            Set<ClassInfo> classes = getClasses(packageName);
            for(ClassInfo c : classes){
                String className = c.getName();
                println(filename,"    <class name=\""+className+"\">");
                println(filename2,"\""+className+"\":{_\"_type\":\"class\",");
                Class<?> clazz = Class.forName(c.getName());
                Method[] methods = clazz.getMethods();
                for(Method m: methods){
                    println(filename,"      <method name=\""+m.getName()+"\"/>");
                    println(filename2,"\""+m.getName()+"\":{}");
                }
                Field[] fields=clazz.getFields();
                for(Field f: fields){
                    println(filename,"      <field name=\""+f.getName()+"\"/>");
                    println(filename2,"\""+f.getName()+"\":{}");
                }
                println(filename,"    </class>");
                println(filename2,"}");
            }
            println(filename,"  </package>");
            println(filename2,"}");
        }
        println("report.xml","</packages>");
        println("report.json","]");
    }
    public static Set<ClassInfo> getClasses(String packageName) throws IOException{
        ClassPath classPath = ClassPath.from(App.class.getClassLoader());
        return classPath.getAllClasses();
    }
}
