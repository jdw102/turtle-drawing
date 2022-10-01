package oolala;

import javafx.animation.SequentialTransition;

public class CommandPenDown extends Command {

    public CommandPenDown(){
        prefix =CmdName.PENDOWN;
    }
    public void runCommand(TurtleView turtle, CanvasScreen canvas, SequentialTransition animation) {
        turtle.putPenDown();
    }
}
