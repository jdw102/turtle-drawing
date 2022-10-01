package oolala;

import javafx.animation.SequentialTransition;

public class CommandShowTurtle extends Command {

    public CommandShowTurtle(){
        prefix =CmdName.SHOWT;
    }
    public void runCommand(TurtleView turtle, CanvasScreen canvas, SequentialTransition animation) {
        turtle.showTurtle(animation);
    }
}
