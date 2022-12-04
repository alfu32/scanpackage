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
        
        println("scan.xml","<packages>");
        println("scan.json","[");
        Package[] packages = Package.getPackages();
        for(Package p : packages){
            String packageName=p.getName();
            if(packageName.startsWith("com.google")){
                continue;
            }
            println("scan.xml","  <package name=\""+packageName+"\">");
            println("scan.json","\""+packageName+"\":{");
            Set<ClassInfo> classes = getClasses(packageName);
            for(ClassInfo c : classes){
                String className = c.getName();
                println("scan.xml","    <class name=\""+className+"\">");
                println("scan.json","\""+className+"\":{_\"_type\":\"class\",");
                Class<?> clazz = Class.forName(c.getName());
                Method[] methods = clazz.getMethods();
                for(Method m: methods){
                    println("scan.xml","      <method name=\""+m.getName()+"\"/>");
                    println("scan.json","\""+m.getName()+"\":{}");
                }
                Field[] fields=clazz.getFields();
                for(Field f: fields){
                    println("scan.xml","      <field name=\""+f.getName()+"\"/>");
                    println("scan.json","\""+f.getName()+"\":{}");
                }
                println("scan.xml","    </class>");
                println("scan.json","}");
            }
            println("scan.xml","  </package>");
            println("scan.json","}");
        }
        println("scan.xml","</packages>");
        println("scan.json","]");
    }
    public static Set<ClassInfo> getClasses(String packageName) throws IOException{
        ClassPath classPath = ClassPath.from(App.class.getClassLoader());
        return classPath.getAllClasses();
    }
}
