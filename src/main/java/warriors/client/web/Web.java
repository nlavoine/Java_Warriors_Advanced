package warriors.client.web;


import io.vavr.collection.Stream;
import warriors.contracts.Hero;

import java.util.Scanner;

public class Web {

    private final Scanner scanner;
    private final WebListPicker listPicker;

    Web(Scanner scanner) {
        this.scanner = scanner;
        listPicker = new WebListPicker(scanner);
    }

    Hero chooseHero(Iterable<Hero> heroes) {
        Runnable before = () -> System.out.println("Choisissez votre héro:");
        WebListPicker.EntryFormatter<Hero> formatter = (index, heroe) ->
                Stream.of(
                        index + " - " + heroe.getName(),
                        "    Force d'attaque : " + heroe.getAttackLevel().asInt(),
                        "    Niveau de vie : " + heroe.getLife().asInt());
        return listPicker.askChoice(
                heroes,
                before,
                formatter);
    }
}
