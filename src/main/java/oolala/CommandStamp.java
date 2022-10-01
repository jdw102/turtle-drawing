package oolala;

import javafx.animation.SequentialTransition;

public class CommandStamp extends Command {
    public CommandStamp(){
        prefix =CmdName.STAMP;
    }

    public void runCommand(TurtleView turtle, CanvasScreen canvas, SequentialTransition animation) {
        turtle.stamp(canvas, animation);
    }
}
