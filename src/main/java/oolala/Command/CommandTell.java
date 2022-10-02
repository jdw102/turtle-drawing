package oolala.Command;

import javafx.animation.SequentialTransition;
import oolala.CanvasScreen;
import oolala.TurtleView;

import java.util.ArrayList;

public class CommandTell extends Command {

    public CommandTell() {
        prefix = CmdName.TELL;
        params = new ArrayList<Integer>();
    }

    public ArrayList<Integer> getParams(){
        return params;
    }

    public void runCommand(TurtleView turtle, CanvasScreen canvas, SequentialTransition animation) {

    }
}



