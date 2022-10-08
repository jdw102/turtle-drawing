package oolala.Command;

import javafx.animation.SequentialTransition;
import oolala.Views.ViewComponents.CanvasScreen;
import oolala.Views.TurtleView;

public class CommandPenUp extends Command {
    public CommandPenUp(){
        prefix =CommandName.PENUP;
    }

    public void runCommand(TurtleView turtle) {
        turtle.putPenUp();
    }
}
