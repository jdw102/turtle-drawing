package oolala;

import javafx.animation.SequentialTransition;

public class CommandRight extends Command {

    public CommandRight(){
        prefix =CmdName.RIGHT;
    }
    public void runCommand(TurtleView turtle, CanvasScreen canvas, SequentialTransition animation) {
        turtle.rotateTurtle(param, animation);
    }
}
