package oolala.Parsers;

import java.util.*;

import javafx.scene.control.Alert;
import oolala.Command.Command;
import oolala.Command.CommandTell;
import oolala.Models.TurtleModel;

public class LSystemParser extends Parser {

    public static final char[] ALPHA_SYM = {'f', 'g', 'a', 'b', '+', '-', 'x'};
    public static final String[] ALPHA_COMM = {"pd fd length", "pu fd length", "pu bk length", "pd bk length",
            "rt angle", "lt angle", "stamp"};
    public static final int DEFAULT_DIST = 10;
    public static final int DEFAULT_ANGLE = 30;
    public static final int DEFAULT_LEVEL = 3;
    public static final int DEFAULT_LSF = 1;
    public static final String LENGTH_MARKER = "length";
    public static final String ANGLE_MARKER = "angle";
    public static final String QUOTE_REGEX = "([\"'])(?:(?=(\\\\?))\\2.)*?\\1";

    private boolean usingRandomDist = false;
    private boolean usingRandomAngle = false;
    private int distMin = DEFAULT_DIST;
    private int distMax = DEFAULT_DIST;
    private int angMin = DEFAULT_ANGLE;
    private int angMax = DEFAULT_ANGLE;
    private int dist = DEFAULT_DIST;
    private int ang = DEFAULT_ANGLE;
    private int level = DEFAULT_LEVEL;
    private int lsf = DEFAULT_LSF;
    private Map<Character, String> alphabet;
    private Map<Character, String> rules;
    private Map<Character, String> altRules;
    private Map<Character, Double> ruleProbs;
    private String start;
    private LogoParser logoParser;
    private ResourceBundle myResources;
    private List<String> seenCommands;
    private List<Integer> turtles;

    public LSystemParser(ResourceBundle resources) {
        myResources = resources;
        turtles = new ArrayList<>();
        seenCommands = new ArrayList<>();
        alphabet = new HashMap<>();
        start = "";
        for (int i = 0; i < ALPHA_COMM.length; i++) {
            alphabet.put(ALPHA_SYM[i], ALPHA_COMM[i]);
        }
        rules = new HashMap<>();
        altRules = new HashMap<>();
        ruleProbs = new HashMap<>();
        logoParser = new LogoParser(myResources);
        turtles.add(1);
    }
    private void reset(){
        alphabet.clear();
        for (int i = 0; i < ALPHA_COMM.length; i++) {
            alphabet.put(ALPHA_SYM[i], ALPHA_COMM[i]);
        }
        rules.clear();
    }
    /**
     * A function that expands the given root according to the given rules.
     *
     * @return a String object containing the root string expanded the specified number of times
     */
    public String applyRules() {
        String expanded = start;
        Random rand = new Random();
        for (int i = 0; i < getLevel(); i++) {
            String nextLevel = "";
            for (int j = 0; j < expanded.length(); j++) {
                char symbol = expanded.charAt(j);
                if (rules.containsKey(symbol)) {
                    if (altRules.containsKey(symbol)) // TODO: refactor
                        nextLevel = (rand.nextDouble(1) < ruleProbs.get(symbol))
                            ? nextLevel.concat(altRules.get(symbol))
                            : nextLevel.concat(rules.get(symbol));
                    else
                        nextLevel = nextLevel.concat(rules.get(symbol));
                } else {
                    nextLevel = nextLevel.concat(Character.toString(symbol));
                }
            }
            expanded = nextLevel;
        }
        return expanded;
    }

    public String getCommandString(String expansion) {
        String commandString = "";
        for (Integer turtle : turtles) {
            int lsfPowers = 0;
            commandString = commandString.concat("tell " + turtle + " ");
            for (int i = 0; i < expansion.length(); i++) {
                char currChar = expansion.charAt(i);
                if (alphabet.containsKey(currChar)) {
                    seenCommands.add(Character.toString(currChar).toUpperCase());
                    String cmd = alphabet.get(currChar);
                    if (usingRandomDist)
                        dist = (int) Math.round(Math.random() * (distMax - distMin) + distMin)
                            * (int) Math.pow(lsf, lsfPowers);
                    if (usingRandomAngle)
                        ang = (int) Math.round(Math.random() * (angMax - angMin) + angMin)
                            * (int) Math.pow(lsf, lsfPowers);
                    cmd = cmd.replace(LENGTH_MARKER,
                        Integer.toString(this.getDist() * (int) Math.pow(lsf, lsfPowers)));
                    cmd = cmd.replace(ANGLE_MARKER, Integer.toString(this.getAng()));
                    commandString = commandString.concat(cmd).concat(" ");
                } else {
                    switch (currChar) {
                        case '<' -> lsfPowers++;
                        case '>' -> lsfPowers--;
                    }
                }
            }
        }
        System.out.println(commandString);
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
        double prob;
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
                case "randomp" -> {
                    symbol = scan.next().charAt(0);
                    prob = scan.nextInt();
                    expansion = scan.next();
                    ruleProbs.put(symbol, prob);
                    altRules.put(symbol, expansion);
                }
                case "set" -> {
                    symbol = scan.next().charAt(0);
                    expansion = scan.findInLine(QUOTE_REGEX);
                    expansion = expansion.substring(1, expansion.length() - 1);
                    alphabet.put(symbol, expansion);
                }
                case "tell" -> {
                    turtles.clear();
                    if (!scan.hasNextInt()) {
                        // TODO: Handle
                        System.err.println("Missing parameters for TELL command!");
                    }
                    while (scan.hasNextInt())
                        turtles.add(scan.nextInt());
                }
                default -> {
                    // TODO: Handle bad input
                    Alert alert = new Alert(Alert.AlertType.ERROR, myResources.getString("CommandError"));
                    alert.showAndWait();
                    System.err.println("Unrecognized Command!");
                }
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

    public List<Command> parse(String input) {
        reset();
        parseConfig(input);
        return logoParser.parse(getCommandString(applyRules()));
    }
    public List<String> getRecentCommandStrings(){
        return seenCommands;
    }

    public int getLsf() {
        return lsf;
    }

    public void setLsf(int lsf) {
        this.lsf = lsf;
    }
}
