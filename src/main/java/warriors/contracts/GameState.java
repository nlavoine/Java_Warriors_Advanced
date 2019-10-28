package warriors.contracts;

import io.vavr.collection.List;

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
