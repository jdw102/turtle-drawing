package oolala;

import javafx.animation.SequentialTransition;

public class CommandPenUp extends Command {
    public CommandPenUp(){
        prefix =CmdName.PENUP;
    }

    public void runCommand(TurtleView turtle, CanvasScreen canvas, SequentialTransition animation) {
        turtle.putPenUp();
    }
}
