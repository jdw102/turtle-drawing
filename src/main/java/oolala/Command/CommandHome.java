package oolala.Command;

import javafx.animation.SequentialTransition;
import oolala.CanvasScreen;
import oolala.TurtleView;

public class CommandHome extends Command {
    public CommandHome(){
        prefix =CmdName.HOME;
    }

    public void runCommand(TurtleView turtle, CanvasScreen canvas, SequentialTransition animation) {
        turtle.home(animation);
    }
}
