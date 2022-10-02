package oolala.Command;

import javafx.animation.SequentialTransition;
import oolala.CanvasScreen;
import oolala.TurtleView;

public class CommandBackward extends Command {

    public CommandBackward(){
        prefix =CmdName.BACK;
    }

    public void runCommand(TurtleView turtle, CanvasScreen canvas, SequentialTransition animation) {
        turtle.move(-param, canvas, animation);
    }
}
