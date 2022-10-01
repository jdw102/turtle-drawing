package oolala;

import javafx.animation.SequentialTransition;

public class CommandForward extends Command {
    public CommandForward(){
        prefix =CmdName.FORWARD;
    }

    public void runCommand(TurtleView turtle, CanvasScreen canvas, SequentialTransition animation) {
        turtle.move(param, canvas, animation);
    }
}
