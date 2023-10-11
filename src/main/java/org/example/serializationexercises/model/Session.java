package org.example.serializationexercises.model;

import org.example.serializationexercises.SerializationExercises;

import java.io.Serializable;

public class Session implements Serializable {
    private Movie movie;
    private Theater theater;
    private String time;

    public Session(Movie movie, Theater theater, String time) {
        this.movie = movie;
        this.theater = theater;
        this.time = time;
    }
    public Movie getMovie() {
        return movie;
    }

    public Theater getTheater() {
        return theater;
    }

    public String getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Session{" +
                "movie=" + movie +
                ", theater=" + theater +
                ", time='" + time + '\'' +
                '}';
    }
}
