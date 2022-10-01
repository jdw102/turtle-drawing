package oolala;

import javafx.animation.SequentialTransition;

public class CommandHideTurtle extends Command {
    public CommandHideTurtle(){
        prefix =CmdName.HIDET;
    }

    public void runCommand(TurtleView turtle, CanvasScreen canvas, SequentialTransition animation) {
        turtle.hideTurtle(animation);
    }
}
