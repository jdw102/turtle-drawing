package oolala.Command;

import javafx.animation.SequentialTransition;
import oolala.CanvasScreen;
import oolala.TurtleView;

public class CommandShowTurtle extends Command {

    public CommandShowTurtle(){
        prefix =CmdName.SHOWT;
    }
    public void runCommand(TurtleView turtle, CanvasScreen canvas, SequentialTransition animation) {
        turtle.showTurtle(animation);
    }
}
