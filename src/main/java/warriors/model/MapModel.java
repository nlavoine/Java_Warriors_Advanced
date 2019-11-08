package warriors.model;

import io.vavr.collection.List;
import warriors.contracts.Map;

public class MapModel implements Map {

	private String name;
	private List<BaseCase> cases;
	
	public MapModel(String name, List<BaseCase> cases) {
		this.name = name;
		this.cases = cases;
	}
	
	public List<BaseCase> getCases() {
		return this.cases;
	}	
	
	@Override
	public int getNumberOfCase() {
		return this.cases.length();
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public BaseCase getCase(int index) {
		return this.cases.get(index);
	}

	public MapModel change(int currentCaseIndex, BaseCase newCase) {
		this.cases = this.cases.update(currentCaseIndex, newCase);
		return this;
	}
}


