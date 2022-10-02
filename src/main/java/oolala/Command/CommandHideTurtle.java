package oolala.Command;

import javafx.animation.SequentialTransition;
import oolala.CanvasScreen;
import oolala.TurtleView;

public class CommandHideTurtle extends Command {
    public CommandHideTurtle(){
        prefix =CmdName.HIDET;
    }

    public void runCommand(TurtleView turtle, CanvasScreen canvas, SequentialTransition animation) {
        turtle.hideTurtle(animation);
    }
}
