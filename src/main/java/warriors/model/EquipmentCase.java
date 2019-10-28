package warriors.model;

public abstract class EquipmentCase extends BaseCase {
	
	private String name;
	private int equipmentValue;
	
	public EquipmentCase(String name, int equipmentValue) {
		this.name = name;
		this.equipmentValue = equipmentValue;
	}
	
	public int getEquipmentValue() {
		return this.equipmentValue;
	}
	
	@Override
	public CaseType getType() {
		return CaseType.EQUIPMENT;
	}
	
	@Override
	public String message() {
		return "Vous avez trouvé une caisse contenant : " + this.name + " de valeur " + this.getEquipmentValue();
	}
}
