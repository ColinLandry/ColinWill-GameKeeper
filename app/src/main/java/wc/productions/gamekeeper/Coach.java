package wc.productions.gamekeeper;

/**
 * Created by colinlandry on 2018-03-29.
 */

public class Coach {
    private int id;
    private String name;
    private String email;

    public Coach(String name, String email){
        this.name = name;
        this.email = email;
    }

    public Coach(int id, String name, String email){
        this.id = id;
        this.name = name;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
