package oolala.Command;

import javafx.animation.SequentialTransition;
import oolala.Views.ViewComponents.CanvasScreen;
import oolala.Views.TurtleView;

public class CommandStamp extends Command {
    public CommandStamp(){
        prefix =CommandName.STAMP;
    }

    public void runCommand(TurtleView turtle) {
        turtle.stamp();
    }
}
