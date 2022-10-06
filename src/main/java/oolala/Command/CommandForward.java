package oolala.Command;

import javafx.animation.SequentialTransition;
import oolala.Models.TurtleModel;
import oolala.Views.ViewComponents.CanvasScreen;
import oolala.Views.TurtleView;

public class CommandForward extends Command {
    public CommandForward(){
        prefix =CmdName.FORWARD;
    }

    public void runCommand(TurtleModel turtle, CanvasScreen canvas, SequentialTransition animation) {
        turtle.move(param, canvas, animation);
    }
}
