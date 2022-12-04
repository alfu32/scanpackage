package com.github.alfu32.java.scanpackage;

public class Member {
    public enum Kind{
        CLASS,
        METHOD,
        FIELD
    }
    public String fqn;
    public Kind kind;
    public Member(String fqn,Kind kind){
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
