package warriors.client.console;

import java.util.EnumSet;
import java.util.Scanner;

import io.vavr.collection.Stream;
import warriors.contracts.GameState;
import warriors.contracts.Hero;
import warriors.contracts.Map;

class Console {

    private final Scanner scanner;
    private final ConsoleListPicker listPicker;

    Console(Scanner scanner) {
        this.scanner = scanner;
        listPicker = new ConsoleListPicker(scanner);
    }

    void startBanner() {
        System.out.println();
        System.out.println("================== Java Warriors ==================");
    }

    void endMessage() {
        System.out.println("À bientot");
    }

    ClientConsole.MainMenuChoice mainMenu() {
        var choices = EnumSet.allOf(ClientConsole.MainMenuChoice.class);
        Runnable startBanner = this::startBanner;
        ConsoleListPicker.EntryFormatter<ClientConsole.MainMenuChoice> entryFormatter = (index, mainMenuChoice) -> {
            switch (mainMenuChoice) {
                case Start:
                    return Stream.of(index + " - Commencer une partie");
                case Quit:
                    return Stream.of(index + " - Quitter");
                default:
                    throw new IllegalStateException();
            }
        };
        return listPicker.askChoice(
            choices,
            startBanner,
            entryFormatter);
    }

    String askName() {
        System.out.println();
        System.out.println("Entrez votre nom:");
        return scanner.nextLine();
    }

    Hero chooseHero(Iterable<Hero> heroes) {
        Runnable before = () -> System.out.println("Choisissez votre héro:");
        ConsoleListPicker.EntryFormatter<Hero> formatter = (index, heroe) ->
            Stream.of(
                index + " - " + heroe.getName(),
                "    Force d'attaque : " + heroe.getAttackLevel().asInt(),
                "    Niveau de vie : " + heroe.getLife().asInt());
        return listPicker.askChoice(
            heroes,
            before,
            formatter);
    }


    Map chooseMap(Iterable<Map> maps) {
        Runnable before = () -> System.out.println("Choisissez votre map:");
        ConsoleListPicker.EntryFormatter<Map> formatter = (index, map) -> Stream.of(index + " - " + map.getName());
        return listPicker.askChoice(maps,
            before,
            formatter);
    }

    void displayTurn(GameState gameState) {
        gameState.getLastLog().forEach(System.out::println);
        System.out.println("\nAppuyer sur une touche pour lancer le dé");
        scanner.nextLine();
    }
}
