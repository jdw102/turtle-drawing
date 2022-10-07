package oolala.Command;

import javafx.animation.SequentialTransition;
import oolala.Views.ViewComponents.CanvasScreen;
import oolala.Views.TurtleView;

public class CommandHideTurtle extends Command {
    public CommandHideTurtle(){
        prefix =CmdName.HIDET;
    }

    public void runCommand(TurtleView turtle) {
        turtle.hideTurtle();
    }
}
