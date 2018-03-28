package wc.productions.gamekeeper;

/**
 * Created by colinlandry on 2018-03-27.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    /**
     * Keep track of the database version
     */
    public static final int DATABASE_VERSION = 1;

    /**
     * Create the name of the database
     */
    public static final String DATABASE_NAME = "gamekeeper";

    /**
     * Create the names of all the tables
     */
    public static final String TABLE_PLAYERS = "players";
    public static final String TABLE_GAMES = "games";
    public static final String TABLE_TEAMS = "teams";
    public static final String TABLE_COACHES = "coaches";

    /**
     * Create common column names
     */

    public static final String COLUMN_ID = "id";

    /**
     * Coach table column names
     */

    public static final String COLUMN_COACHNAME = "name";
    public static final String COLUMN_COACHEMAIL = "email";

    /**
     * Games table column names
     */

    public static final String COLUMN_GAMENAME = "name";
    public static final String COLUMN_GAMECOACH = "coach";
    public static final String COLUMN_GAMETEAM1 = "team1";
    public static final String COLUMN_GAMETEAM2 = "team2";
    public static final String COLUMN_GAMEDATE = "date";

    /**
     * Player table column names
     */

    public static final String COLUMN_PLAYERNAME = "name";
    public static final String COLUMN_PLAYERPHONE = "phone";
    public static final String COLUMN_PLAYEREMAIL = "email";

    /**
     * Teams table column names
     */

    public static final String COLUMN_TEAMPLAYERS = "players";
    public static final String COLUMN_TEAMNAME = "name";
    public static final String COLUMN_TEAMCOACH = "coach";

    /**
     * Create statements
     */

    /**
     * Player table
     */

    public static final String CREATE_PLAYER_TABLE = "CREATE TABLE " +
            TABLE_PLAYERS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY,"
            + COLUMN_PLAYERNAME + " TEXT,"
            + COLUMN_PLAYERPHONE + " INTEGER,"
            + COLUMN_PLAYEREMAIL + " TEXT)";

    /**
     * Coaches table
     */

    public static final String CREATE_COACHES_TABLE = "CREATE TABLE " +
            TABLE_COACHES + "(" + COLUMN_ID + " INTEGER PRIMARY KEY,"
            + COLUMN_COACHNAME + " TEXT,"
            + COLUMN_COACHEMAIL + " TEXT)";

    /**
     * Games table
     */

    public static final String CREATE_GAMES_TABLE = "CREATE TABLE " +
            TABLE_GAMES + "(" + COLUMN_ID + " INTEGER PRIMARY KEY,"
            + COLUMN_GAMENAME + " TEXT,"
            + COLUMN_GAMEDATE + " DATE,"
            + COLUMN_GAMECOACH + " INTEGER,"
            + COLUMN_GAMETEAM1 + " INTEGER,"
            + COLUMN_GAMETEAM2 + " INTEGER)";

    /**
     * Teams table
     * ask Cai how to have an array of players referencing id's in the player table
     */

    public static final String CREATE_TEAMS_TABLE =  "CREATE TABLE " +
            TABLE_TEAMS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY,"
            + COLUMN_TEAMNAME + " TEXT,"
            + COLUMN_TEAMPLAYERS + " MULTISET,"
            + COLUMN_TEAMCOACH + " INTEGER)";

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null , DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_COACHES_TABLE);
        db.execSQL(CREATE_GAMES_TABLE);
        db.execSQL(CREATE_PLAYER_TABLE);
        db.execSQL(CREATE_TEAMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEAMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAMES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COACHES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYERS);
    }

    /**
     * CRUD Operations
     */

}
