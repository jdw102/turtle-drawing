package oolala;

import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.SpringLayout;
import oolala.Command.CmdName;

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
    ArrayList<Command> program = new ArrayList<>();

    commandString = commandString.toLowerCase();
    Scanner scan = new Scanner(commandString);
    while (scan.hasNext()){
      Command c = new Command();
      String prefix = scan.next();
      if(prefix.charAt(0) == '#'){
        scan.nextLine();
        continue;
      }
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
