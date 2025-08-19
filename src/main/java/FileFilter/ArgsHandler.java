package FileFilter;

import java.util.ArrayList;

/**
 * Класс обрабатывает аргументы командой строки и сохраняет информацию о полученных файлах
 * и установленных флагах.
 */
public class ArgsHandler {
  /**
   * Класс-перечисление форматов вывода статистики.
   */
  public enum StatForm {
    /**
     * Полная статистики
     */
    FULL,

    /**
     * Короткая статистика
     */
    SHORT}

  /**
   * Файлы, из которых будет производиться чтение информации.
   */
  private ArrayList<String> inputFiles;

  /**
   * Префикс, задаваемый флагом -p.
   */
  private String prefix;

  /**
   * Директория, в которую будут помещены результаты работы утилиты.
   * Задается флагом -o.
   */
  private String outputDir;

  /**
   * Если true, то значения в выходные файлы будут добавляться в конец.
   * Если false, файлы будут перезаписываться.
   */
  private boolean appendFlag;

  /**
   * Определяет форму вывода статистики.
   * null, если флага вывода статистики не задано, иначе значение из StatForm.
   */
  private StatForm statForm;

  /**
   * Конструктор. Инициализирует экземпляр класса значениями по умолчанию
   */
  public ArgsHandler() {
    inputFiles = new ArrayList<>(5);
    prefix = "";
    outputDir = "";
    appendFlag = false;
    statForm = null;
  }

  /**
   * Возвращает список файлов для обработки. По умолчанию пуст.
   * @return список файлов для обработки.
   */
  public ArrayList<String> getInputFiles() {
    return inputFiles;
  }

  /**
   * Возвращает префикс выходных файлов. По умолчанию - пустая строка.
   * @return префикс выходных файлов.
   */
  public String getPrefix() {
    return prefix;
  }

  /**
   * Возвращает директорию выходных файлов. По умолчанию - пустая строка.
   * @return директория выходных файлов.
   */
  public String getOutputDir() {
    return outputDir;
  }

  /**
   * Возвращает true, если запись нужно производить в конец существующих файлов, false иначе
   * @return true, если запись нужно производить в конец существующих файлов, false иначе
   */
  public boolean haveToAppend() {
    return appendFlag;
  }

  /**
   * Возвращает формат вывода статистики в виде значения из StatForm, если формат был указан флагом.
   * Иначе null. По умолчанию null
   * @return формат вывода статистики в виде значения из StatForm, если формат был указан флагом. Иначе null.
   */
  public StatForm getStatForm() {
    return statForm;
  }

  /**
   * Основная функция класса, обрабатывает аргументы командной строки.
   * @param args Аргументы командной строки.
   * @throws ArgsHandlerException при любых некорректных аргументах.
   */
  public void processArgs(String[] args) throws ArgsHandlerException {
    int i = 0;
    while (i < args.length && args[i].startsWith("-")) {
      if (args[i].equals("-o")) {
        i++;
        if (i >= args.length) {
          throw new ArgsHandlerException("Нет аргумента для флага -o");
        }
        outputDir = args[i];
      } else if (args[i].equals("-p")) {
        i++;
        if (i >= args.length) {
          throw new ArgsHandlerException("Нет аргумента для флага -p");
        }
        prefix = args[i];
      } else if (args[i].equals("-a")) {
        appendFlag = true;
      } else if (args[i].equals("-s")) {
        if (statForm != null) {
          throw new ArgsHandlerException("Слишком много флагов статистики");
        }
        statForm = StatForm.SHORT;
      } else if (args[i].equals("-f")) {
        if (statForm != null) {
          throw new ArgsHandlerException("Слишком много флагов статистики");
        }
        statForm = StatForm.FULL;
      } else {
        throw new ArgsHandlerException("Неизвестный флаг");
      }
      i++;
    }

    while(i < args.length) {
      inputFiles.add(args[i]);
      i++;
    }

    if (!isPrefixValid()) {
      throw new ArgsHandlerException("Недопустимый префикс имен файлов");
    }

    if (inputFiles.isEmpty()) {
      throw new ArgsHandlerException("Не обнаружено файлов для фильтрации");
    }
  }

  /**
   * Проверяет, содержит ли префикс символы, которые недопустимы в имени в заданной системе.
   * @return true, если префикс валидный, false иначе.
   */
  private boolean isPrefixValid() {
    if (prefix.isEmpty()) {
      return true;
    }

    char[] invalidChars = null;
    if (System.getProperty("os.name").toLowerCase().contains("win")) {
      invalidChars = new char[] {'\\', '/', ':', '*', '?', '"', '<', '>', '|'};
    } else {
      invalidChars = new char[] {'/', '\0'};
    }

    for (int i = 0; i < invalidChars.length; i++) {
      if (prefix.indexOf(invalidChars[i]) > -1) {
        return false;
      }
    }
    return true;
  }
}
