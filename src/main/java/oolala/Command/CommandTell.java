package oolala.Command;

import javafx.animation.SequentialTransition;
import oolala.Views.ViewComponents.CanvasScreen;
import oolala.Views.TurtleView;

import java.util.ArrayList;

public class CommandTell extends Command {

    public CommandTell() {
        prefix = CommandName.TELL;
        params = new ArrayList<Integer>();
    }

    public ArrayList<Integer> getParams(){
        return params;
    }

    public void runCommand(TurtleView turtle) {

    }
}



