package oolala.Command;

import javafx.animation.SequentialTransition;
import oolala.Views.ViewComponents.CanvasScreen;
import oolala.Views.TurtleView;

public class CommandShowTurtle extends Command {

    public CommandShowTurtle() {
        prefix = CommandName.SHOWT;
    }

    public void runCommand(TurtleView turtle) {
        turtle.showTurtle();
    }
}
