package oolala;

import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import oolala.Command.*;

/**
 * The internal parser for each Logo command.
 *
 * @author Aditya Paul
 */
public class Parser {
  ResourceBundle myResources;
  public Parser(ResourceBundle resourceBundle){
    myResources = resourceBundle;
  }

  /**
   * A method to parse user-given Logo commands from the app console.
   * See https://courses.cs.duke.edu/compsci307d/fall22/assign/02_oolala/logo.php
   * for list of supported commands.
   *
   * @author Aditya Paul
   * @param textArea - The IDE's textbox. In the future, we plan on making this
   *                  a rich text area for syntax error highlighting.
   * @return An ArrayList of the parsed commands
   */
  public ArrayList<Command> parse(TextArea textArea) {
    ArrayList<Command> program = new ArrayList<>();
    Command c = new Command();
    String commandString = textArea.getText().toLowerCase();
    Scanner scan = new Scanner(commandString);
    while (scan.hasNext()){
//      Command c = new Command();
      String prefix = scan.next();
      if(prefix.charAt(0) == '#'){
        scan.nextLine();
        continue;
      }
      switch(prefix) {
        case "fd":
          c = new CommandForward();
          if (scan.hasNextInt())
            c.setParam(scan.nextInt());
          else {
            // TODO: Handle
            System.err.println("Missing parameters for FD command!");
            return new ArrayList<>();
          }
          break;
        case "bk":
          c = new CommandBackward();
          if (scan.hasNextInt())
            c.setParam(scan.nextInt());
          else {
            // TODO: Handle
            System.err.println("Missing parameters for BK command!");
            return new ArrayList<>();
          }
          break;
        case "lt":
          c = new CommandLeft();
          if (scan.hasNextInt())
            c.setParam(scan.nextInt());
          else {
            // TODO: Handle
            System.err.println("Missing parameters for LT command!");
            return new ArrayList<>();
          }
          break;
        case "rt":
          c = new CommandRight();
          if (scan.hasNextInt())
            c.setParam(scan.nextInt());
          else {
            // TODO: Handle
            System.err.println("Missing parameters for RT command!");
            return new ArrayList<>();
          }
          break;
        case "pd":
          c = new CommandPenDown();
          break;
        case "pu":
          c = new CommandPenUp();
          break;
        case "st":
          c = new CommandShowTurtle();
          break;
        case "ht":
          c  = new CommandHideTurtle();
          break;
        case "home":
          c = new CommandHome();
          break;
        case "stamp":
          c = new CommandStamp();
          break;
        case "tell":
          c = new CommandTell();
          if (!scan.hasNextInt()){
            // TODO: Handle
            System.err.println("Missing parameters for TELL command!");
            return new ArrayList<>();
          }
          while (scan.hasNextInt())
            ((CommandTell) c).getParams().add(scan.nextInt());
          break;
        default:
          // TODO: Handle bad input
          Alert alert = new Alert(Alert.AlertType.ERROR,
                  myResources.getString("CommandError"));
          alert.showAndWait();
          return new ArrayList<>();
      }
      program.add(c);
    }
    return program;
  }

  public void setLanguage(ResourceBundle resources) {
    myResources = resources;
  }
}
