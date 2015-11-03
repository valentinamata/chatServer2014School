
package entity;

public class Sport {
    String sport_name;
    int min_players;
    int max_players;
    int price_instructor;

    public Sport(String sport_name, int min_players, int max_players, int price_instructor) {
        this.sport_name = sport_name;
        this.min_players = min_players;
        this.max_players = max_players;
        this.price_instructor = price_instructor;
    }

    public String getSport_name() {
        return sport_name;
    }

    public void setSport_name(String sport_name) {
        this.sport_name = sport_name;
    }

    public int getPrice_instructor() {
        return price_instructor;
    }

    public void setPrice_instructor(int price_instructor) {
        this.price_instructor = price_instructor;
    }
    
    
    public int getMin_players() {
        return min_players;
    }

    public void setMin_players(int min_players) {
        this.min_players = min_players;
    }

    public int getMax_players() {
        return max_players;
    }

    public void setMax_players(int max_players) {
        this.max_players = max_players;
    }
    
        @Override
    public String toString() {
        return "Sport name: " + sport_name + ", Instructor price :" + price_instructor + '}';
    }

}
