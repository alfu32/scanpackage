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
        
        Package[] packages = Package.getPackages();
        int totalPackages=packages.length;
        for(int i=0;i<packages.length;i++){
            Package p=packages[i];
            String packageName=p.getName();
            
            System.out.printf("scanning package [%d/%d] %s\r",packageName,i,totalPackages);
            
            String filename = "scans/"+packageName+".xml";
            String filename2 = "scans/"+packageName+".json";
            
            println(filename2,new Member(Member.Kind.PACKAGE,packageName).toJson());
            
            Set<ClassInfo> classes = getClasses(packageName);
            for(ClassInfo c : classes){
                String className = c.getName();
                
                Class<?> clazz = Class.forName(c.getName());
                if(clazz.isInterface()){
                    println(filename2,new Member(Member.Kind.INTERFACE,clazz.getName()).toJson());
                } else if(clazz.isEnum()){
                    println(filename2,new Member(Member.Kind.ENUM,clazz.getName()).toJson());
                } else {
                    println(filename2,new Member(Member.Kind.CLASS,clazz.getName()).toJson());
                }
                Method[] methods = clazz.getMethods();
                for(Method m: methods){
                    println(filename2,new Member(Member.Kind.METHOD,m.getName()).toJson());
                }
                Field[] fields=clazz.getFields();
                for(Field f: fields){
                    println(filename2,new Member(Member.Kind.FIELD,f.getName()).toJson());
                }
            }
        }
    }
    public static Set<ClassInfo> getClasses(String packageName) throws IOException{
        ClassPath classPath = ClassPath.from(App.class.getClassLoader());
        return classPath.getAllClasses();
    }
}
