package wc.productions.gamekeeper;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by colinlandry on 2018-04-02.
 */

public class Game implements Parcelable{
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

    public int describeContents() {
        return 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.name);
        parcel.writeString(this.date);
        parcel.writeInt(this.team1);
        parcel.writeInt(this.team2);
    }

    protected Game(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.date = in.readString();
        this.team1 = in.readInt();
        this.team2 = in.readInt();
    }

    public static final Parcelable.Creator<Game> CREATOR = new Parcelable.Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel source) {
            return new Game(source);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };
}
