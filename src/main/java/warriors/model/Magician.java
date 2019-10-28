package warriors.model;

import warriors.contracts.Attack;
import warriors.contracts.Life;

public class Magician extends BaseHero {

	public Magician() {
		super("Magicien",
			new Life(3, 8),
			new Attack(6, 15));
	}

	@Override
	protected boolean ApplyOffensiveEquipment(EquipmentCase equipment) {
		if (equipment instanceof SpellCase) {
			this.attack = this.attack.upgrade(equipment.getEquipmentValue());
			return true;
		}
		return false;
	}
}
