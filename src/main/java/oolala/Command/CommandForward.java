package oolala.Command;

import javafx.animation.SequentialTransition;
import oolala.Models.TurtleModel;
import oolala.Views.ViewComponents.CanvasScreen;
import oolala.Views.TurtleView;

public class CommandForward extends Command {
    public CommandForward(){
        prefix =CommandName.FORWARD;
    }

    public void runCommand(TurtleView turtle) {
        turtle.move(param);
    }
}
