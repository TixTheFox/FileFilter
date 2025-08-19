package FileFilter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Класс обходит заданные при запуске файлы и собирает данные (строки и числа).
 */
public class FileProcessor {
  /**
   * Список дробных чисел из файлов
   */
  protected ArrayList<Double> doubles;
  /**
   * Список целых чисел из файлов
   */
  protected ArrayList<Long> integers;
  /**
   * Список строк из файлов
   */
  protected ArrayList<String> strings;


  /**
   * Конструктор. Инициализирует экземпляр класса.
   */
  public FileProcessor() {
    doubles = new ArrayList<>();
    integers = new ArrayList<>();
    strings = new ArrayList<>();
  }

  /**
   * Для каждого из заданных файлов запускает функцию по считыванию элементов.
   * @param files Список имен файлов, которые будут обработаны
   * @throws NullPointerException Если files = null.
   */
  public void processFiles(ArrayList<String> files) {
    if (files == null) {
      throw new NullPointerException();
    }
    for (String filename : files) {
      processFile(filename);
    }
  }

  /**
   * Обрабатывает один файл, сохраняет элементы во внутренние списки.
   * @param filename Имя файла, который будет обработан.
   * @throws NullPointerException Если filename = null.
   */
  public void processFile(String filename) {
    if (filename == null) {
      throw new NullPointerException();
    }
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
      String line;
      while ((line = reader.readLine()) != null) {
        processFileLine(line);
      }
    } catch (FileNotFoundException e) {
      System.out.println("Файл " + filename + " не найден и не будет обработан");
    } catch (IOException e) {
      System.out.println("Ошибка при чтении файла: " + e.getMessage() + ". Файл не будет обработан");
    }
  }

  /**
   * Обрабатывает строку из файла. Если строка представляет собой дробное число, то функция сохраняет это
   * число во внутреннем списке, аналогично для целого числа; иначе функция сохраняет строку.
   * @param line Строка файла
   */
  private void processFileLine(String line) {
    try {
      integers.add(Long.parseLong(line));
      return;
    } catch (NumberFormatException e) {}

    try {
      doubles.add(localParseDouble(line));
      return;
    } catch (NumberFormatException e) {}

    strings.add(line);
  }


  private double localParseDouble(String line) {
    double val = Double.parseDouble(line);
    char[] doubleDefiningChars = new char[] {'E', 'e', '.'};
    boolean containsDoubleDefiningChar = false;
    for (int i = 0; i < doubleDefiningChars.length; i++) {
      if (line.indexOf(doubleDefiningChars[i]) >= 0) {
        containsDoubleDefiningChar = true;
      }
    }
    if (!Double.isFinite(val) || !containsDoubleDefiningChar) {
      throw new NumberFormatException();
    }

    return val;
  }
}
