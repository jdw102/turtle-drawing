package oolala;

import java.util.ArrayList;

/**
 * The internal parser for each Logo command.
 *
 * @author Aditya Paul
 */
public class Parser {

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

    String[] programTokens = commandString.split("\\s+");  // Regex to split on all whitespace
    int currToken = 0;
    while (currToken < programTokens.length){
      Command c = new Command();
      String prefix = programTokens[currToken];
      currToken++;
      switch(prefix) {
        case "fd":

          break;
        case "bk":

          break;
        case "lt":

          break;
        case "rt":

          break;
        case "pd":

          break;
        case "pu":

          break;
        case "st":

          break;
        case "ht":

          break;
        case "home":

          break;
        case "stamp":

          break;
        case "tell":

          break;
      }
    }

    return program;
  }
}
