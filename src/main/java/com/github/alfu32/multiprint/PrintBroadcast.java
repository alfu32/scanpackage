package com.github.alfu32.multiprint;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;


public class PrintBroadcast extends PrintWriter{
    public PrintBroadcast(Writer out) {
        super(System.out);
        //TODO Auto-generated constructor stub
    }

    ArrayList<PrintSubscriber> subscribers=new ArrayList<>();

    PrintBroadcast subscribe(PrintSubscriber s){
        subscribers.add(s);
        return this;
    }

    @Override
    public void print(String str){
        subscribers.forEach(s -> {
            s.getPrintWriter().print(s.transform(str));
        });
    }
    
    @Override
    public void println(String str){
        subscribers.forEach(s -> {
            s.getPrintWriter().println(s.transform(str));
        });
    }

    @Override
    public PrintWriter printf(String format, Object... args) {
        subscribers.forEach(s -> {
            s.getPrintWriter().printf(format,s.transform(args[0].toString()));
        });
        return this;
    }

    @Override
    public void flush() {
        subscribers.forEach(s -> {
            s.getPrintWriter().flush();
        });
        super.flush();
    }

    @Override
    public void close() {
        subscribers.forEach(s -> {
            s.getPrintWriter().close();
        });
        super.close();
    }

}