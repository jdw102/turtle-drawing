package oolala;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import oolala.Command.CmdName;

public class LSystemParser {

  public static final int DEFAULT_DIST = 10;
  public static final int DEFAULT_ANGLE = 30;

  private boolean usingRandomDist = false;
  private boolean usingRandomAngle = false;
  private HashMap<Character, String> alphabet;
  private HashMap<Character, String> rules;

  public LSystemParser() {
    alphabet = new HashMap<>();

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
  public ArrayList<Command> parse(String commandString) {
    ArrayList<Command> program = new ArrayList<Command>();

    Scanner scan = new Scanner(commandString);
    while (scan.hasNext()){
      Command c = new Command();
      String prefix = scan.next();
      switch(prefix) {
        case "fd":
          c.prefix = CmdName.FORWARD;
          c.param = scan.nextInt();
          break;
        case "bk":
          c.prefix = CmdName.BACK;
          c.param = scan.nextInt();
          break;
        case "lt":
          c.prefix = CmdName.LEFT;
          c.param = scan.nextInt();
          break;
        case "rt":
          c.prefix = CmdName.RIGHT;
          c.param = scan.nextInt();
          break;
        case "pd":
          c.prefix = CmdName.PENDOWN;
          break;
        case "pu":
          c.prefix = CmdName.PENUP;
          break;
        case "st":
          c.prefix = CmdName.SHOWT;
          break;
        case "ht":
          c.prefix = CmdName.HIDET;
          break;
        case "home":
          c.prefix = CmdName.HOME;
          break;
        case "stamp":
          c.prefix = CmdName.STAMP;
          break;
        case "tell":
          c.prefix = CmdName.TELL;
          c.params = new ArrayList<Integer>();
          while (scan.hasNextInt())
            c.params.add(scan.nextInt());
          break;
        default:
          // TODO: Handle bad input
          System.err.println("Unrecognized Command!");
          break;
      }
      program.add(c);
    }
    return program;
  }
}
