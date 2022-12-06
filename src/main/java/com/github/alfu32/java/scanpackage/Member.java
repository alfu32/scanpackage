package com.github.alfu32.java.scanpackage;

public class Member {
    public enum Kind{
        PACKAGE,
        ANNOTATION,
        ENUM,
        INTERFACE,
        CLASS,
        METHOD,
        FIELD
    }
    public String fqn;
    public Kind kind;
    public String signature;
    public Member(Kind kind,String fqn,String signature){
        this.kind=kind;
        this.fqn=fqn;
        this.signature=signature;
    }
    public String toJson(){
        return "{"
        + "\"kind\":\"" + kind.name() + '"'+','
        + "\"name\":\"" + fqn + '"'+','
        + "\"signature\":\"" + signature + '"'
        + "}";
    }
}
