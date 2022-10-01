package oolala;

import javafx.animation.SequentialTransition;

public class CommandBackward extends Command {

    public CommandBackward(){
        prefix =CmdName.BACK;
    }

    public void runCommand(TurtleView turtle, CanvasScreen canvas, SequentialTransition animation) {
        turtle.move(-param, canvas, animation);
    }
}
