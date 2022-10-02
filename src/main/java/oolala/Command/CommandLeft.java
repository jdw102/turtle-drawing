package oolala.Command;

import javafx.animation.SequentialTransition;
import oolala.CanvasScreen;
import oolala.TurtleView;

public class CommandLeft extends Command {

    public CommandLeft(){
        prefix =CmdName.LEFT;
    }
    public void runCommand(TurtleView turtle, CanvasScreen canvas, SequentialTransition animation) {
        turtle.rotateTurtle(-param, animation);
    }
}
