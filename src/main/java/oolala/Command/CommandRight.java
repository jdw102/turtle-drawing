package oolala.Command;

import javafx.animation.SequentialTransition;
import oolala.CanvasScreen;
import oolala.TurtleView;

public class CommandRight extends Command {

    public CommandRight(){
        prefix =CmdName.RIGHT;
    }
    public void runCommand(TurtleView turtle, CanvasScreen canvas, SequentialTransition animation) {
        turtle.rotateTurtle(param, animation);
    }
}
