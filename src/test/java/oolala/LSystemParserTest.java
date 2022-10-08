package oolala;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ResourceBundle;

import oolala.Parsers.LSystemParser;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class LSystemParserTest {
  public static final String DEFAULT_RESOURCE_PACKAGE = "Properties.";
  public static final String DEFAULT_LANGUAGE = "English";

  LSystemParser lparser = new LSystemParser(ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + DEFAULT_LANGUAGE));

  @ParameterizedTest
  @CsvSource({
      "0, F",
      "1, F-F++F-F",
      "2, F-F++F-F-F-F++F-F++F-F++F-F-F-F++F-F",
      "3, F-F++F-F-F-F++F-F++F-F++F-F-F-F++F-F-F-F++F-F-F"
          + "-F++F-F++F-F++F-F-F-F++F-F++F-F++F-F-F-F++F-F+"
          + "+F-F++F-F-F-F++F-F-F-F++F-F-F-F++F-F++F-F++F-F-F-F++F-F"
  })
  void testApplyRules(int level, String expansion) {
    lparser.parseConfiguration("START F RULE F F-F++F-F");
    lparser.setLevel(level);
    assertEquals(expansion.toLowerCase(), lparser.applyRules());
  }

  @ParameterizedTest
  @CsvSource({
      "START A SET A \"pd fd LENGTH\", pd fd 10 ",
      "start T rule T F-G-G set G \"pd fd LENGTH\", pd fd 10 lt 30 pd fd 10 lt 30 pd fd 10 ",
      "start X, stamp"
  })
  void testSetCommand(String program, String commandString){
    lparser.setLevel(1);
    lparser.parseConfiguration(program);
    assertEquals(commandString.toLowerCase(), lparser.getCommandString(lparser.applyRules()).trim());
  }

  @ParameterizedTest
  @CsvSource({
      "0, pd fd 10 ",
      "1, pd fd 10 lt 30 pd fd 10 rt 30 rt 30 pd fd 10 lt 30 pd fd 10 ",
      "2, pd fd 10 lt 30 pd fd 10 rt 30 rt 30 pd fd 10 lt 30 pd fd 10 "
          + "lt 30 pd fd 10 lt 30 pd fd 10 rt 30 rt 30 pd fd 10 lt 30 "
          + "pd fd 10 rt 30 rt 30 pd fd 10 lt 30 pd fd 10 rt 30 rt 30 "
          + "pd fd 10 lt 30 pd fd 10 lt 30 pd fd 10 lt 30 pd fd 10 rt "
          + "30 rt 30 pd fd 10 lt 30 pd fd 10 "
  })
  void testGetCommandString(int level, String commandString) {
    lparser.parseConfiguration("START F RULE F F-F++F-F");
    lparser.setLevel(level);
    assertEquals(commandString.toLowerCase(), lparser.getCommandString(lparser.applyRules()).trim());
  }
}