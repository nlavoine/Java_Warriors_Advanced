package warriors.client.console;

import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Supplier;

import io.vavr.collection.Map;
import io.vavr.collection.Stream;
import io.vavr.control.Option;
import io.vavr.control.Try;

public class ConsoleListPicker {

    @FunctionalInterface
    interface EntryFormatter<T> {
        Stream<String> format(Integer index, T t);
    }

    private final Scanner scanner;

    ConsoleListPicker(Scanner scanner) {
        this.scanner = scanner;
    }

    <T> T askChoice(Iterable<T> choices, Runnable before, EntryFormatter<T> formatter) {
        return repeatIfNeeded(() -> tryAskChoice(choices, before, formatter));
    }

    private <T> T repeatIfNeeded(Supplier<Option<T>> supplier) {
        return Stream.continually(supplier)
            .flatMap(Function.identity())
            .head();
    }

    private <T> Option<T> tryAskChoice(Iterable<T> choices, Runnable before, EntryFormatter<T> formatter) {
        Map<Integer, T> indexedChoices = Stream.ofAll(choices).zipWithIndex().toMap(t -> t._2, t -> t._1);
        before.run();
        showChoices(formatter, indexedChoices);
        return Try.ofSupplier(() -> Integer.parseInt(scanner.nextLine()))
            .toOption()
            .flatMap(choice -> indexedChoices.get(choice - 1))
            .onEmpty(() -> System.out.println("Saisie invalide"));
    }

    private <T> void showChoices(EntryFormatter<T> formatter, io.vavr.collection.Map<Integer, T> indexedChoices) {
        indexedChoices.flatMap(t -> formatter.format(t._1 + 1, t._2)).forEach(System.out::println);
    }

}
