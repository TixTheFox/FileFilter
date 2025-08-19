package FileFilter;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;

/**
 * Основной класс, решающий задачу.
 */
public class FileFilter {
  /**
   * Класс-перечисление, который для фильтруемых типов данных определяет
   * базовое имя их выходного файла.
   */
  private enum OutputFilesForTypes {
    INTEGER("integers.txt"), DOUBLE("floats.txt"), STRING("strings.txt");

    private String filePath;
    OutputFilesForTypes(String filePath) {
      this.filePath = filePath;
    }
    public String getPath() { return filePath; }
  }

  private ArgsHandler argsHandler;
  private FileProcessor fileProcessor;

  /**
   * Конструктор. Инициализирует экземпляр класса.
   */
  public FileFilter() {
    argsHandler = new ArgsHandler();
    fileProcessor = new FileProcessor();
  }

  /**
   * Основная функция класса, последовательно запускает парсинг аргументов, обработку файлов,
   * вывод статистики (если необходимо) и сохранение результатов.
   * @param args Аргументы командной строки.
   */
  public void start(String[] args) {
    // парсинг аргументов командной строки с помощью argsHandler
    try{
      argsHandler.processArgs(args);
    } catch (ArgsHandlerException e) {
      System.out.println("Ошибка при обработке аргументов запуска: " + e.getMessage());
      return;
    }

    // обработка файлов с помощью fileProcessor
    fileProcessor.processFiles(argsHandler.getInputFiles());

    // вывод статистики по полученным данным
    if (argsHandler.getStatForm() == ArgsHandler.StatForm.SHORT) {
      StatsPrinter.shortStatPrint(fileProcessor.doubles, fileProcessor.integers, fileProcessor.strings);
    } else if (argsHandler.getStatForm() == ArgsHandler.StatForm.FULL) {
      StatsPrinter.fullStatPrint(fileProcessor.doubles, fileProcessor.integers, fileProcessor.strings);
    }

    // сохранение в выходные файлы
    save();
  }

  /**
   * Функция вызывает saveType для каждого из обрабатываемых типов данных.
   */
  private void save() {
    saveType(OutputFilesForTypes.DOUBLE);
    saveType(OutputFilesForTypes.INTEGER);
    saveType(OutputFilesForTypes.STRING);
  }

  /**
   * Функция сохраняет элементы заданного типа в соответствующий файл (с учетом директории, префикса, append-флага).
   * Если директории не существует, она будет создана.
   * Если файла не существует, он будет создан.
   * @param type Тип данных, который будет сохранен.
   */
  private void saveType(OutputFilesForTypes type) {
    // определяем список элементов, которые будут записываться в файл
    ArrayList<?> list = null;
    switch (type) {
      case DOUBLE:
        list = fileProcessor.doubles;
        break;
      case STRING:
        list = fileProcessor.strings;
        break;
      case INTEGER:
        list = fileProcessor.integers;
        break;
    }

    if (!list.isEmpty()) {
      Path dir = Paths.get(argsHandler.getOutputDir());
      Path path = dir.resolve(argsHandler.getPrefix() + type.getPath());

      // создаем директорию
      try {
        Files.createDirectories(dir);
      } catch (IOException e) {
        System.out.println("Ошибка при создании директории для результатов: " + e.getMessage());
      }

      // Определяем формат записи (перезапись / запись в конец)
      OpenOption appendOrTruncateOption = null;
      if (argsHandler.haveToAppend()){
        appendOrTruncateOption = StandardOpenOption.APPEND;
      } else {
        appendOrTruncateOption = StandardOpenOption.TRUNCATE_EXISTING;
      }

      // Открываем файл и пишем в него. Если его не существует, он будет создан
      try (BufferedWriter writer = Files.newBufferedWriter(path, appendOrTruncateOption, StandardOpenOption.CREATE)) {
        for (var item : list) {
          writer.write(item + "\n");
        }
      } catch(IOException e) {
        System.out.println("Ошибка при записи в файл " + path + ": " + e.getMessage());
      }
    }
  }
}
