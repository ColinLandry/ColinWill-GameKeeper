package wc.productions.gamekeeper;

/**
 * Created by colinlandry on 2018-04-10.
 */

public class Logo {
    private int id;
    private String resource;

    public Logo(int id, String resource) {
        this.id = id;
        this.resource = resource;
    }

    public Logo(){

    }

    public Logo(String resource) {
        this.resource = resource;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}
