import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Example_of_Streams_and_Files {
    public void example() {
        //создаем новый ArrayList для хранения рандомных значений.
        List<Integer> integerList = new ArrayList<>();
        //создаем объект класса Random.
        Random random = new Random();
        //создаем объект класса Path для указания на файлов-записи
        final Path pathFirstAdding = Paths.get("src\\fileForWritingRandomNumbers.txt");
        final Path pathSecondAdding = Paths.get("src\\fileForWritingResultOfTask.txt");

        //указываем путь и кодировку для файла-записи рандомных чисел.
        //отлавливаем возможное исключение на отсуствие файла для записи.
        try (BufferedWriter writerOfRandomNumbers = Files.newBufferedWriter(pathFirstAdding, Charset.defaultCharset())) {
            //создаем 200 рандомных чисел.
            for (int i = 1; i < 201; i++) {
                //рандомим числа в диапазоне [0;999]
                int k = random.nextInt(1000);
                //записываем числа в файл-записи.
                writerOfRandomNumbers.write(k + " ");
                //добавляем числа в коллекцию чисел.
                integerList.add(k);
                //после каждого двадцатого числа начинаем записывать с новой строки.
                if (i % 20 == 0) {
                    writerOfRandomNumbers.write("\n");
                }
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }

        //создаем новую коллекцию чисел с помощью запуска Stream'a по предыдущей коллекции чисел.
        List<Integer> sortedList = integerList.stream()
                //фильтруем значения по принципу деления на 5 и 7 с нулевым остатком.
                .filter(s -> s % 5 == 0 || s % 7 == 0)
                //исключаем только уникальные числа.
                .distinct()
                //сортируем в порядке возрастания.
                .sorted((Comparator.reverseOrder()))
                //собираем элементы Stream'a в List.
                .collect(Collectors.toList());

        //создаем объект класса Double для подсчета average.
        Double average = sortedList.stream()
                .collect(Collectors.averagingInt(s -> s));
        //создаем объект класса Integer для подсчета sum.

        Integer sum = sortedList.stream()
                //элементы Stream'a возвращает int'ом.
                .mapToInt(s -> s)
                //возвращает сумму элементов.
                .sum();

        //создаем переменную в оберетке Optional для исключения NullPointerException.
        //по переменной запускаем Stream.
        Optional<Integer> maxNumber = sortedList.stream()
                //находим максимальное значение среди всех элементов отсортированного потока.
                .max(Comparator.comparingInt(o -> o));

        //создаем переменную в оберетке Optional для исключения NullPointerException.
        //по переменной запускаем Stream.
        Optional<Integer> minNumber = sortedList.stream()
                //находим минимальное значение среди всех элементов отсортированного Stream'a.
                .min((Comparator.comparing(o -> o)));

        //создаем переменную равную количеству элементов отсортированного Stream'a.
        final int quantity = sortedList.size();

        //указываем путь и кодировку для файла-записи чисел из отсортированного Stream'a.
        //отлавливаем возможное исключение на отсуствие файла для записи.
        try (BufferedWriter writerOfResultOfTask = Files.newBufferedWriter(pathSecondAdding, Charset.defaultCharset())) {
            //создаем переменную для счетчика элементов записи.
            int count = 0;
            //проходим по каждому элементу коллекции отсортированного Stream'a.
            for (Integer elementOfSortedList : sortedList) {
                //записываем каждый элемент.
                writerOfResultOfTask.write(elementOfSortedList + " ");
                //увеличиваем счетчик на 1.
                count++;
                //после каждого десятого числа начинаем записывать с новой строки.
                if (count % 10 == 0) {
                    writerOfResultOfTask.write("\n");
                }
            }

            //продолжаем запись в файл с новой строки.
            writerOfResultOfTask.newLine();
            //записываем максимальное значение или выбрасывает null.
            writerOfResultOfTask.write(maxNumber.orElse(null) + " - is max number.\n");
            //записываем минимальное значение или выбрасывает null.
            writerOfResultOfTask.write(minNumber.orElse(null) + " - is min number.\n");
            //записываем среднее значение.
            writerOfResultOfTask.write(String.format("%.3f",average) + " - is average of sorted numbers.\n");
            //записываем сумму значений.
            writerOfResultOfTask.write(sum + " - is sum of sorted numbers.\n");
            //записываем количество элементов отсортированного листа.
            writerOfResultOfTask.write(quantity + " - is size of sorted list.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}