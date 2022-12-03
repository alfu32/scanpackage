package com.github.alfu32.multiprint;

import java.io.PrintWriter;

public abstract class PrintSubscriber{

    private PrintWriter pw;
    public PrintSubscriber(PrintWriter pw){
        this.pw=pw;
    }
    PrintWriter getPrintWriter(){
        return pw;
    }
    public abstract String transform(String ...strings);
}
