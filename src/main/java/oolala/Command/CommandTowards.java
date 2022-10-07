package oolala.Command;

import java.util.ArrayList;
import javafx.animation.SequentialTransition;
import oolala.Views.TurtleView;
import oolala.Views.ViewComponents.CanvasScreen;

public class CommandTowards extends Command {
  public CommandTowards(){
    prefix = CmdName.TOWARDS;
    params = new ArrayList<Integer>();
  }

  public ArrayList<Integer> getParams() { return params; }

  public void runCommand(TurtleView turtle, CanvasScreen canvas, SequentialTransition animation) {
    double angle = Math.atan2(params.get(1) - (int) turtle.getModel().getPosY(),
                              params.get(0) - (int) turtle.getModel().getPosX()) / Math.PI * 180;
    turtle.turnTurtle((int) angle, animation);
  }
}
