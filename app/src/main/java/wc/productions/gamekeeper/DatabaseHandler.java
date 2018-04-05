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

//    public static final String CREATE_COACHES_TABLE = "CREATE TABLE " +
//            TABLE_COACHES + "(" + COLUMN_ID + " INTEGER PRIMARY KEY,"
//            + COLUMN_COACHNAME + " TEXT,"
//            + COLUMN_COACHEMAIL + " TEXT)";

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
//            + COLUMN_TEAMCOACH + " INTEGER REFERENCES " + TABLE_COACHES + "(" + COLUMN_ID + "))";

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
//        db.execSQL(CREATE_COACHES_TABLE);
        db.execSQL(CREATE_GAMES_TABLE);
        db.execSQL(CREATE_PLAYER_TABLE);
        db.execSQL(CREATE_TEAMS_TABLE);
        db.execSQL(CREATE_PLAYERTEAMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEAMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAMES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COACHES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYERTEAM);
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

    public void addPlayer(Player player, Team team){
        SQLiteDatabase db = this.getWritableDatabase();

        //Insert into player table
        ContentValues values = new ContentValues();
        values.put(COLUMN_PLAYERNAME, player.getName());
        values.put(COLUMN_PLAYERPHONE, player.getPhone());
        values.put(COLUMN_PLAYEREMAIL, player.getEmail());
        db.insert(TABLE_PLAYERS, null, values);

        //Insert into playerteams table
        ContentValues v = new ContentValues();
        v.put(COLUMN_TEAMID, team.getId());
        v.put(COLUMN_PLAYERID, player.getId());
        db.insert(TABLE_PLAYERTEAM, null, v);
        db.close();
    }

    /**
     * Create coach
     */

//    public void addCoach(Coach coach){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_PLAYERNAME, coach.getName());
//        values.put(COLUMN_PLAYEREMAIL, coach.getEmail());
//        db.insert(TABLE_PLAYERS, null, values);
//        db.close();
//    }

    /**
     * Create team
     */

    public void addTeam(Team team){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEAMNAME, team.getName());
        values.put(COLUMN_TEAMCOACH, team.getCoach());
        db.insert(TABLE_TEAMS, null, values);
        db.close();
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
                        Integer.parseInt(cursor.getString(2)),
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
                    Integer.parseInt(cursor.getString(2)),
                    cursor.getString(3));
        }
        db.close();
        return player;
    }

    /**
     * Select all from players table where the id matches in the player teams table,
     * and the id is of the team you input
     * @param team team that you want to retrieve all players for
     */
    //Retrieve all players for a team
    public ArrayList<Player> getAllTeamPlayers(Team team){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Player> list = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_PLAYERS + " AS p "
                + " JOIN " + TABLE_PLAYERTEAM + " AS t "
                + " ON p." + COLUMN_ID + " = t." + COLUMN_PLAYERID
                + " WHERE t." + COLUMN_TEAMID + " = " + team.getId();
        System.out.println("Test" + team.getId());
        Cursor cursor = db.rawQuery(sql, null);
        /**
         * If cursor is not null, add to player arraylist
         */
        if (cursor != null && cursor.moveToFirst()){
            do{
                list.add((new Player(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        Integer.parseInt(cursor.getString(2)),
                        cursor.getString(3))));
            } while (cursor.moveToNext());
        }
        for (int i = 0; i < list.size(); i++){
            System.out.println(list.get(i));

        }
        db.close();
        return list;
    }

    /**
     * Dont know how to grab and populate arraylist of players from database, might need to make a
     * loop to grab from tableplayers team where id is equal to team id
     *
     * maybe use addplayertoteam function matching id
     */
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

//    //Retrieve all coaches
//    public ArrayList<Coach> getAllCoaches(){
//        ArrayList<Coach> coachList = new ArrayList<Coach>();
//        String query = "SELECT * FROM " + TABLE_COACHES;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//        if(cursor.moveToFirst()){
//            do{
//                coachList.add(new Coach(Integer.parseInt(cursor.getString(0)),
//                        cursor.getString(1),
//                        cursor.getString(2)));
//            } while (cursor.moveToNext());
//        }
//
//        db.close();
//        return coachList;
//    }

//    //Retrieve one coach
//    public Coach getCoach(int id){
//        SQLiteDatabase db = this.getReadableDatabase();
//        Coach coach = null;
//        Cursor cursor = db.query(TABLE_COACHES,
//                new String[]{COLUMN_ID, COLUMN_COACHNAME, COLUMN_COACHEMAIL},
//                COLUMN_ID + "=?", new String[]{String.valueOf(id)},
//                null, null, null, null);
//        if(cursor != null){
//            cursor.moveToFirst();
//            coach = new Coach(Integer.parseInt(cursor.getString(0)),
//                    cursor.getString(1),
//                    cursor.getString(2));
//        }
//        db.close();
//        return coach;
//    }

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

    //Delete coach

    public void deleteCoach(int coach){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COACHES, COLUMN_ID + " = ?",
                new String[]{String.valueOf(coach)});
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

}
