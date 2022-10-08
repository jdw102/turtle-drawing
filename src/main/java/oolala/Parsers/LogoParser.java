package oolala.Parsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;

import oolala.Command.*;

/**
 * The internal parser for each Logo command.
 *
 * @author Aditya Paul
 */
public class LogoParser extends Parser {
    ResourceBundle myResources;
    Map<String, Integer> variables;
    private String token;


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
    public List<Command> parse(String inputText) throws IllegalStateException {
        program = new ArrayList<>();
        Command c;
        Scanner scan = new Scanner(inputText);
        while (scan.hasNext()) {
            token = "";
            String prefix = scan.next();
            if (prefix.charAt(0) == '#') {
                scan.nextLine();
                continue;
            }
//<<<<<<< HEAD
//            switch (prefix) {
//                case "fd": case "forward":
//                    c = new CommandForward();
//                    if (scan.hasNextInt())
//                        c.setParam(scan.nextInt());
//                    else {
//                        tkn = scan.next();
//                        if (variables.containsKey(tkn))
//                            c.setParam(variables.get(tkn));
//                        else
//                            return errorAndExit("Missing parameters for FD command!");
//                    }
//                    break;
//                case "bk": case "back":
//                    c = new CommandBackward();
//                    System.out.println("Current token " + prefix);
//                    if (scan.hasNextInt())
//                        c.setParam(scan.nextInt());
//                    else {
//                        tkn = scan.next();
//                        if (variables.containsKey(tkn))
//                            c.setParam(variables.get(tkn));
//                        else
//                            return errorAndExit("Missing parameters for BK command!");
//                    }
//                    break;
//                case "lt": case "left":
//                    c = new CommandLeft();
//                    if (scan.hasNextInt())
//                        c.setParam(scan.nextInt());
//                    else {
//                        tkn = scan.next();
//                        if (variables.containsKey(tkn))
//                            c.setParam(variables.get(tkn));
//                        else
//                            return errorAndExit("Missing parameters for LT command!");
//                    }
//                    break;
//                case "rt": case "right":
//                    c = new CommandRight();
//                    if (scan.hasNextInt())
//                        c.setParam(scan.nextInt());
//                    else {
//                        tkn = scan.next();
//                        if (variables.containsKey(tkn))
//                            c.setParam(variables.get(tkn));
//                        else
//                            return errorAndExit("Missing parameters for RT command!");
//                    }
//                    break;
//                case "pd": case "pendown":
//                    c = new CommandPenDown();
//                    break;
//                case "pu": case "penup":
//                    c = new CommandPenUp();
//                    break;
//                case "st": case "show": case "showt":
//                    c = new CommandShowTurtle();
//                    break;
//                case "ht": case "hide": case "hidet":
//                    c = new CommandHideTurtle();
//                    break;
//                case "clearscreen": case "cs":
//                    c = new CommandClear();
//                    break;
//                case "home":
//                    c = new CommandHome();
//                    break;
//                case "stamp":
//                    c = new CommandStamp();
//                    break;
//                case "towards":
//                    c = new CommandTowards();
//                    for(int i = 0; i < 2; i++) {
//                        if (scan.hasNextInt())
//                            ((CommandTowards) c).getParams().add(scan.nextInt());
//                        else {
//                            tkn = scan.next();
//                            if (variables.containsKey(tkn))
//                                ((CommandTowards) c).getParams().add(variables.get(tkn));
//                            else
//                                return errorAndExit("Missing parameters for TOWARDS command!");
//                        }
//                    }
//                    break;
//                case "goto": case "setxy":
//                    c = new CommandGoto();
//                    for(int i = 0; i < 2; i++) {
//                        if (scan.hasNextInt())
//                            ((CommandGoto) c).getParams().add(scan.nextInt());
//                        else {
//                            tkn = scan.next();
//                            if (variables.containsKey(tkn))
//                                ((CommandGoto) c).getParams().add(variables.get(tkn));
//                            else
//                                return errorAndExit("Missing parameters for GOTO/SETXY command!");
//                        }
//                    }
//                    break;
//                case "make": case "set":
//                    // Get name
//                    c = new CommandMake();
//                    tkn = scan.next();
//                    if(tkn.charAt(0) != ':')
//                        return errorAndExit("Incorrect variable name for make command!");
//                    System.out.println("Got token " + tkn);
//                    // TODO: refactor this into a single function
//                    if (scan.hasNextInt())
//                        variables.put(tkn.substring(1), scan.nextInt());
//                    else {
//                        tkn = scan.next();
//                        if (variables.containsKey(tkn))
//                            variables.put(tkn.substring(1), variables.get(tkn));
//                        else
//                            return errorAndExit("Incorrect variable value for make command!");
//                    }
//                    ((CommandMake) c).setVar(tkn.substring(1));
//                    c.setParam(variables.get(tkn));
//                    break;
//                case "tell":
//                    c = new CommandTell();
//                    if (!(scan.hasNextInt() || hasNextVariable(scan)))
//                        return errorAndExit("Missing parameters for TELL command!");
//                    while (scan.hasNextInt() || hasNextVariable(scan)) {
//                        if(scan.hasNextInt())
//                            ((CommandTell) c).getParams().add(scan.nextInt());
//                        else if(hasNextVariable(scan))
//                            ((CommandTell) c).getParams().add(variables.get(scan.next()));
//                    }
//                    break;
//                default:
//                    // TODO: Handle bad input
//                    return errorAndExit("Bad prefix: " + prefix);
//            }
//=======
            c = switch (prefix) {
                case "fd", "bk", "rt", "lt", "forward", "backward", "left", "right" ->
                        parseCommandWithOneParameter(scan, prefix);
                case "goto", "setxy", "towards" -> parseCommandNParameters(scan, prefix, 2);
                case "tell" -> parseCommandTell(scan);
                case "cs", "clearscreen" -> new CommandClear();
                case "pd", "pendown" -> new CommandPenDown();
                case "pu", "penup" -> new CommandPenUp();
                case "st", "show", "showt" -> new CommandShowTurtle();
                case "ht", "hide", "hidet" -> new CommandHideTurtle();
                case "home" -> new CommandHome();
                case "stamp" -> new CommandStamp();
                case "make", "set" -> parseMake(scan, prefix);
                default -> throw new IllegalStateException(myResources.getString("InvalidCommand"));
            };
            program.add(c);
        }
        return program;
    }

