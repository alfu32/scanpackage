package com.github.alfu32.java.scanpackage;

import java.io.IOException;
import java.util.Set;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;
import com.google.common.reflect.TypeToken;

import java.lang.System;
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
        System.out.println("Hello World!");
        for(Package p : Package.getPackages()){
            String packageName=p.getName();
            System.out.println(" : "+packageName);
            for(ClassInfo c : getClasses(packageName)){
                System.out.println("  - "+c.getName());
                for(Method m: Class.forName(c.getName()).getMethods()){
                    System.out.println("  - "+c.getName()+"#"+m.getName());
                }
            }
        }
    }
    public static Set<ClassInfo> getClasses(String packageName) throws IOException{
        ClassPath classPath = ClassPath.from(App.class.getClassLoader());
        return classPath.getAllClasses();
    }
}
