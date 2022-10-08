package oolala.Command;

import javafx.animation.SequentialTransition;
import oolala.Views.ViewComponents.CanvasScreen;
import oolala.Views.TurtleView;

public class CommandRight extends Command {

    public CommandRight(){
        prefix =CommandName.RIGHT;
    }
    public void runCommand(TurtleView turtle) {
        turtle.rotateTurtle(param);
    }
}
