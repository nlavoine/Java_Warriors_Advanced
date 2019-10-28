package warriors.contracts;

import io.vavr.control.Option;

public interface WarriorsAPI {
	
	Iterable<Hero> availableHeroes();
	
	Iterable<Map> availableMaps();
	
	GameState createGame(String playerName, Hero hero, Map map);
	
	Option<GameState> nextTurn(GameId gameId);
}
