package wc.productions.gamekeeper;

/**
 * Created by colinlandry on 2018-03-29.
 */

public class Player {

    private int id;
    private String name;
    private int phone;
    private String email;

    public Player(String name, int phone, String email){
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public Player(int id, String name, int phone, String email){
        this.id = id;
        this.name = name;
        this.phone = phone;
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

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
