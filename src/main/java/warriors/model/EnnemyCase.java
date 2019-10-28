package warriors.model;

import warriors.contracts.Attack;
import warriors.contracts.Life;

public class EnnemyCase extends BaseCase {

	private final Ennemy ennemy;
	
	public EnnemyCase(Ennemy ennemy) {
		this.ennemy = ennemy;
	}

	public Ennemy getEnnemy() {
		return ennemy;
	}

	@Override
	public CaseType getType() {
		return CaseType.ENEMY;
	}
	
	@Override
	public String message() {
		return "Vous rencontrez un " + this.getEnnemy().getName() + " de force " + this.getEnnemy().getAttack().asInt();
	}

}
