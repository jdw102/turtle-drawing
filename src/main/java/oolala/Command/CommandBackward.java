package oolala.Command;

import javafx.animation.SequentialTransition;
import oolala.Views.ViewComponents.CanvasScreen;
import oolala.Views.TurtleView;

public class CommandBackward extends Command {

    public CommandBackward(){
        prefix =CmdName.BACK;
    }

    public void runCommand(TurtleView turtle) {
        turtle.move(-param);
    }
}
