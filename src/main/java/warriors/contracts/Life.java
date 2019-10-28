package warriors.contracts;

import com.fasterxml.jackson.annotation.JsonValue;

public class Life {

    private final int level;
    private final int max;

    public Life(int level, int max) {
        this.level = level;
        this.max = max;
    }

    @JsonValue
    public int asInt() {
        return level;
    }

    private Life withLevel(int level) {
        return new Life(level, max);
    }

    public Life plus(int equipmentValue) {
        return withLevel(Math.min(this.max, this.level + equipmentValue));
    }

    public Life minus(Attack attack) {
        return withLevel(Math.max(0, this.level - attack.asInt()));
    }

    public boolean isDead() {
        return level <= 0;
    }
}
