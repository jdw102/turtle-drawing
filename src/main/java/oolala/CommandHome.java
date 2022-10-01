package oolala;

import javafx.animation.SequentialTransition;

public class CommandHome extends Command {
    public CommandHome(){
        prefix =CmdName.HOME;
    }

    public void runCommand(TurtleView turtle, CanvasScreen canvas, SequentialTransition animation) {
        turtle.home(animation);
    }
}
