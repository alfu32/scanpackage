package com.github.alfu32.java.scanpackage;

import java.io.IOException;
import java.util.Set;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;
import com.google.common.reflect.TypeToken;

import java.lang.System;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws SecurityException
     */
    public static void main(String[] args) throws IOException, SecurityException, ClassNotFoundException {
        
        System.out.println("<packages>");
        Package[] packages = Package.getPackages();
        for(Package p : packages){
            String packageName=p.getName();
            System.out.println("  <package name=\""+packageName+"\">");
            Set<ClassInfo> classes = getClasses(packageName);
            for(ClassInfo c : classes){
                String className = c.getName();
                System.out.println("    <class name=\""+className+"\">");
                Class<?> clazz = Class.forName(c.getName());
                Method[] methods = clazz.getMethods();
                for(Method m: methods){
                    System.out.println("      <method name=\""+m.getName()+"\"/>");
                }
                Field[] fields=clazz.getFields();
                for(Field f: fields){
                    System.out.println("      <field name=\""+f.getName()+"\"/>");
                }
                System.out.println("    </class>");
            }
            System.out.println("  </package>");
        }
        System.out.println("</packages>");
    }
    public static Set<ClassInfo> getClasses(String packageName) throws IOException{
        ClassPath classPath = ClassPath.from(App.class.getClassLoader());
        return classPath.getAllClasses();
    }
}
