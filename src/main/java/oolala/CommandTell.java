package oolala;

import java.util.ArrayList;

public class CommandTell extends Command {

    public CommandTell() {
        prefix = CmdName.TELL;
        params = new ArrayList<Integer>();
    }

    public ArrayList<Integer> getParams(){
        return params;
    }

    public void runCommand(Turtle turtle, AppView display) {
        turtle.stamp(display);
    }
}



