package oolala;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import oolala.Command.CmdName;

public class LSystemParser {

  public static final char[] ALPHA_SYM = {'f', 'g', 'a', 'b', '+', '-', 'x'};
  public static final String[] ALPHA_COMM = {"pd fd ", "pu fd ", "pu bk ", "pd bk ",
                                                "rt ", "lt ", "stamp"};
  public static final int DEFAULT_DIST = 10;
  public static final int DEFAULT_ANGLE = 30;

  private boolean usingRandomDist = false;
  private boolean usingRandomAngle = false;
  private int distMin = DEFAULT_DIST;
  private int distMax = DEFAULT_DIST;
  private int angMin = DEFAULT_ANGLE;
  private int angMax = DEFAULT_ANGLE;
  private HashMap<Character, String> alphabet;
  private HashMap<Character, String> rules;
  private String start;

  public LSystemParser() {
    alphabet = new HashMap<>();
    for(int i = 0; i < ALPHA_COMM.length; i++) {
      alphabet.put(ALPHA_SYM[i], ALPHA_COMM[i]);
    }
    rules = new HashMap<>();
  }

  /**
   * A function that expands the given root according to the given rules.
   *
   * @param root - The root string, or the initial string to start expanding
   * @param level - The number of times to perform the expansion
   * @return a String object containing the root string expanded the specified number of times
   */
  private String applyRules(String root, int level) {
    String expanded = root;
    for(int i = 0; i < level; i++){
      String nextLevel = "";
      for(int j = 0; j < expanded.length(); j++){
        if(rules.containsKey(expanded.charAt(j))){
          nextLevel.concat(rules.get(expanded.charAt(j)));
        }
      }
      expanded = nextLevel;
    }
    return expanded;
  }

  /**
   * A method to parse user-given Logo commands from the app console.
   * See https://courses.cs.duke.edu/compsci307d/fall22/assign/02_oolala/logo.php
   * for list of supported commands.
   *
   * @author Aditya Paul
   * @param commandString - A string from the console containing all the
   *                        commands given to the application by the user
   * @return An ArrayList of the parsed commands
   */
  public void parse(String commandString) {
    commandString = commandString.toLowerCase();
    Scanner scan = new Scanner(commandString);

    char symbol;
    String expansion;
    while (scan.hasNext()){
      String prefix = scan.next();
      switch(prefix) {
        case "start":
          start = scan.next();
          break;
        case "rule":
          symbol = scan.next().charAt(0);
          expansion = scan.next();
          rules.put(symbol, expansion);
          break;
        case "randomd":
          usingRandomDist = true;
          distMin = scan.nextInt();
          distMax = scan.nextInt();
          break;
        case "randoma":
          usingRandomAngle = true;
          angMin = scan.nextInt();
          angMax = scan.nextInt();
          break;
        case "set":
          symbol = scan.next().charAt(0);
          expansion = scan.next();
          alphabet.put(symbol, expansion);
          break;
        default:
          // TODO: Handle bad input
          System.err.println("Unrecognized Command!");
          break;
      }
    }
  }
}
