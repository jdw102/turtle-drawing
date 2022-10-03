package oolala.Command;

import javafx.animation.SequentialTransition;
import oolala.CanvasScreen;
import oolala.TurtleView;

public class CommandPenUp extends Command {
    public CommandPenUp(){
        prefix =CmdName.PENUP;
    }

    public void runCommand(TurtleView turtle, CanvasScreen canvas, SequentialTransition animation) {
        turtle.putPenUp();
    }
}
