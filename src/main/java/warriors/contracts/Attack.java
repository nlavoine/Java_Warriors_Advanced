package warriors.contracts;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public class Attack {

    private final int level;
    private final int max;

    public Attack(int level, int max) {
        this.level = level;
        this.max = max;
    }

    @JsonValue
    public int asInt() {
        return level;
    }

    public Attack upgrade(int equipmentValue) {
        return new Attack(Math.min(max, level + equipmentValue), max);
    }
}
