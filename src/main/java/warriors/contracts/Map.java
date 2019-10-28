package warriors.contracts;

import warriors.model.BaseCase;

public interface Map {

	String getName();
	
	int getNumberOfCase();

    BaseCase getCase(int index);
}
