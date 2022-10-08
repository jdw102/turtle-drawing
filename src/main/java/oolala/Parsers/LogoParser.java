package oolala.Parsers;

import java.util.*;

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
        c.setParam(param);
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
