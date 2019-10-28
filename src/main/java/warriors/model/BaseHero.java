package warriors.model;

import warriors.contracts.Attack;
import warriors.contracts.Hero;
import warriors.contracts.Life;
import warriors.engine.Game;

public abstract class BaseHero implements Hero {
	private String name;
	private Life life;
	protected Attack attack;

	public BaseHero(String newName, Life defaultLife, Attack attack) {
		this.name = newName;
		this.life = defaultLife;
		this.attack = attack;
	}

	public String applyEquipment(EquipmentCase equipment) {
		if(equipment instanceof LifeCase) {
			this.life = this.life.plus(equipment.getEquipmentValue());
		 	return "Votre vie est désormais de : " + this.life.asInt() + " points";
		} else {
			if (this.ApplyOffensiveEquipment(equipment)) {
				return "Votre niveau d'attaque est désormais de : " + this.attack.asInt() + " points";
			} else {
				return "Désolé, cette équipement n'est pas compatible avec votre héro";
			}			
		}			
	}
	
	public Game.FightStatus fightWithEnnemy(EnnemyCase ennemyCase) {
		Ennemy ennemy = ennemyCase.getEnnemy();
		ennemy.minus(this.getAttackLevel());
		if (ennemy.isDead()) {
			return Game.FightStatus.PlayerWin;
		}
		this.life = this.life.minus(ennemy.getAttack());
		if (isDead()) {
			return Game.FightStatus.EnnemyWin;
		}
		return Game.FightStatus.BothAlive;
	}

	public boolean isDead() {
		return life.isDead();
	}

	protected abstract boolean ApplyOffensiveEquipment(EquipmentCase equipment);

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Life getLife() {
		return this.life;
	}

	@Override
	public Attack getAttackLevel() {
		return this.attack;
	}	
}
