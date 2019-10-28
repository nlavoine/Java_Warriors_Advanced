package warriors.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import warriors.contracts.Attack;
import warriors.contracts.Life;

public class Ennemy {

    private final String name;
    private Life life;
    private final Attack attack;

    public Ennemy(String name, Life life, Attack attack) {
        this.name = name;
        this.life = life;
        this.attack = attack;
    }

    public String getName() {
        return name;
    }

    public Attack getAttack() {
        return attack;
    }

    public Life getLife() {
        return life;
    }

    public Life minus(Attack attackLevel) {
        this.life = life.minus(attackLevel);
        return life;
    }

    @JsonIgnore
    public boolean isDead() {
        return life.isDead();
    }

}
