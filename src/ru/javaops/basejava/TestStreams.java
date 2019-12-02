package ru.javaops.basejava;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TestStreams {
    public static void main(String[] args) {
        int[] values = new int[]{5, 1, 1, 2, 9, 9, 3};
        System.out.println("Задан массив чисел: ");
        System.out.println(IntStream.of(values)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining(", "))
        );
        System.out.println("\nВозвращаю минимально возможное число, составленное из этих уникальных цифр:");
        System.out.println(minValue(values));

        System.out.println("\nЗадана коллекция чисел.");
        System.out.println("Если сумма всех чисел нечетная - удаляю все нечетные, если четная - удаляю все четные.");

        List<Integer> integers1 = Stream.iterate(1, x -> x + 3).limit(17).collect(Collectors.toList());
        System.out.println("\nКоллеция 1 = " + integers1);
        System.out.println("Результат: " + oddOrEven(integers1));

        List<Integer> integers2 = Stream.iterate(1, x -> x + 3).limit(15).collect(Collectors.toList());
        System.out.println("\nКоллеция 2 = " + integers2);
        System.out.println("Результат: " + oddOrEven(integers2));
    }

    private static int minValue(int[] values) {
//    Реализовать метод через стрим int minValue(int[] values).
//    Метод принимает массив цифр от 1 до 9, надо выбрать уникальные
//    и вернуть минимально возможное число, составленное из этих уникальных цифр.
//    Не использовать преобразование в строку и обратно.
//    Например {1,2,3,3,2,3} вернет 123, а {9,8} вернет 89

//    Вариант 1
//        int res = 0;
//        try {
//            res = IntStream.of(values)
//                    .distinct()
//                    .sorted()
//                    .reduce((x, y) -> x * 10 + y)
//                    .getAsInt();
//        } catch (NoSuchElementException e) {
//            System.out.println("Массив 'int[] values' пустой!");
//        }
//        return res;

//    Вариант 2
        return IntStream.of(values)
                .distinct()
                .sorted()
                .reduce((x, y) -> x * 10 + y)
                .orElseThrow(() -> new NoSuchElementException("Массив 'int[] values' пустой!"));
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
//      Реализовать метод List<Integer> oddOrEven(List<Integer> integers)
//      если сумма всех чисел нечетная - удалить все нечетные, если четная - удалить все четные.
//      Сложность алгоритма должна быть O(N). Optional - решение в один стрим.

        int sum = integers.stream()
                .mapToInt(x -> x)
                .sum();
        System.out.println("Сумма чисел: " + sum);

        boolean evenSum = ((sum % 2) == 0);

        List<Integer> results = integers.stream()
                .filter(x -> evenSum == !(x % 2 == 0))
                .collect(Collectors.toList());

        return results;
    }
}
