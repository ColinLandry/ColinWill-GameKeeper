package wc.productions.gamekeeper;

/**
 * Created by colinlandry on 2018-03-27.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;

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
    public static final String TABLE_PLAYERTEAM = "playerteam";
    public static final String TABLE_TEAMLOGO = "teamlogo";
    public static final String TABLE_LOGOS = "logos";
    /**
     * Create common column names
     */

    public static final String COLUMN_ID = "id";

    /**
     * Games table column names
     */

    public static final String COLUMN_GAMENAME = "name";
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

    public static final String COLUMN_TEAMNAME = "name";
    public static final String COLUMN_TEAMCOACH = "coach";

    /**
     * Playerteams table column names
     */

    public static final String COLUMN_PLAYERID = "playerid";
    public static final String COLUMN_TEAMID = "teamid";

    /**
     * Images table column names
     */

    public static final String COLUMN_RESOURCE = "resource";

    /**
     * Imagesteams table column names
     */

    public static final String COLUMN_LOGO = "logo";

    /**
     * Create statements
     */

    /**
     * Player table
     */

    public static final String CREATE_PLAYER_TABLE = "CREATE TABLE " +
            TABLE_PLAYERS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY,"
            + COLUMN_PLAYERNAME + " TEXT,"
            + COLUMN_PLAYERPHONE + " TEXT,"
            + COLUMN_PLAYEREMAIL + " TEXT)";

    /**
     * Games table
     */

    public static final String CREATE_GAMES_TABLE = "CREATE TABLE " +
            TABLE_GAMES + "(" + COLUMN_ID + " INTEGER PRIMARY KEY,"
            + COLUMN_GAMENAME + " TEXT,"
            + COLUMN_GAMEDATE + " TEXT,"
            + COLUMN_GAMETEAM1 + " INTEGER REFERENCES " + TABLE_TEAMS + "(" + COLUMN_ID + "),"
            + COLUMN_GAMETEAM2 + " INTEGER REFERENCES " + TABLE_TEAMS + "(" + COLUMN_ID + "))";

    /**
     * Teams table
     */

    public static final String CREATE_TEAMS_TABLE =  "CREATE TABLE " +
            TABLE_TEAMS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY,"
            + COLUMN_TEAMNAME + " TEXT,"
            + COLUMN_TEAMCOACH + " TEXT)";

    /**
     * Images table
     */

    public static final String CREATE_LOGOS_TABLE = "CREATE TABLE " +
            TABLE_LOGOS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY,"
            + COLUMN_RESOURCE + " TEXT)";

    /**
     * Teamlogo table
     */

    public static final String CREATE_TEAMLOGO_TABLE = "CREATE TABLE " +
            TABLE_TEAMLOGO + "(" + COLUMN_TEAMID + " INTEGER REFERENCES " +
            TABLE_TEAMS + "(" + COLUMN_ID + ")," + COLUMN_LOGO +
            " INTEGER REFERENCES " + TABLE_LOGOS + "(" + COLUMN_ID + "))";

    /**
     * Playerteams table
     */

    public static final String CREATE_PLAYERTEAMS_TABLE = "CREATE TABLE " +
            TABLE_PLAYERTEAM + "(" + COLUMN_ID + " INTEGER PRIMARY KEY,"
            + COLUMN_PLAYERID + " INTEGER REFERENCES " + TABLE_TEAMS + "(" + COLUMN_ID + "),"
            + COLUMN_TEAMID + " INTEGER REFERENCES " + TABLE_PLAYERS + "(" + COLUMN_ID + "))";

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null , DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_GAMES_TABLE);
        db.execSQL(CREATE_PLAYER_TABLE);
        db.execSQL(CREATE_TEAMS_TABLE);
        db.execSQL(CREATE_PLAYERTEAMS_TABLE);
        db.execSQL(CREATE_LOGOS_TABLE);
        db.execSQL(CREATE_TEAMLOGO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEAMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAMES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYERTEAM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEAMLOGO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGOS);
    }

    /**
     * CRUD Operations
     */

    /**
     * CREATE Operations
     */

    /**
     * Create player
     */

    public int addPlayer(Player player, Team team){
        SQLiteDatabase db = this.getWritableDatabase();

        //Insert into player table
        ContentValues values = new ContentValues();
        values.put(COLUMN_PLAYERNAME, player.getName());
        values.put(COLUMN_PLAYERPHONE, player.getPhone());
        values.put(COLUMN_PLAYEREMAIL, player.getEmail());
        db.insert(TABLE_PLAYERS, null, values);

        Cursor cursor = db.rawQuery("SELECT last_insert_rowid()", null);
        if(cursor.moveToFirst()) {
            int id = Integer.parseInt(cursor.getString(0));
            System.out.println("Record ID " + id);

            //Insert into playerteams table
            ContentValues v = new ContentValues();
            v.put(COLUMN_TEAMID, team.getId());
            v.put(COLUMN_PLAYERID, id);
            db.insert(TABLE_PLAYERTEAM, null, v);
            db.close();

            return id;
        }
        return -1;
    }

    /**
     * Create team
     */

    public void addTeam(Team team){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEAMNAME, team.getName());
        values.put(COLUMN_TEAMCOACH, team.getCoach());
        db.insert(TABLE_TEAMS, null, values);

        Cursor cursor = db.rawQuery("SELECT last_insert_rowid()", null);
        if(cursor.moveToFirst()) {
            int id = Integer.parseInt(cursor.getString(0));
            System.out.println("Record ID " + id);
            team.setId(id);
        }
        db.close();
    }

    /**
     * Create logo
     */

    public void addTeamLogo(int logo, int team){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEAMID, team);
        values.put(COLUMN_LOGO, logo);
        db.insert(TABLE_TEAMLOGO, null, values);
        db.close();
    }

    public int addLogo(Logo logo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_RESOURCE, logo.getResource());
        db.insert(TABLE_LOGOS, null, values);
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT last_insert_rowid()", null);
        if(cursor.moveToFirst()) {
            int location = Integer.parseInt(cursor.getString(0));
            db.close();
            return location;
        }
        return -1;
    }

    /**
     * Create game
     */

    public void addGame(String gameName, String date, Team team1, Team team2){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_GAMENAME, gameName);
        values.put(COLUMN_GAMEDATE, date);
        values.put(COLUMN_GAMETEAM1, team1.getId());
        values.put(COLUMN_GAMETEAM2, team2.getId());
        db.insert(TABLE_GAMES, null, values);
        db.close();
    }

    /**
     * RETRIEVE Operations
     */

    //Retrieve all games
    public ArrayList<Game> getAllGames(){
        ArrayList<Game> gameList = new ArrayList<Game>();
        String query = "SELECT * FROM " + TABLE_GAMES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                gameList.add(new Game(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        Integer.parseInt(cursor.getString(3)),
                        Integer.parseInt(cursor.getString(4))));
            } while (cursor.moveToNext());
        }

        db.close();
        return gameList;
    }

    //Retrieve one game
    public Game getGame(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Game game = null;
        Cursor cursor = db.query(TABLE_GAMES,
                new String[]{COLUMN_ID, COLUMN_GAMENAME, COLUMN_GAMEDATE, COLUMN_GAMETEAM1, COLUMN_GAMETEAM2},
                COLUMN_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
            game = new Game(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    Integer.parseInt(cursor.getString(3)),
                    Integer.parseInt(cursor.getString(4)));
        }
        db.close();
        return game;
    }

    //Retrieve all players
    public ArrayList<Player> getAllPlayers(){
        ArrayList<Player> playerList = new ArrayList<Player>();
        String query = "SELECT * FROM " + TABLE_PLAYERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                playerList.add(new Player(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)));
            } while (cursor.moveToNext());
        }

        db.close();
        return playerList;
    }

    //Retrieve one player
    public Player getPlayer(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Player player = null;
        Cursor cursor = db.query(TABLE_PLAYERS,
                new String[]{COLUMN_ID, COLUMN_PLAYERNAME, COLUMN_PLAYERPHONE, COLUMN_PLAYEREMAIL},
                COLUMN_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
            player = new Player(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3));
        }
        db.close();
        return player;
    }

    //Get all team players
    public ArrayList<Player> getAllTeamPlayers(int team) {
        ArrayList<Player> playerList = new ArrayList<Player>();
        String selectQuery = "SELECT  * FROM " + TABLE_PLAYERTEAM + " WHERE " + COLUMN_TEAMID + " = " + team;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                String innerQuery = "SELECT * FROM " + TABLE_PLAYERS + " WHERE " + COLUMN_ID + "=" + cursor.getInt(1);
                Cursor innerCursor = db.rawQuery(innerQuery, null);
                if (innerCursor.moveToFirst()) {
                    do {
                        Player player = new Player(Integer.parseInt(innerCursor.getString(0)),
                                innerCursor.getString(1),
                                innerCursor.getString(2),
                                innerCursor.getString(3));
                        playerList.add(player);
                    } while (innerCursor.moveToNext());
                }
            }while (cursor.moveToNext());
        }
        return playerList;
    }

    //Get team logo
    public Logo getLogo(int team) {
        Logo teamLogo = new Logo();
        String selectQuery = "SELECT  * FROM " + TABLE_TEAMLOGO + " WHERE " + COLUMN_TEAMID + " = " + team;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                String innerQuery = "SELECT * FROM " + TABLE_LOGOS + " WHERE " + COLUMN_ID + "=" + cursor.getInt(1);
                Cursor innerCursor = db.rawQuery(innerQuery, null);
                if (innerCursor.moveToFirst()) {
                    do {
                        teamLogo.setId(Integer.parseInt(innerCursor.getString(0)));
                        teamLogo.setResource(innerCursor.getString(1));
                    } while (innerCursor.moveToNext());
                }
            }while (cursor.moveToNext());
        }
        return teamLogo;
    }

    //Retrieve all teams
    public ArrayList<Team> getAllTeams(){
        ArrayList<Team> teamList = new ArrayList<Team>();
        String query = "SELECT * FROM " + TABLE_TEAMS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                teamList.add(new Team(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2)));
            } while (cursor.moveToNext());
        }

        db.close();
        return teamList;
    }

    //Retrieve one team
    public Team getTeam(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Team team = null;
        Cursor cursor = db.query(TABLE_TEAMS,
                new String[]{COLUMN_ID, COLUMN_TEAMNAME, COLUMN_TEAMCOACH},
                COLUMN_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
            team = new Team(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1),
                    cursor.getString(2));
        }
        db.close();
        return team;
    }

    /**
     * UPDATE Operations
     */

    //Update team
    public int updateTeam(Team team){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEAMNAME, team.getName());
        values.put(COLUMN_TEAMCOACH, team.getCoach());
        return db.update(TABLE_COACHES, values, COLUMN_ID + "= ?",
                new String[]{String.valueOf(team.getId())});
    }

    //Update player
    public int updatePlayer(Player player){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PLAYERNAME, player.getName());
        values.put(COLUMN_PLAYERPHONE, player.getPhone());
        values.put(COLUMN_PLAYEREMAIL, player.getEmail());
        return db.update(TABLE_COACHES, values, COLUMN_ID + "= ?",
                new String[]{String.valueOf(player.getId())});
    }
    
    /**
     * DELETE Operations
     */

    //Delete game

    public void deleteGame(int game){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_GAMES, COLUMN_ID + " = ?",
                new String[]{String.valueOf(game)});
        db.close();
    }

    //Delete player

    public void deletePlayer(int player){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PLAYERS, COLUMN_ID + " = ?",
                new String[]{String.valueOf(player)});
        db.delete(TABLE_PLAYERTEAM, COLUMN_ID + " = ?",
                new String[]{String.valueOf(player)});
        db.close();
    }

    //Delete team

    public void deleteTeam(int team){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TEAMS, COLUMN_ID + " = ?",
                new String[]{String.valueOf(team)});
        db.delete(TABLE_PLAYERTEAM, COLUMN_ID + " = ?",
                new String[]{String.valueOf(team)});
        db.close();
    }

    public void deleteAllTeams(){
        String query = "SELECT * FROM " + TABLE_TEAMS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                deleteTeam(Integer.parseInt(cursor.getString(0)));
            } while (cursor.moveToNext());
        }

        db.close();
    }

    public void deleteAllGames(){
        String query = "SELECT * FROM " + TABLE_GAMES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                deleteGame(Integer.parseInt(cursor.getString(0)));
            } while (cursor.moveToNext());
        }

        db.close();

    }

    public void deleteAllPlayers(){
        String query = "SELECT * FROM " + TABLE_PLAYERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                deletePlayer(Integer.parseInt(cursor.getString(0)));
            } while (cursor.moveToNext());
        }

        db.close();

    }

    public void resetTables(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEAMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAMES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COACHES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYERTEAM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEAMLOGO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGOS);

        db.execSQL(CREATE_GAMES_TABLE);
        db.execSQL(CREATE_PLAYER_TABLE);
        db.execSQL(CREATE_TEAMS_TABLE);
        db.execSQL(CREATE_PLAYERTEAMS_TABLE);
        db.execSQL(CREATE_LOGOS_TABLE);
        db.execSQL(CREATE_TEAMLOGO_TABLE);
    }

}
