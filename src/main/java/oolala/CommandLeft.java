package oolala;

import javafx.animation.SequentialTransition;

public class CommandLeft extends Command {

    public CommandLeft(){
        prefix =CmdName.LEFT;
    }
    public void runCommand(TurtleView turtle, CanvasScreen canvas, SequentialTransition animation) {
        turtle.rotateTurtle(-param, animation);
    }
}
