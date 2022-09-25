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
   * @param textbox - The IDE's textbox. In the future, we plan on making this
   *                  a rich text area for syntax error highlighting.
   * @return An ArrayList of the parsed commands
   */
  public ArrayList<Command> parse(TextBox textbox) {
    ArrayList<Command> program = new ArrayList<>();

    String commandString = textbox.getTextArea().getText().toLowerCase();
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
          if (scan.hasNextInt())
            c.param = scan.nextInt();
          else {
            // TODO: Handle
            System.err.println("Missing parameters for FD command!");
            return new ArrayList<>();
          }
          break;
        case "bk":
          c.prefix = CmdName.BACK;
          if (scan.hasNextInt())
            c.param = scan.nextInt();
          else {
            // TODO: Handle
            System.err.println("Missing parameters for BK command!");
            return new ArrayList<>();
          }
          break;
        case "lt":
          c.prefix = CmdName.LEFT;
          if (scan.hasNextInt())
            c.param = scan.nextInt();
          else {
            // TODO: Handle
            System.err.println("Missing parameters for LT command!");
            return new ArrayList<>();
          }
          break;
        case "rt":
          c.prefix = CmdName.RIGHT;
          if (scan.hasNextInt())
            c.param = scan.nextInt();
          else {
            // TODO: Handle
            System.err.println("Missing parameters for RT command!");
            return new ArrayList<>();
          }
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
          if (!scan.hasNextInt()){
            // TODO: Handle
            System.err.println("Missing parameters for TELL command!");
            return new ArrayList<>();
          }
          while (scan.hasNextInt())
            c.params.add(scan.nextInt());
          break;
        default:
          // TODO: Handle bad input
          System.err.println("Unrecognized Command!");
          return new ArrayList<>();
      }
      program.add(c);
    }
    return program;
  }
}
