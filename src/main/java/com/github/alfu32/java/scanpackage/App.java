package com.github.alfu32.java.scanpackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Pattern;

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
    static void closeall(){
        for (Entry<String, PrintWriter> pair : logcache.entrySet()) {
            pair.getValue().flush();
            pair.getValue().close();
        }
    }
    static Printer printTo(String filename) throws FileNotFoundException{
        PrintWriter pw = getPrintWriter(filename);
        return (String message) -> pw.println(message);
    }
    static void println(String filename,String message) throws FileNotFoundException{
        getPrintWriter(filename).println(message);
    }
    private static final String[] anyMatchFilters=new String[]{
        "java\\..+",
        "com\\.xnet\\..+",
    };
    private static final String[] notMatchFilters=new String[]{
        ".+\\.equals",
        ".+\\.toString",
        ".+\\.hashCode",
    };
    public static boolean filters(String name){
        for (String filter : anyMatchFilters) {
            Pattern r=Pattern.compile(filter);
            // System.out.printf("name : %s\n",name);
            // System.out.printf("filter : %s\n",filter);
            // System.out.printf("r : %s\n",r);
            if(r.matcher(name).matches()){
                System.out.printf("accepted %s\n",name);
                return true;
            }
        }
        for (String filter : notMatchFilters) {
            Pattern r=Pattern.compile(filter);
            // System.out.printf("name : %s\n",name);
            // System.out.printf("filter : %s\n",filter);
            // System.out.printf("r : %s\n",r);
            if(r.matcher(name).matches()){
                System.out.printf("rejected %s\n",name);
                return false;
            }
        }
        System.out.printf("No match for %s\n",name);
        return false;
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
            if(filters(packageName)){
                System.out.printf("scanning package [%d/%d] %s\r",i,totalPackages,packageName);
                
                String json = "scans/"+packageName+".json";
                
                println(json,new Member(Member.Kind.PACKAGE,packageName,packageName).toJson());
                
                Set<ClassInfo> classes = getClasses(packageName);
                for(ClassInfo c : classes){
                    String className = c.getName();
                    if(filters(className)){
                        Class<?> clazz = Class.forName(c.getName());
                        if(clazz.isInterface()){
                            println(json,new Member(Member.Kind.INTERFACE,clazz.getName(),clazz.toGenericString()).toJson());
                        } else if(clazz.isEnum()){
                            println(json,new Member(Member.Kind.ENUM,clazz.getName(),clazz.toGenericString()).toJson());
                        } else if (clazz.isAnnotation()){
                            println(json,new Member(Member.Kind.ANNOTATION,clazz.getName(),clazz.toGenericString()).toJson());
                        } else{
                            println(json,new Member(Member.Kind.CLASS,clazz.getName(),clazz.toGenericString()).toJson());
                        }
                        Method[] methods = clazz.getMethods();
                        for(Method m: methods){
                            println(json,new Member(Member.Kind.METHOD,className+"."+m.getName(),m.toGenericString()).toJson());
                        }
                        Field[] fields=clazz.getFields();
                        for(Field f: fields){
                            println(json,new Member(Member.Kind.FIELD,className+"."+f.getName(),f.toGenericString()).toJson());
                        }
                    }
                }
            }
        }
        closeall();
    }
    public static Set<ClassInfo> getClasses(String packageName) throws IOException{
        ClassPath classPath = ClassPath.from(App.class.getClassLoader());
        return classPath.getAllClasses();
    }
}
