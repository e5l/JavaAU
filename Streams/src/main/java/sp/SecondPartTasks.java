package sp;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public final class SecondPartTasks {

    private SecondPartTasks() {}

    // Найти строки из переданных файлов, в которых встречается указанная подстрока.
    public static List<String> findQuotes(List<String> paths, CharSequence sequence) {
        return paths
                .stream()
                .flatMap(p -> {
                    try {
                        return Files.lines(Paths.get(p));
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                })
                .filter(a -> a.contains(sequence))
                .collect(toList());
    }

    // В квадрат с длиной стороны 1 вписана мишень.
    // Стрелок атакует мишень и каждый раз попадает в произвольную точку квадрата.
    // Надо промоделировать этот процесс с помощью класса java.util.Random и посчитать, какова вероятность попасть в мишень.
    public static double piDividedBy4() {

        Random random = new Random();
        Supplier<Double> doubler = () -> random.nextDouble() * 2 - 1;

        class Point {
            public double x;
            public double y;

            public Point(double x, double y) {
                this.x = x;
                this.y = y;
            }
        }

        int size = 100000000;
        return Stream.generate(() -> new Point(doubler.get(), doubler.get()))
                     .limit(size)
                     .filter(p -> p.x * p.x + p.y * p.y <= 1)
                     .count() * 1.0 / size;
    }

    // Дано отображение из имени автора в список с содержанием его произведений.
    // Надо вычислить, чья общая длина произведений наибольшая.
    public static String findPrinter(Map<String, List<String>> compositions) {
        class Pair {
            public String first;
            public int second;

            public Pair(String first, int second) {
                this.first = first;
                this.second = second;
            }

        }
        return compositions
                .entrySet()
                .stream()
                .map(a -> new Pair(a.getKey(), a.getValue().stream().mapToInt(String::length).sum()))
                .max((a, b) -> Integer.compare(a.second, b.second))
                .orElse(new Pair("", 0))
                .first;
    }

    // Вы крупный поставщик продуктов. Каждая торговая сеть делает вам заказ в виде Map<Товар, Количество>.
    // Необходимо вычислить, какой товар и в каком количестве надо поставить.
    public static Map<String, Integer> calculateGlobalOrder(List<Map<String, Integer>> orders) {
        return orders
                .stream()
                .map(Map::entrySet)
                .flatMap(Collection::stream)
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, Integer::sum));
    }
}
