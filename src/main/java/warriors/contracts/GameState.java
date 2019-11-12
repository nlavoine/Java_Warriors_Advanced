package warriors.contracts;

import io.vavr.collection.List;
import io.vavr.control.Option;
import warriors.engine.Game;

public interface GameState {

	enum GameStatus {
		IN_PROGRESS,
		GAME_OVER,
		FINISHED
	}

	String getPlayerName();
	
	GameId getGameId();
	
	GameStatus getGameStatus();
	
	Hero getHero();
	
	Map getMap();
	
	List<String> getLastLog();


}
