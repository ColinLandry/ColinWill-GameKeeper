package wc.productions.gamekeeper;

import java.util.ArrayList;

/**
 * Created by colinlandry on 2018-04-02.
 */

public class Game {
    private int id;
    private String name;
    private String date;
    private int team1;
    private int team2;

    public Game(String name, String date, int team1, int team2){
        this.name = name;
        this.date = date;
        this.team1 = team1;
        this.team2 = team2;
    }

    public Game(int id, String name, String date, int team1, int team2){
        this.id = id;
        this.name = name;
        this.date = date;
        this.team1 = team1;
        this.team2 = team2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTeam1() {
        return team1;
    }

    public void setTeam1(int team1) {
        this.team1 = team1;
    }

    public int getTeam2() {
        return team2;
    }

    public void setTeam2(int team2) {
        this.team2 = team2;
    }
}
