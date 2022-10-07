package oolala.Parsers;

import java.util.ArrayList;
import java.util.List;
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


    public LogoParser(ResourceBundle resourceBundle) {
        myResources = resourceBundle;
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
            String prefix = scan.next();
            if (prefix.charAt(0) == '#') {
                scan.nextLine();
                continue;
            }
            c = switch (prefix) {
                case "fd", "bk", "rt", "lt" -> parseCommandWithParameter(scan, prefix);
                case "tell" -> parseTell(scan);
                case "pd" -> new CommandPenDown();
                case "pu" -> new CommandPenUp();
                case "st" -> new CommandShowTurtle();
                case "ht" -> new CommandHideTurtle();
                case "home" -> new CommandHome();
                case "stamp" -> new CommandStamp();
                default -> throw new IllegalStateException(myResources.getString("InvalidCommand"));
            };
            program.add(c);
        }
        return program;
    }

    private Command parseTell(Scanner scan) throws IllegalStateException {
        Command c;
        c = new CommandTell();
        try {
            c.getParams().add(scan.nextInt());
        } catch (Exception e) {
            throw new IllegalStateException(String.format(myResources.getString("MissingParameter"), "Tell"));
        }
        return c;
    }

    private Command parseCommandWithParameter(Scanner scan, String prefix) throws IllegalStateException {
        Command c;
        int param;
        if (scan.hasNextInt())
            param = scan.nextInt();
        else
            throw new IllegalStateException(String.format(myResources.getString("MissingParameter"), prefix));
        switch (prefix) {
            case "fd" -> c = new CommandForward();
            case "bk" -> c = new CommandBackward();
            case "lt" -> c = new CommandLeft();
            case "rt" -> c = new CommandRight();
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

    public void setLanguage(ResourceBundle resources) {
        myResources = resources;
    }
}
