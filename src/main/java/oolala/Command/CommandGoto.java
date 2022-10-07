package oolala.Command;

import java.util.ArrayList;
import javafx.animation.SequentialTransition;
import oolala.Views.TurtleView;
import oolala.Views.ViewComponents.CanvasScreen;

public class CommandGoto extends Command {
  public CommandGoto(){
    prefix = CmdName.GOTO;
    params = new ArrayList<Integer>();
  }

  public ArrayList<Integer> getParams() { return params; }

  public void runCommand(TurtleView turtle, CanvasScreen canvas, SequentialTransition animation) {
    turtle.goTo(params.get(0), params.get(1), animation);
  }
}
