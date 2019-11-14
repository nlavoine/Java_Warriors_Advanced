package warriors.engine;

import io.vavr.collection.List;
import io.vavr.collection.Stream;
import warriors.contracts.GameId;
import warriors.contracts.GameState;
import warriors.contracts.Hero;
import warriors.contracts.Map;
import warriors.model.*;

import java.util.concurrent.ThreadLocalRandom;

public class Game implements GameState {

	public enum FightStatus {
		PlayerWin,
		EnnemyWin,
		BothAlive
	}

	private GameId gameId;
	private GameStatus gameStatus;
	private String playerName;
	private BaseHero gameHero;
	private MapModel gameMap;
	private List<String> lastLog;
	private int currentCaseIndex;
	
	public Game(String playerName, Hero hero, Map map) {
		this.gameId = GameId.generate();
		this.gameStatus = GameStatus.IN_PROGRESS;
		this.playerName = playerName;
		this.gameHero = (BaseHero) hero;
		this.gameMap = (MapModel) map;
		this.currentCaseIndex = 0;
		this.lastLog = List.of("Partie démarrée ! Vous etes sur la case Départ");
	}
	
	public Game nextTurn() {
		resetLog();
		int thimbleResult = playThimble();
		this.gameStatus = movePlayerOnBoard(thimbleResult);
		if (gameStatus == GameStatus.FINISHED) {
			appendMessagesToLog("Vous etes arrivé victorieux au bout du plateau !!");
		} else {
			BaseCase currentCase = this.gameMap.getCase(this.currentCaseIndex);
			appendMessagesToLog(
				"Vous avancez sur la case " + this.currentCaseIndex + "/" + this.gameMap.getNumberOfCase(),
				currentCase.message());
			
			if (currentCase.getType() == CaseType.EQUIPMENT) {
				appendMessagesToLog(this.gameHero.applyEquipment((EquipmentCase)currentCase));
			} else if(currentCase.getType() == CaseType.ENEMY) {
				this.gameStatus = fightEnnemy((EnnemyCase)currentCase);
			}
		}
		return this;
	}

	private GameStatus movePlayerOnBoard(int thimbleResult) {
		int numberOfCase = getMap().getNumberOfCase();
		currentCaseIndex = Math.min(numberOfCase - 1, currentCaseIndex + thimbleResult);
		if (currentCaseIndex == numberOfCase - 1) {
			return GameStatus.FINISHED;
		}
		return GameStatus.IN_PROGRESS;
	}

	private int playThimble() {
		int value = ThreadLocalRandom.current().nextInt(1, 6 + 1);
		appendMessagesToLog("Tirage du dé: " + value);
		return value;
	}

	private void resetLog() {
		this.lastLog = List.empty();
	}

	private void appendMessagesToLog(String... message) {
		lastLog = lastLog.appendAll(Stream.of(message));
	}

	private GameStatus fightEnnemy(EnnemyCase ennemy) {
		FightStatus fightResult = this.gameHero.fightWithEnnemy(ennemy);
		switch (fightResult) {
			case EnnemyWin:
				appendMessagesToLog(
					"Vous vous etes fait tuer par l'ennemi !!",
					"GAME OVER");
				return GameStatus.GAME_OVER;
			case PlayerWin:
				appendMessagesToLog("Bravo, vous avez vaincu l'ennemi !!");
				this.gameMap = this.gameMap.change(currentCaseIndex, new EmptyCase());
				return GameStatus.IN_PROGRESS;
			case BothAlive:
				appendMessagesToLog(
					"Vous avez été blessé par l'ennemi mais vous pouvez contiuer votre chemin",
					"Vous avez " + this.gameHero.getLife().asInt() + " points de vie");
				return GameStatus.IN_PROGRESS;
		}
		throw new IllegalStateException();
	}

	@Override
	public GameId getGameId() {
		return this.gameId;
	}

	@Override
	public GameStatus getGameStatus() {
		return this.gameStatus;
	}

	@Override
	public Hero getHero() {
		return this.gameHero;
	}

	@Override
	public Map getMap() {
		return this.gameMap;
	}

	@Override
	public List<String> getLastLog() {
		return this.lastLog;
	}

	@Override
	public String getPlayerName() {
		return this.playerName;
	}


}
