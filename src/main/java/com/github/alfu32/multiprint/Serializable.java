package com.github.alfu32.multiprint;

public interface Serializable {

    @Override
    String toString();

    public static Serializable from(SerializableXml s){
        return new Serializable() {
            public String toString(){
                return s.toXml();
            }
        };
    }

    public static Serializable from(SerializableJson s){
        return new Serializable() {
            public String toString(){
                return s.toJson();
            }
        };
    }

    public static Serializable from(SerializableCsv s){
        return new Serializable() {
            public String toString(){
                return s.toCsv();
            }
        };
    }
}
