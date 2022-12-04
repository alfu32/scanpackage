package com.github.alfu32.java.scanpackage;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class AppTest {
    @Test
    void testGetClasses() {
        try {
            App.getClasses("java.io");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    void testGetPrintWriter() {
        try {
            App.getPrintWriter("test.file");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    void testMain() {

    }

    @Test
    void testPrintln() {
        try {
            App.println("test.file", "it works");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
