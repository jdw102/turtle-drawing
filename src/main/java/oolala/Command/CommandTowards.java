package oolala.Command;

import java.util.ArrayList;

import javafx.animation.SequentialTransition;
import oolala.Views.TurtleView;
import oolala.Views.ViewComponents.CanvasScreen;

public class CommandTowards extends Command {
    public CommandTowards() {
        prefix = CommandName.TOWARDS;
        params = new ArrayList<Integer>();
    }

    public void runCommand(TurtleView turtle) {
        turtle.turnTurtle(params.get(0), params.get(1));
    }
}

