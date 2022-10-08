package oolala.Parsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.scene.control.Alert;
import oolala.Command.*;
import oolala.Parsers.Parser;

/**
 * The internal parser for each Logo command.
 *
 * @author Aditya Paul
 */
public class LogoParser extends Parser {
    ResourceBundle myResources;
    Map<String, Integer> variables;

    public LogoParser(ResourceBundle resourceBundle) {
        myResources = resourceBundle;
        variables = new HashMap<>();
    }

    /**
     * A method to parse user-given Logo commands from the app console. See
     * https://courses.cs.duke.edu/compsci307d/fall22/assign/02_oolala/logo.php for list of supported
     * commands.
     *
     * @param inputText - The IDE's textbox. In the future, we plan on making this a rich text area
     *                  for syntax error highlighting.
     * @return An ArrayList of the parsed commands
     * @author Aditya Paul
     */
    public List<Command> parse(String inputText) {
        program = new ArrayList<>();
        Command c = new Command();
        Scanner scan = new Scanner(inputText);
        while (scan.hasNext()) {
            String tkn = "";
            String prefix = scan.next();
            if (prefix.charAt(0) == '#') {
                scan.nextLine();
                continue;
            }
            switch (prefix) {
                case "fd": case "forward":
                    c = new CommandForward();
                    if (scan.hasNextInt())
                        c.setParam(scan.nextInt());
                    else {
                        tkn = scan.next();
                        if (variables.containsKey(tkn))
                            c.setParam(variables.get(tkn));
                        else
                            return errorAndExit("Missing parameters for FD command!");
                    }
                    break;
                case "bk": case "back":
                    c = new CommandBackward();
                    System.out.println("Current token " + prefix);
                    if (scan.hasNextInt())
                        c.setParam(scan.nextInt());
                    else {
                        tkn = scan.next();
                        if (variables.containsKey(tkn))
                            c.setParam(variables.get(tkn));
                        else
                            return errorAndExit("Missing parameters for BK command!");
                    }
                    break;
                case "lt": case "left":
                    c = new CommandLeft();
                    if (scan.hasNextInt())
                        c.setParam(scan.nextInt());
                    else {
                        tkn = scan.next();
                        if (variables.containsKey(tkn))
                            c.setParam(variables.get(tkn));
                        else
                            return errorAndExit("Missing parameters for LT command!");
                    }
                    break;
                case "rt": case "right":
                    c = new CommandRight();
                    if (scan.hasNextInt())
                        c.setParam(scan.nextInt());
                    else {
                        tkn = scan.next();
                        if (variables.containsKey(tkn))
                            c.setParam(variables.get(tkn));
                        else
                            return errorAndExit("Missing parameters for RT command!");
                    }
                    break;
                case "pd": case "pendown":
                    c = new CommandPenDown();
                    break;
                case "pu": case "penup":
                    c = new CommandPenUp();
                    break;
                case "st": case "show": case "showt":
                    c = new CommandShowTurtle();
                    break;
                case "ht": case "hide": case "hidet":
                    c = new CommandHideTurtle();
                    break;
                case "clearscreen": case "cs":
                    c = new CommandClear();
                    break;
                case "home":
                    c = new CommandHome();
                    break;
                case "stamp":
                    c = new CommandStamp();
                    break;
                case "towards":
                    c = new CommandTowards();
                    for(int i = 0; i < 2; i++) {
                        if (scan.hasNextInt())
                            ((CommandTowards) c).getParams().add(scan.nextInt());
                        else {
                            tkn = scan.next();
                            if (variables.containsKey(tkn))
                                ((CommandTowards) c).getParams().add(variables.get(tkn));
                            else
                                return errorAndExit("Missing parameters for TOWARDS command!");
                        }
                    }
                    break;
                case "goto": case "setxy":
                    c = new CommandGoto();
                    for(int i = 0; i < 2; i++) {
                        if (scan.hasNextInt())
                            ((CommandGoto) c).getParams().add(scan.nextInt());
                        else {
                            tkn = scan.next();
                            if (variables.containsKey(tkn))
                                ((CommandGoto) c).getParams().add(variables.get(tkn));
                            else
                                return errorAndExit("Missing parameters for GOTO/SETXY command!");
                        }
                    }
                    break;
                case "make": case "set":
                    // Get name
                    c = new CommandMake();
                    tkn = scan.next();
                    if(tkn.charAt(0) != ':')
                        return errorAndExit("Incorrect variable name for make command!");
                    System.out.println("Got token " + tkn);
                    // TODO: refactor this into a single function
                    if (scan.hasNextInt())
                        variables.put(tkn.substring(1), scan.nextInt());
                    else {
                        tkn = scan.next();
                        if (variables.containsKey(tkn))
                            variables.put(tkn.substring(1), variables.get(tkn));
                        else
                            return errorAndExit("Incorrect variable value for make command!");
                    }
                    System.out.println("Got variable " + variables.get(tkn.substring(1)));
                    System.out.println(variables.toString());
                    break;
                case "tell":
                    c = new CommandTell();
                    if (!(scan.hasNextInt() || hasNextVariable(scan)))
                        return errorAndExit("Missing parameters for TELL command!");
                    while (scan.hasNextInt() || hasNextVariable(scan)) {
                        if(scan.hasNextInt())
                            ((CommandTell) c).getParams().add(scan.nextInt());
                        else if(hasNextVariable(scan))
                            ((CommandTell) c).getParams().add(variables.get(scan.next()));
                    }
                    break;
                default:
                    // TODO: Handle bad input
                    return errorAndExit("Bad prefix: " + prefix);
            }
            program.add(c);
        }
        return program;
    }

    private boolean hasNextVariable(Scanner scan) {
        boolean isValid = false;
        for(String var : variables.keySet()){
            isValid |= scan.hasNext(var);
        }
        return isValid;
    }

    private List<Command> errorAndExit(String message){
        System.err.println(message);
        Alert alert = new Alert(Alert.AlertType.ERROR, myResources.getString("CommandError"));
        alert.showAndWait();
        return new ArrayList<>();
    }

    @Override
    public List<String> getRecentCommandStrings(){
        List<String> recent = new ArrayList<>();
        for (Command c: program){
            recent.add(c.toString());
        }
        return recent;
    }
    public void setLanguage(ResourceBundle resources) {
        myResources = resources;
    }
}
