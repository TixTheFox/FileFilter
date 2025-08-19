package FileFilter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ArgsHandlerTest {

  @Test
  void ArgHandler_EmptyArgs() {
    ArgsHandler argsHandler = new ArgsHandler();

    String[] args = {};

    Assertions.assertThrows(ArgsHandlerException.class, () -> argsHandler.processArgs(args));
  }

  @Test
  void ArgHandler_NoFlags_OneFile() {
    ArgsHandler argsHandler = new ArgsHandler();

    String[] args = {"resources/in1.txt"};

    try{
      argsHandler.processArgs(args);
    } catch (ArgsHandlerException e) {
      System.out.println(e.getMessage());
      Assertions.fail();
    }

    Assertions.assertEquals(argsHandler.getInputFiles().get(0), "resources/in1.txt");
    Assertions.assertEquals(argsHandler.getPrefix(), "");
    Assertions.assertEquals(argsHandler.getOutputDir(), "");
    Assertions.assertFalse(argsHandler.haveToAppend());
    Assertions.assertNull(argsHandler.getStatForm());
  }

  @Test
  void ArgHandler_NoFlags_MultipleFiles() {
    ArgsHandler argsHandler = new ArgsHandler();

    String[] args = {"resources/in1.txt", "test2.txt"};

    try{
      argsHandler.processArgs(args);
    } catch (ArgsHandlerException e) {
      System.out.println(e.getMessage());
      Assertions.fail();
    }

    Assertions.assertEquals(argsHandler.getInputFiles().get(0), "resources/in1.txt");
    Assertions.assertEquals(argsHandler.getInputFiles().get(1), "test2.txt");
    Assertions.assertEquals(argsHandler.getPrefix(), "");
    Assertions.assertEquals(argsHandler.getOutputDir(), "");
    Assertions.assertFalse(argsHandler.haveToAppend());
    Assertions.assertNull(argsHandler.getStatForm());
  }

  @Test
  void ArgHandler_FlagsAfterFiles() {
    ArgsHandler argsHandler = new ArgsHandler();

    String[] args = {"resources/in1.txt", "test2.txt", "-s", "-a"};

    try{
      argsHandler.processArgs(args);
    } catch (ArgsHandlerException e) {
      System.out.println(e.getMessage());
      Assertions.fail();
    }

    Assertions.assertEquals(argsHandler.getInputFiles().get(0), "resources/in1.txt");
    Assertions.assertEquals(argsHandler.getInputFiles().get(1), "test2.txt");
    Assertions.assertEquals(argsHandler.getInputFiles().get(2), "-s");
    Assertions.assertEquals(argsHandler.getInputFiles().get(3), "-a");
    Assertions.assertEquals(argsHandler.getPrefix(), "");
    Assertions.assertEquals(argsHandler.getOutputDir(), "");
    Assertions.assertFalse(argsHandler.haveToAppend());
    Assertions.assertNull(argsHandler.getStatForm());
  }

  @Test
  void ArgHandler_FlagsOnly() {
    ArgsHandler argsHandler = new ArgsHandler();

    String[] args = {"-s", "-a"};

    Assertions.assertThrows(ArgsHandlerException.class, () -> argsHandler.processArgs(args));
  }

  @Test
  void ArgHandler_UnknownFlag() {
    ArgsHandler argsHandler = new ArgsHandler();

    String[] args = {"-s", "-a", "-u", "resources/in1.txt"};

    Assertions.assertThrows(ArgsHandlerException.class, () -> argsHandler.processArgs(args));
  }

  @Test
  void ArgHandler_StatFlag_Short() {
    ArgsHandler argsHandler = new ArgsHandler();

    String[] args = {"-s", "resources/in1.txt", "test2.txt"};

    try{
      argsHandler.processArgs(args);
    } catch (ArgsHandlerException e) {
      System.out.println(e.getMessage());
      Assertions.fail();
    }

    Assertions.assertEquals(argsHandler.getInputFiles().get(0), "resources/in1.txt");
    Assertions.assertEquals(argsHandler.getInputFiles().get(1), "test2.txt");
    Assertions.assertEquals(argsHandler.getPrefix(), "");
    Assertions.assertEquals(argsHandler.getOutputDir(), "");
    Assertions.assertFalse(argsHandler.haveToAppend());
    Assertions.assertEquals(argsHandler.getStatForm(), ArgsHandler.StatForm.SHORT);
  }

  @Test
  void ArgHandler_StatFlag_Full() {
    ArgsHandler argsHandler = new ArgsHandler();

    String[] args = {"-f", "resources/in1.txt", "test2.txt"};

    try{
      argsHandler.processArgs(args);
    } catch (ArgsHandlerException e) {
      System.out.println(e.getMessage());
      Assertions.fail();
    }

    Assertions.assertEquals(argsHandler.getInputFiles().get(0), "resources/in1.txt");
    Assertions.assertEquals(argsHandler.getInputFiles().get(1), "test2.txt");
    Assertions.assertEquals(argsHandler.getPrefix(), "");
    Assertions.assertEquals(argsHandler.getOutputDir(), "");
    Assertions.assertFalse(argsHandler.haveToAppend());
    Assertions.assertEquals(argsHandler.getStatForm(), ArgsHandler.StatForm.FULL);
  }

  @Test
  void ArgHandler_StatFlag_MultipleDifferentFlags() {
    ArgsHandler argsHandler = new ArgsHandler();

    String[] args = {"-s", "-f", "resources/in1.txt", "test2.txt"};

    Assertions.assertThrows(ArgsHandlerException.class, () -> argsHandler.processArgs(args));
  }

  @Test
  void ArgHandler_PrefixFlag() {
    ArgsHandler argsHandler = new ArgsHandler();

    String[] args = {"-p", "pref", "resources/in1.txt", "test2.txt"};

    try{
      argsHandler.processArgs(args);
    } catch (ArgsHandlerException e) {
      System.out.println(e.getMessage());
      Assertions.fail();
    }

    Assertions.assertEquals(argsHandler.getInputFiles().get(0), "resources/in1.txt");
    Assertions.assertEquals(argsHandler.getInputFiles().get(1), "test2.txt");
    Assertions.assertEquals(argsHandler.getPrefix(), "pref");
    Assertions.assertEquals(argsHandler.getOutputDir(), "");
    Assertions.assertFalse(argsHandler.haveToAppend());
    Assertions.assertNull(argsHandler.getStatForm());
  }

  @Test
  void ArgHandler_PrefixFlag_PrefixDefinedBeforeFlag() {
    ArgsHandler argsHandler = new ArgsHandler();

    String[] args = {"pref", "-p", "resources/in1.txt", "test2.txt"};

    try{
      argsHandler.processArgs(args);
    } catch (ArgsHandlerException e) {
      System.out.println(e.getMessage());
      Assertions.fail();
    }

    Assertions.assertEquals(argsHandler.getInputFiles().get(0), "pref");
    Assertions.assertEquals(argsHandler.getInputFiles().get(1), "-p");
    Assertions.assertEquals(argsHandler.getInputFiles().get(2), "resources/in1.txt");
    Assertions.assertEquals(argsHandler.getInputFiles().get(3), "test2.txt");
    Assertions.assertEquals(argsHandler.getPrefix(), "");
    Assertions.assertEquals(argsHandler.getOutputDir(), "");
    Assertions.assertFalse(argsHandler.haveToAppend());
    Assertions.assertNull(argsHandler.getStatForm());
  }

  @Test
  void ArgHandler_PrefixFlag_InvalidPrefix() {
    ArgsHandler argsHandler = new ArgsHandler();

    String[] args = {"-p", "/s", "resources/in1.txt", "test2.txt"};

    Assertions.assertThrows(ArgsHandlerException.class, () -> argsHandler.processArgs(args));
  }

  @Test
  void ArgHandler_OutputDirFlag() {
    ArgsHandler argsHandler = new ArgsHandler();

    String[] args = {"-o", "dir", "resources/in1.txt", "test2.txt"};

    try{
      argsHandler.processArgs(args);
    } catch (ArgsHandlerException e) {
      System.out.println(e.getMessage());
      Assertions.fail();
    }

    Assertions.assertEquals(argsHandler.getInputFiles().get(0), "resources/in1.txt");
    Assertions.assertEquals(argsHandler.getInputFiles().get(1), "test2.txt");
    Assertions.assertEquals(argsHandler.getPrefix(), "");
    Assertions.assertEquals(argsHandler.getOutputDir(), "dir");
    Assertions.assertFalse(argsHandler.haveToAppend());
    Assertions.assertNull(argsHandler.getStatForm());
  }

  @Test
  void ArgHandler_AppendFlag() {
    ArgsHandler argsHandler = new ArgsHandler();

    String[] args = {"-a", "resources/in1.txt", "test2.txt"};

    try{
      argsHandler.processArgs(args);
    } catch (ArgsHandlerException e) {
      System.out.println(e.getMessage());
      Assertions.fail();
    }

    Assertions.assertEquals(argsHandler.getInputFiles().get(0), "resources/in1.txt");
    Assertions.assertEquals(argsHandler.getInputFiles().get(1), "test2.txt");
    Assertions.assertEquals(argsHandler.getPrefix(), "");
    Assertions.assertEquals(argsHandler.getOutputDir(), "");
    Assertions.assertTrue(argsHandler.haveToAppend());
    Assertions.assertNull(argsHandler.getStatForm());
  }

  @Test
  void ArgHandler_AllFlags_Order_1() {
    ArgsHandler argsHandler = new ArgsHandler();

    String[] args = {"-a", "-o", "outdir", "-p", "pref", "-f", "resources/in1.txt", "test2.txt"};

    try{
      argsHandler.processArgs(args);
    } catch (ArgsHandlerException e) {
      System.out.println(e.getMessage());
      Assertions.fail();
    }

    Assertions.assertEquals(argsHandler.getInputFiles().get(0), "resources/in1.txt");
    Assertions.assertEquals(argsHandler.getInputFiles().get(1), "test2.txt");
    Assertions.assertEquals(argsHandler.getPrefix(), "pref");
    Assertions.assertEquals(argsHandler.getOutputDir(), "outdir");
    Assertions.assertTrue(argsHandler.haveToAppend());
    Assertions.assertEquals(argsHandler.getStatForm(), ArgsHandler.StatForm.FULL);
  }

  @Test
  void ArgHandler_AllFlags_Order_2() {
    ArgsHandler argsHandler = new ArgsHandler();

    String[] args = {"-s", "-o", "outdir", "-a", "-p", "pref", "resources/in1.txt", "test2.txt"};

    try{
      argsHandler.processArgs(args);
    } catch (ArgsHandlerException e) {
      System.out.println(e.getMessage());
      Assertions.fail();
    }

    Assertions.assertEquals(argsHandler.getInputFiles().get(0), "resources/in1.txt");
    Assertions.assertEquals(argsHandler.getInputFiles().get(1), "test2.txt");
    Assertions.assertEquals(argsHandler.getPrefix(), "pref");
    Assertions.assertEquals(argsHandler.getOutputDir(), "outdir");
    Assertions.assertTrue(argsHandler.haveToAppend());
    Assertions.assertEquals(argsHandler.getStatForm(), ArgsHandler.StatForm.SHORT);
  }
}