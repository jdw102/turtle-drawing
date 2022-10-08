package oolala.Command;

import java.util.ArrayList;
import javafx.animation.SequentialTransition;
import oolala.Views.TurtleView;
import oolala.Views.ViewComponents.CanvasScreen;

public class CommandGoto extends Command {
    public CommandGoto(){
        prefix = CommandName.GOTO;
        params = new ArrayList<Integer>();
    }

    public void runCommand(TurtleView turtle) {
        turtle.goTo(params.get(0), params.get(1));
    }
}
