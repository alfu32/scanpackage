package com.github.alfu32.multiprint;

import java.io.PrintWriter;

public abstract class PrintSubscriber implements SerializableCsv,SerializableJson,SerializableXml{

    private PrintWriter pw;
    public PrintSubscriber(PrintWriter pw){
        this.pw=pw;
    }
    public PrintWriter getPrintWriter(){
        return pw;
    }
    public abstract String transform(String obj);
}
