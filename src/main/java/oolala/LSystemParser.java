package oolala;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class LSystemParser {

  public static final char[] ALPHA_SYM = {'f', 'g', 'a', 'b', '+', '-', 'x'};
  public static final String[] ALPHA_COMM = {"pd fd ", "pu fd ", "pu bk ", "pd bk ",
                                                "rt ", "lt ", "stamp"};
  public static final int DEFAULT_DIST = 10;
  public static final int DEFAULT_ANGLE = 30;
  public static final int DEFAULT_LEVEL = 3;

  private boolean usingRandomDist = false;
  private boolean usingRandomAngle = false;
  private int distMin = DEFAULT_DIST;
  private int distMax = DEFAULT_DIST;
  private int angMin = DEFAULT_ANGLE;
  private int angMax = DEFAULT_ANGLE;
  private int dist = DEFAULT_DIST;
  private int ang = DEFAULT_ANGLE;
  private int level = DEFAULT_LEVEL;
  private HashMap<Character, String> alphabet;
  private HashMap<Character, String> rules;
  private String start;
  private Parser logoParser;

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
   * @return a String object containing the root string expanded the specified number of times
   */
  private String applyRules() {
    String expanded = start;
    for(int i = 0; i < getLevel(); i++){
      String nextLevel = "";
      for(int j = 0; j < expanded.length(); j++){
        if(rules.containsKey(expanded.charAt(j))){
          nextLevel = nextLevel.concat(rules.get(expanded.charAt(j)));
        }
      }
      expanded = nextLevel;
    }
    return expanded;
  }

  private String getCommandString(String expansion){
    String commandString = "";
    for(int i = 0; i < expansion.length(); i++){
      char currChar = expansion.charAt(i);
      if(alphabet.containsKey(currChar)) {
        String cmd = alphabet.get(currChar);
        if(cmd.substring(cmd.length() - 3).equals("fd ") ||
            cmd.substring(cmd.length() - 3).equals("bk ")){
          cmd = cmd.concat(Integer.toString(dist));
        } else if(cmd.substring(cmd.length() - 3).equals("lt ") ||
            cmd.substring(cmd.length() - 3).equals("rt ")) {
          cmd = cmd.concat(Integer.toString(ang));
        }
        commandString = commandString.concat(cmd).concat(" ");
      }
    }
    return commandString;
  }

  /**
   * A method to parse user-given Logo commands from the app console.
   * See https://courses.cs.duke.edu/compsci307d/fall22/assign/02_oolala/logo.php
   * for list of supported commands.
   *
   * @author Aditya Paul
   * @param configString - A string from the console containing all the
   *                        commands given to the application by the user
   * @return An ArrayList of the parsed commands
   */
  public void parseConfig(String configString) {
    configString = configString.toLowerCase();
    Scanner scan = new Scanner(configString);

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

  public int getDist() {
    return dist;
  }

  public void setDist(int dist) {
    this.dist = dist;
  }

  public int getAng() {
    return ang;
  }

  public void setAng(int ang) {
    this.ang = ang;
  }

  public int getLevel() {
    return level;
  }

  public void setLevel(int level) {
    this.level = level;
  }

}
