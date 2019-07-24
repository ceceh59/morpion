package com.example.morpion;

import java.util.Date;

public class ScoreData {

        private int idScore;
        private String name;
        private int score;
        private Date when_integer;

    public ScoreData(int idScore, String name, int score, Date when_integer) {
        this.idScore = idScore;
        this.name = name;
        this.score = score;
        this.when_integer = when_integer;
    }

    public int getIdScore() {
        return idScore;
    }

    public void setIdScore(int idScore) {
        this.idScore = idScore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Date getWhen_integer() {
        return when_integer;
    }

    public void setWhen_integer(Date when_integer) {
        this.when_integer = when_integer;
    }

    @Override
    public String toString() {
        return "ScoreData{" +
                "idScore=" + idScore +
                ", name='" + name + '\'' +
                ", score=" + score +
                ", when_integer=" + when_integer +
                '}';
    }
}
