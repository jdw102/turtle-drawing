package oolala.Parsers;

import oolala.Command.Command;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    protected List<Command> program;

    public List<Command> parse(String input) {
        return program;
    }

    public void setLevel(int level) {
    }

    public List<String> getRecentCommandStrings() {
        return new ArrayList<>();
    }
}
