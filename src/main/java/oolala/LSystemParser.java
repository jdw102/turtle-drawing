package oolala;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Scanner;
import oolala.Command.Command;

public class LSystemParser extends Parser{

    public static final char[] ALPHA_SYM = {'f', 'g', 'a', 'b', '+', '-', 'x'};
    public static final String[] ALPHA_COMM = {"pd fd length", "pu fd length", "pu bk length", "pd bk length",
            "rt angle", "lt angle", "stamp"};
    public static final int DEFAULT_DIST = 10;
    public static final int DEFAULT_ANGLE = 30;
    public static final int DEFAULT_LEVEL = 3;
    public static final String LENGTH_MARKER = "length";
    public static final String ANGLE_MARKER = "angle";

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
    private TurtleParser logoTurtleParser;
    private ResourceBundle myResources;

    public LSystemParser(ResourceBundle resources) {
        myResources = resources;
        alphabet = new HashMap<>();
        for (int i = 0; i < ALPHA_COMM.length; i++) {
            alphabet.put(ALPHA_SYM[i], ALPHA_COMM[i]);
        }
        rules = new HashMap<>();
        logoTurtleParser = new TurtleParser(myResources);
    }

    /**
     * A function that expands the given root according to the given rules.
     *
     * @return a String object containing the root string expanded the specified number of times
     */
    String applyRules() {
        String expanded = start;
        for (int i = 0; i < getLevel(); i++) {
            String nextLevel = "";
            for (int j = 0; j < expanded.length(); j++) {
                if (rules.containsKey(expanded.charAt(j))) {
                    nextLevel = nextLevel.concat(rules.get(expanded.charAt(j)));
                }  else {
                    nextLevel = nextLevel.concat(Character.toString(expanded.charAt(j)));
                }
            }
            expanded = nextLevel;
        }
        return expanded;
    }

    String getCommandString(String expansion) {
        String commandString = "";
        for (int i = 0; i < expansion.length(); i++) {
            char currChar = expansion.charAt(i);
            if (alphabet.containsKey(currChar)) {
                String cmd = alphabet.get(currChar);
                if (usingRandomDist)
                    dist = (int) Math.round(Math.random() * (distMax - distMin) + distMin);
                if (usingRandomAngle)
                    dist = (int) Math.round(Math.random() * (angMax - angMin) + angMin);
                cmd = cmd.replace(LENGTH_MARKER, Integer.toString(this.getDist()));
                cmd = cmd.replace(ANGLE_MARKER, Integer.toString(this.getAng()));
                commandString = commandString.concat(cmd).concat(" ");
            }
        }
        return commandString;
    }

    /**
     * A method to configure the parser based on the inputs given in the
     * IDE text box.
     *
     * @param configString - A string from the console containing all the
     *                     commands given to the application by the user
     * @author Aditya Paul
     */
    public void parseConfig(String configString) {
        configString = configString.toLowerCase();
        Scanner scan = new Scanner(configString);

        char symbol;
        String expansion;
        while (scan.hasNext()) {
            String prefix = scan.next();
            if (prefix.charAt(0) == '#') {
                scan.nextLine();
                continue;
            }
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
                    expansion = scan.nextLine();
                    expansion = expansion.substring(expansion.indexOf('\"'), expansion.lastIndexOf('\"') - 2);
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

    public ArrayList<Command> parse(String input) {
        parseConfig(input);
        return logoTurtleParser.parse(getCommandString(applyRules()));
    }
}
