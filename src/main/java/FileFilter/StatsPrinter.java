package FileFilter;

import java.util.ArrayList;

/**
 * Класс для расчета и вывода статистики.
 */
public class StatsPrinter {
  /**
   * Вывод короткой статистики (число элементов) по каждому типу.
   * <p>
   * Для целых и дробных чисел выводит число элементов, минимум, максимум,
   * сумму и среднее. Для строк выводит число элементов, длину самой короткой
   * и самой длинной строк.
   * </p>
   * @param doubles список дробных чисел.
   * @param integers список целых чисел.
   * @param strings список строк.
   *
   * @throws NullPointerException Если какой-либо из аргументов null.
   */
  static public void shortStatPrint( ArrayList<Double> doubles, ArrayList<Long> integers, ArrayList<String> strings) {
    if (doubles == null || integers == null || strings == null) {
      throw new NullPointerException();
    }

    System.out.println("Краткая статистика по дробным числам");
    System.out.println("Число элементов: " + doubles.size());
    System.out.println("------");
    System.out.println("Краткая статистика по целым числам");
    System.out.println("Число элементов: " + integers.size());
    System.out.println("------");
    System.out.println("Краткая статистика по строкам");
    System.out.println("Число элементов: " + strings.size());
  }

  /**
   * Вывод полной статистики по каждому типу.
   * @param doubles список дробных чисел.
   * @param integers список целых чисел.
   * @param strings список строк.
   *
   * @throws NullPointerException Если какой-либо из аргументов null.
   */
  static public void fullStatPrint(ArrayList<Double> doubles, ArrayList<Long> integers, ArrayList<String> strings) {
    if (doubles == null || integers == null || strings == null) {
      throw new NullPointerException();
    }

    fullStatPrintForDoubles(doubles);
    System.out.println("------");
    fullStatPrintForIntegers(integers);
    System.out.println("------");
    fullStatPrintForString(strings);
  }

  /**
   * Вывод полной статистики по дробным числам. Выводит число элементов, минимум, максимум,
   * сумму элементов и среднее.
   * @param doubles Список дробных чисел.
   */
  static private void fullStatPrintForDoubles(ArrayList<Double> doubles) {
    if (doubles.isEmpty()) {
      System.out.println("Статистика по дробным числам");
      System.out.println("Число элементов: 0");
      return;
    }

    int size = doubles.size();
    double min = doubles.getFirst();
    double max = doubles.getFirst();
    double sum = 0;
    double mean = 0;

    for (double num : doubles) {
      if (num < min) min = num;
      else if (num > max) max = num;
      sum += num;
    }
    mean = sum / size;

    System.out.println("Статистика по дробным числам");
    System.out.println("Число элементов: " + size);
    System.out.println("MIN: " + min);
    System.out.println("MAX: " + max);
    System.out.println("Сумма: " + sum);
    System.out.println("Среднее: " + mean);
  }

  /**
   * Вывод полной статистики по целым числам. Выводит число элементов, минимум, максимум,
   * сумму элементов и среднее.
   * @param integers Список целых чисел.
   */
  static private void fullStatPrintForIntegers(ArrayList<Long> integers) {
    if (integers.isEmpty()) {
      System.out.println("Статистика по целым числам");
      System.out.println("Число элементов: 0");
    }

    int size = integers.size();
    long min = integers.getFirst();
    long max = integers.getFirst();
    long sum = 0;
    double mean = 0;

    for (long num : integers) {
      if (num < min) min = num;
      else if (num > max) max = num;
      sum += num;
    }
    mean = (double) sum / size;

    System.out.println("Статистика по целым числам");
    System.out.println("Число элементов: " + size);
    System.out.println("MIN: " + min);
    System.out.println("MAX: " + max);
    System.out.println("Сумма: " + sum);
    System.out.println("Среднее: " + mean);
  }

  /**
   * Вывод полной статистики по строкам. Выводит число элементов, длину самой короткой
   * и самой длинной строк.
   * @param strings Список строк.
   */
  static private void fullStatPrintForString(ArrayList<String> strings) {
    if (strings.isEmpty()) {
      System.out.println("Статистика по строкам");
      System.out.println("Число элементов: 0");
    }

    int size = strings.size();
    int minLength = strings.getFirst().length();
    int maxLength = strings.getFirst().length();

    for (String str : strings) {
      if (str.length() < minLength) minLength = str.length();
      else if (str.length() > maxLength) maxLength = str.length();
    }

    System.out.println("Статистика по строкам");
    System.out.println("Число элементов: " + size);
    System.out.println("Размер самой короткой строки: " + minLength);
    System.out.println("Размер самой длинной строки: " + maxLength);
  }
}
