package FileFilter;

/**
 * Исключение для класса ArgsHandler.
 */
public class ArgsHandlerException extends Exception {
  /**
   * Конструктор простого исключения.
   */
  public ArgsHandlerException() { super(); }

  /**
   * Конструктор исключения с возможностью задать сообщение.
   * @param message Сообщение.
   */
  public ArgsHandlerException(String message) { super(message); }
}
