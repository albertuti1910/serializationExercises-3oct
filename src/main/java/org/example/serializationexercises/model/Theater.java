package org.example.serializationexercises.model;

import java.io.Serializable;

public class Theater implements Serializable {
    private String name;
    private String location;

    public Theater(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "Theater{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
