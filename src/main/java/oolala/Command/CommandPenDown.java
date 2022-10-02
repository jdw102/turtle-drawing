package oolala.Command;

import javafx.animation.SequentialTransition;
import oolala.CanvasScreen;
import oolala.TurtleView;

public class CommandPenDown extends Command {

    public CommandPenDown(){
        prefix =CmdName.PENDOWN;
    }
    public void runCommand(TurtleView turtle, CanvasScreen canvas, SequentialTransition animation) {
        turtle.putPenDown();
    }
}
