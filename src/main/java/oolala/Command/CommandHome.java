package oolala.Command;

import javafx.animation.SequentialTransition;
import oolala.Views.ViewComponents.CanvasScreen;
import oolala.Views.TurtleView;

public class CommandHome extends Command {
    public CommandHome(){
        prefix =CmdName.HOME;
    }

    public void runCommand(TurtleView turtle) {
        turtle.home();
    }
}
