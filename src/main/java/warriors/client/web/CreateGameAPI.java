package warriors.client.web;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateGameAPI {
    public int hero;
    public int map;
    public String playerName;



    public CreateGameAPI(@JsonProperty("hero") int hero, @JsonProperty("map") int map, @JsonProperty("playerName") String playerName) {
        this.hero = hero;
        this.map = map;
        this.playerName = playerName;
    }

    public int getHero() {
        return hero;
    }

    public void setHero(int hero) {
        this.hero = hero;
    }
    public int getMap() {
        return map;
    }

    public void setMap(int map) {
        this.map = map;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
