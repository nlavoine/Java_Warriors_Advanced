package warriors.model;

public class EmptyCase extends BaseCase {

	@Override
	public CaseType getType() {
		return CaseType.EMPTY;
	}

	@Override
	public String message() {
		return "Vous etes sur une case vide !";
	}

}
