package wc.productions.gamekeeper;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by colinlandry on 2018-03-29.
 */

public class Team implements Parcelable {
    private int id;
    private String name;
    private String coach;
    private ArrayList<Player> playerList;

    public Team(String name, String coach){
        this.name = name;
        this.coach = coach;
    }

    public Team(int id, String name, String coach){
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

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(ArrayList<Player> playerList) {
        this.playerList = playerList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.name);
        parcel.writeString(this.coach);
    }

    protected Team(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.coach = in.readString();
    }

    public static final Parcelable.Creator<Team> CREATOR = new Parcelable.Creator<Team>() {
        @Override
        public Team createFromParcel(Parcel source) {
            return new Team(source);
        }

        @Override
        public Team[] newArray(int size) {
            return new Team[size];
        }
    };
}
