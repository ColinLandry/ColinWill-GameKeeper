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

    public static final String COLUMN_FNAME = "firstName";
    public static final String COLUMN_LNAME = "lastName";
    public static final String COLUMN_EMAIL = "email";

    /**
     * Games table column names
     */

    public static final String COLUMN_GAMENAME = "name";
    public static final String COLUMN_COACH = "coach";
    public static final String COLUMN_TEAM1 = "team1";
    public static final String COLUMN_TEAM2 = "team2";
    public static final String COLUMN_DATE = "date";

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

//    public static final String CREATE_BREEDS_TABLE = "CREATE TABLE " +
//            TABLE_BREEDS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY,"
//            + COLUMN_NAME + " TEXT," + COLUMN_DESCRIPTION + " TEXT,"
//            + COLUMN_IMAGE + " TEXT," + COLUMN_URL + " TEXT)";

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null , DATABASE_VERSION);
    }

//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(CREATE_BREEDS_TABLE);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BREEDS);
//    }

    /**
     * CRUD Operations
     */

}
