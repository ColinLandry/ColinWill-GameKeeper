package wc.productions.gamekeeper;

import java.util.ArrayList;

/**
 * Created by colinlandry on 2018-03-29.
 */

public class Team {
    private int id;
    private String name;
    private int coach;
    private ArrayList<Player> playerList;

    public Team(String name, int coach){
        this.name = name;
        this.coach = coach;
    }

    public Team(int id, String name, int coach){
        this.id = id;
        this.name = name;
        this.coach = coach;
    }

    public void addPlayerToTeam(Player player){
        this.playerList.add(player);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCoach() {
        return coach;
    }

    public void setCoach(int coach) {
        this.coach = coach;
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(ArrayList<Player> playerList) {
        this.playerList = playerList;
    }
}
