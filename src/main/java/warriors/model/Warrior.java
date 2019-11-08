package warriors.model;

import warriors.contracts.Attack;
import warriors.contracts.Life;

public class Warrior extends BaseHero{

	public Warrior() {
		super("Guerrier", new Life(5, 10), new Attack(10, 20));
	}

	@Override
	protected boolean ApplyOffensiveEquipment(EquipmentCase equipment) {
		if(equipment instanceof WeaponCase) {
			this.attack = this.attack.upgrade(equipment.getEquipmentValue());
			return true;
		}
		return false;
	}

}


