package oolala;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ResourceBundle;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class LSystemParserTest {
  private static final String DEFAULT_RESOURCE_PACKAGE = "Properties.";
  private static final String DEFAULT_LANGUAGE = "English";

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
    lparser.parseConfig("START F RULE F F-F++F-F");
    lparser.setLevel(level);
    assertEquals(expansion.toLowerCase(), lparser.applyRules());
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
    lparser.parseConfig("START F RULE F F-F++F-F");
    lparser.setLevel(level);
    assertEquals(commandString.toLowerCase(), lparser.getCommandString(lparser.applyRules()).trim());
  }
}