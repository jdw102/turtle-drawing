package oolala.Command;

import javafx.animation.SequentialTransition;
import oolala.Views.ViewComponents.CanvasScreen;
import oolala.Views.TurtleView;

public class CommandMake extends Command {

    private String var;

    public CommandMake() {
        prefix = CommandName.MAKE;
        var = "";
    }

    public void runCommand(TurtleView turtle, CanvasScreen canvas, SequentialTransition animation) {

    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }

    @Override
    public String toString() {
        return "make :" + var + " " + super.param;
    }
}