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
    logoParser = new Parser();
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
          if (usingRandomDist) {
            dist = (int) Math.round(Math.random() * (distMax - distMin) + distMin);
          }
          cmd = cmd.concat(Integer.toString(dist));
        } else if(cmd.substring(cmd.length() - 3).equals("lt ") ||
            cmd.substring(cmd.length() - 3).equals("rt ")) {
          if (usingRandomAngle) {
            ang = (int) Math.round(Math.random() * (angMax - angMin) + angMin);
          }
          cmd = cmd.concat(Integer.toString(ang));
        }
        commandString = commandString.concat(cmd).concat(" ");
      }
    }
    return commandString;
  }

  /**
   * A method to configure the parser based on the inputs given in the
   * IDE text box.
   *
   * @author Aditya Paul
   * @param configString - A string from the console containing all the
   *                        commands given to the application by the user
   */
  public void parseConfig(String configString) {
    configString = configString.toLowerCase();
    Scanner scan = new Scanner(configString);

    char symbol;
    String expansion;
    while (scan.hasNext()){
      String prefix = scan.next();
      switch (prefix) {
        case "start" -> start = scan.next();
        case "rule" -> {
          symbol = scan.next().charAt(0);
          expansion = scan.next();
          rules.put(symbol, expansion);
        }
        case "randomd" -> {
          usingRandomDist = true;
          distMin = scan.nextInt();
          distMax = scan.nextInt();
        }
        case "randoma" -> {
          usingRandomAngle = true;
          angMin = scan.nextInt();
          angMax = scan.nextInt();
        }
        case "set" -> {
          symbol = scan.next().charAt(0);
          expansion = scan.next();
          alphabet.put(symbol, expansion);
        }
        default ->
          // TODO: Handle bad input
            System.err.println("Unrecognized Command!");
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

  public ArrayList<Command> parse(String input){
    parseConfig(input);
    return logoParser.parse(getCommandString(applyRules()));
  }
}
