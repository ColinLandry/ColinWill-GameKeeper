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
}
