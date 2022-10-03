package oolala.Command;

import javafx.animation.SequentialTransition;
import oolala.CanvasScreen;
import oolala.TurtleView;

public class CommandStamp extends Command {
    public CommandStamp(){
        prefix =CmdName.STAMP;
    }

    public void runCommand(TurtleView turtle, CanvasScreen canvas, SequentialTransition animation) {
        turtle.stamp(canvas, animation);
    }
}
