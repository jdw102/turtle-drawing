package oolala;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import static oolala.Command.CmdName.TELL;

public abstract class AppModel {
    public AppView myDisplay;
    public AppModel(AppView display){
        myDisplay = display;
    }
    public void runApp(ArrayList<Command> commands){

        }
}
