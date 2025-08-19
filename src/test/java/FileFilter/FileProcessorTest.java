package FileFilter;

import java.net.URL;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FileProcessorTest {
  static final double EPSILON = 1e-7;

  @Test
  void ProcessSingleFile_1() {
    FileProcessor fp = new FileProcessor();

    URL testFile = getClass().getClassLoader().getResource("in1.txt");
    fp.processFile(testFile.getFile());

    Assertions.assertEquals(fp.doubles.get(0), 3.1415, EPSILON);
    Assertions.assertEquals(fp.doubles.get(1), -0.001, EPSILON);

    Assertions.assertEquals(fp.integers.get(0), 45);
    Assertions.assertEquals(fp.integers.get(1), 100500);

    Assertions.assertEquals(fp.strings.get(0), "Lorem ipsum dolor sit amet");
    Assertions.assertEquals(fp.strings.get(1), "Пример");
    Assertions.assertEquals(fp.strings.get(2), "consectetur adipiscing");
    Assertions.assertEquals(fp.strings.get(3), "тестовое задание");
  }

  @Test
  void ProcessSingleFile_2() {
    FileProcessor fp = new FileProcessor();

    URL testFile = getClass().getClassLoader().getResource("in2.txt");
    fp.processFile(testFile.getFile());

    Assertions.assertEquals(fp.doubles.get(0), 1.528535047E-25, EPSILON);

    Assertions.assertEquals(fp.integers.get(0), 1234567890123456789L);

    Assertions.assertEquals(fp.strings.get(0), "Нормальная форма числа с плавающей запятой");
    Assertions.assertEquals(fp.strings.get(1), "Long");
  }

  @Test
  void ProcessMultipleFiles_Order_1() {
    FileProcessor fp = new FileProcessor();

    URL testFile1 = getClass().getClassLoader().getResource("in1.txt");
    URL testFile2 = getClass().getClassLoader().getResource("in2.txt");
    ArrayList<String> files = new ArrayList<>();
    files.add(testFile1.getFile());
    files.add(testFile2.getFile());

    fp.processFiles(files);

    Assertions.assertEquals(fp.doubles.get(0), 3.1415, EPSILON);
    Assertions.assertEquals(fp.doubles.get(1), -0.001, EPSILON);
    Assertions.assertEquals(fp.doubles.get(2), 1.528535047E-25, EPSILON);

    Assertions.assertEquals(fp.integers.get(0), 45);
    Assertions.assertEquals(fp.integers.get(1), 100500);
    Assertions.assertEquals(fp.integers.get(2), 1234567890123456789L);

    Assertions.assertEquals(fp.strings.get(0), "Lorem ipsum dolor sit amet");
    Assertions.assertEquals(fp.strings.get(1), "Пример");
    Assertions.assertEquals(fp.strings.get(2), "consectetur adipiscing");
    Assertions.assertEquals(fp.strings.get(3), "тестовое задание");
    Assertions.assertEquals(fp.strings.get(4), "Нормальная форма числа с плавающей запятой");
    Assertions.assertEquals(fp.strings.get(5), "Long");
  }

  @Test
  void ProcessMultipleFiles_Order_2() {
    FileProcessor fp = new FileProcessor();

    URL testFile1 = getClass().getClassLoader().getResource("in1.txt");
    URL testFile2 = getClass().getClassLoader().getResource("in2.txt");
    ArrayList<String> files = new ArrayList<>();
    // другой порядок файлов
    files.add(testFile2.getFile());
    files.add(testFile1.getFile());

    fp.processFiles(files);

    Assertions.assertEquals(fp.doubles.get(0), 1.528535047E-25, EPSILON);
    Assertions.assertEquals(fp.doubles.get(1), 3.1415, EPSILON);
    Assertions.assertEquals(fp.doubles.get(2), -0.001, EPSILON);

    Assertions.assertEquals(fp.integers.get(0), 1234567890123456789L);
    Assertions.assertEquals(fp.integers.get(1), 45);
    Assertions.assertEquals(fp.integers.get(2), 100500);

    Assertions.assertEquals(fp.strings.get(0), "Нормальная форма числа с плавающей запятой");
    Assertions.assertEquals(fp.strings.get(1), "Long");
    Assertions.assertEquals(fp.strings.get(2), "Lorem ipsum dolor sit amet");
    Assertions.assertEquals(fp.strings.get(3), "Пример");
    Assertions.assertEquals(fp.strings.get(4), "consectetur adipiscing");
    Assertions.assertEquals(fp.strings.get(5), "тестовое задание");
  }

  @Test
  void ProcessSingleFile_SpecificInput() {
    FileProcessor fp = new FileProcessor();

    URL testFile = getClass().getClassLoader().getResource("in3_specificInput.txt");
    fp.processFile(testFile.getFile());

    Assertions.assertEquals(fp.doubles.get(0), -1e-20, EPSILON);
    Assertions.assertEquals(fp.doubles.get(1), -1e-10, EPSILON);

    Assertions.assertEquals(fp.integers.get(0), 0);

    Assertions.assertEquals(fp.strings.get(0), "");
    Assertions.assertEquals(fp.strings.get(1), "  ");
    Assertions.assertEquals(fp.strings.get(2), "13w0");
    Assertions.assertEquals(fp.strings.get(3), "13.-1");
    Assertions.assertEquals(fp.strings.get(4), "-1e-20;");
    Assertions.assertEquals(fp.strings.get(5), "NaN");
    Assertions.assertEquals(fp.strings.get(6), "Nan");
    Assertions.assertEquals(fp.strings.get(7), "INF");
    Assertions.assertEquals(fp.strings.get(8), "Infinity");
    Assertions.assertEquals(fp.strings.get(9), "-Infinity");
  }

  @Test
  void ProcessSingleFile_NoDoubles() {
    FileProcessor fp = new FileProcessor();

    URL testFile = getClass().getClassLoader().getResource("in4_noDoubles.txt");
    fp.processFile(testFile.getFile());

    Assertions.assertTrue(fp.doubles.isEmpty());

    Assertions.assertEquals(fp.integers.get(0), 10291);

    Assertions.assertEquals(fp.strings.get(0), "Str1");
    Assertions.assertEquals(fp.strings.get(1), "str2");
  }
}
