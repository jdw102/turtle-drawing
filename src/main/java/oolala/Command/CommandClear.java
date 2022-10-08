package oolala.Command;

import oolala.Views.TurtleView;

public class CommandClear extends Command{
    public CommandClear(){
        prefix = CommandName.CLEAR;
    }

    public void runCommand(TurtleView turtle) {
        turtle.clearTurtle();
    }
}