    private Command parseMake(Scanner scan, String prefix) throws IllegalStateException {
        CommandMake c = new CommandMake();
        token = scan.next();
        if (token.charAt(0) != ':') {
            throw new IllegalStateException(String.format(myResources.getString("InvalidVariableMake"), prefix));
        }
        if (scan.hasNextInt()) {
            variables.put(token.substring(1), scan.nextInt());
        } else {
            token = scan.next();
            if (variables.containsKey(token))
                variables.put(token.substring(1), variables.get(token));
            else
                throw new IllegalStateException(String.format(myResources.getString("InvalidVariableMake"), prefix));
        }
        c.setVar(token.substring(1));
        c.setParam(variables.get(token.substring(1)));

        return c;
    }


    private Command parseCommandTell(Scanner scan) throws IllegalStateException {
        CommandTell c = new CommandTell();
        int param = 0;

        if (!(scan.hasNextInt() || hasNextVariable(scan)))
            throw new IllegalStateException(String.format(myResources.getString("MissingParameter"), "Tell"));
        while (scan.hasNextInt() || hasNextVariable(scan)) {
            if (scan.hasNextInt())
                param = scan.nextInt();
            else if (hasNextVariable(scan))
                param = variables.get(scan.next());

            if (param > 0)
                c.getParams().add(param);
            else
                throw new IllegalStateException(myResources.getString("InvalidNegativeParameter"));
        }
        return c;
    }

    private Command parseCommandNParameters(Scanner scan, String prefix, int numOfParams) throws IllegalStateException {
        Command c;
        int paramCount = 0;
        int param = 0;
        switch (prefix) {
            case "goto", "setxy" -> c = new CommandGoto();
            case "towards" -> c = new CommandTowards();
            default -> throw new IllegalStateException(myResources.getString("InvalidCommand"));
        }
        if (!(scan.hasNextInt() || hasNextVariable(scan)))
            throw new IllegalStateException(String.format(myResources.getString("MissingParameter"), prefix));
        while (scan.hasNextInt() || hasNextVariable(scan)) {
            paramCount++;
            if (scan.hasNextInt())
                param = scan.nextInt();
            else if (hasNextVariable(scan))
                param = variables.get(scan.next());
            c.getParams().add(param);
       }
        if (paramCount != numOfParams)
            throw new IllegalStateException(String.format(myResources.getString("InvalidNumberOfParameters"), numOfParams, prefix));
        return c;
    }

    private Command parseCommandWithOneParameter(Scanner scan, String prefix) throws IllegalStateException {
        Command c;
        String tkn;
        int param;
        if (scan.hasNextInt())
            param = scan.nextInt();
        else {
            token = scan.next();
            if (variables.containsKey(token))
                param = variables.get(token);
            else
                throw new IllegalStateException(String.format(myResources.getString("MissingParameter"), prefix));
        }
        switch (prefix) {
            case "fd", "forward" -> c = new CommandForward();
            case "bk", "backward" -> c = new CommandBackward();
            case "lt", "left" -> c = new CommandLeft();
            case "rt", "right" -> c = new CommandRight();
            default -> throw new IllegalStateException(myResources.getString("InvalidCommand"));
        }
        if (scan.hasNextInt()) {
            c.setParam(scan.nextInt());
        } else {
            tkn = scan.next();
            if (variables.containsKey(tkn))
                c.setParam(variables.get(tkn));
            else
                throw new IllegalStateException(String.format(myResources.getString("MissingParameter"), prefix));
        }
        return c;
    }

    @Override
    public List<String> getRecentCommandStrings() {
        List<String> recent = new ArrayList<>();
        for (Command c : program) {
            recent.add(c.toString());
        }
        return recent;
    }

    private boolean hasNextVariable(Scanner scan) {
        boolean isValid = false;
        for (String var : variables.keySet()) {
            isValid |= scan.hasNext(var);
        }
        return isValid;
    }
}
