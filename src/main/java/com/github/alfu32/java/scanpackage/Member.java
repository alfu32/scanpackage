package com.github.alfu32.java.scanpackage;

public class Member {
    public enum Kind{
        PACKAGE,
        ENUM,
        INTERFACE,
        CLASS,
        METHOD,
        FIELD
    }
    public String fqn;
    public Kind kind;
    public Member(Kind kind,String fqn){
        this.kind=kind;
        this.fqn=fqn;
    }
    public String toJson(){
        return "{"
        + "\"kind\":\"" + kind + "\","
        + "\"name\":\"" + fqn + "\""
        + "}";
    }
}
