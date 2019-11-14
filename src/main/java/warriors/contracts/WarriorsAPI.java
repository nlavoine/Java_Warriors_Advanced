package warriors.contracts;

import io.vavr.control.Option;
import org.reactivestreams.Publisher;
import warriors.engine.Game;

public interface WarriorsAPI {
	
	Iterable<Hero> availableHeroes();
	
	Iterable<Map> availableMaps();
	
	GameState createGame(String playerName, Hero hero, Map map);
	
	Option<GameState> nextTurn(GameId gameId);

	Option<Game> show(GameId gameId);

    Iterable<Game> listGames();

    Publisher<GameState> observe(GameId gameId);

}
