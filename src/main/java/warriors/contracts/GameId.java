package warriors.contracts;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;
import java.util.UUID;

public class GameId {

    public static GameId generate() {
        return new GameId(UUID.randomUUID());
        //return new GameId(UUID.fromString("756a5724-b3d6-4a49-8211-16928984495b"));
    }

    public static GameId parse(String id) {
        return new GameId(UUID.fromString(id));
    }

    private final UUID id;

    private GameId(UUID id) {
        this.id = id;
    }

    @JsonValue
    public String asString() {
        return id.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameId gameId = (GameId) o;
        return Objects.equals(id, gameId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
