package oolala.Command;

import javafx.animation.SequentialTransition;
import oolala.Views.ViewComponents.CanvasScreen;
import oolala.Views.TurtleView;

public class CommandPenDown extends Command {

    public CommandPenDown(){
        prefix =CommandName.PENDOWN;
    }
    public void runCommand(TurtleView turtle) {
        turtle.putPenDown();
    }
}
