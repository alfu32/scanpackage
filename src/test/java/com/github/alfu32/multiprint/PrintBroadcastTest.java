package com.github.alfu32.multiprint;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PrintBroadcastTest {
    private static  PrintBroadcast pb;
    private static Deferable atEnd;
    @BeforeAll
    static void init() throws FileNotFoundException{
        pb=new PrintBroadcast(null)
            .subscribe(new PrintWriter(new File("test.xml")))
            .subscribe(new PrintWriter(new File("test.json")));
        atEnd=()-> {
            pb.flush();
            pb.close();
        };
    }
    @AfterAll
    static void deinit(){
        pb.flush();
        pb.close();
        atEnd.execute();
    }
    @Test
    void testPrint() {
        pb.println("one");
        pb.println("two");
        pb.println("three");
    }

    @Test
    void testPrintf() {
        pb.println("one");
        pb.println("two");
        pb.println("three");

    }

    @Test
    void testPrintln() {
        pb.println("one");
        pb.println("two");
        pb.println("three");

    }

    @Test
    void testSubscribe() {

    }
}
