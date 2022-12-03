package com.github.alfu32.multiprint;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;


public class PrintBroadcast extends PrintWriter{
    public PrintBroadcast(Writer out) {
        super(System.out);
        //TODO Auto-generated constructor stub
    }

    ArrayList<PrintWriter> subscribers=new ArrayList<>();

    PrintBroadcast subscribe(PrintWriter s){
        subscribers.add(s);
        return this;
    }

    @Override
    public void print(String str){
        subscribers.forEach(s -> {
            s.print(str);
        });
    }
    
    @Override
    public void println(String str){
        subscribers.forEach(s -> {
            s.println(str);
        });
    }

    @Override
    public PrintWriter printf(String format, Object... args) {
        subscribers.forEach(s -> {
            s.printf(format,args);
        });
        return this;
    }

    @Override
    public void flush() {
        subscribers.forEach(s -> {
            s.flush();
        });
        super.flush();
    }

    @Override
    public void close() {
        super.close();
        subscribers.forEach(s -> {
            s.close();
        });
        subscribers=new ArrayList<>();
    }

}