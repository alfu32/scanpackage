package com.github.alfu32.multiprint;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PrintBroadcastTest {
    private PrintBroadcast pb;

    @BeforeAll
    void init() throws FileNotFoundException{
        pb=new PrintBroadcast(null)
            .subscribe(new PrintSubscriber(new PrintWriter(new File("test.xml"))){

                @Override
                public String transform(String ...o) {
                    // TODO Auto-generated method stub
                    return "<"+o[0]+">"+o[1]+"</"+o[0]+">";
                }
            });
        
    }
    @AfterAll
    void deinit(){
        pb.flush();
        pb.close();
    }
    @Test
    void testPrint() {

    }

    @Test
    void testPrintf() {

    }

    @Test
    void testPrintln() {

    }

    @Test
    void testSubscribe() {

    }
}
