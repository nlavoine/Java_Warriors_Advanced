package warriors.client.console;

import io.vavr.collection.Stream;
import warriors.contracts.GameState;
import warriors.contracts.Hero;
import warriors.contracts.Map;
import warriors.contracts.WarriorsAPI;
import warriors.engine.Warriors;

import java.util.Scanner;

public class ClientConsole {

	enum MainMenuChoice {
		Start,
		Quit;
	}

	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			Console console = new Console(sc);
			switch (console.mainMenu()) {
					case Start:
						startGame(new Warriors(), console);
						break;
					case Quit:
						console.endMessage();
				}
		}
	}

	private static void startGame(WarriorsAPI warriors, Console console) {
		String playerName = console.askName();
		Hero hero = console.chooseHero(warriors.availableHeroes());
		Map map = console.chooseMap(warriors.availableMaps());

		Stream
			.iterate(
				warriors.createGame(playerName, hero, map),
				gameState -> warriors.nextTurn(gameState.getGameId()).getOrElseThrow(IllegalStateException::new))
			.peek(console::displayTurn)
			.filter(state -> state.getGameStatus() != GameState.GameStatus.IN_PROGRESS)
			.head();
	}
}

