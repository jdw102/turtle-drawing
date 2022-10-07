package oolala.Command;

import javafx.animation.SequentialTransition;
import oolala.Views.TurtleView;
import oolala.Views.ViewComponents.CanvasScreen;

public class CommandClear extends Command{
  public CommandClear(){
    prefix =CmdName.CLEAR;
  }

  public void runCommand(TurtleView turtle, CanvasScreen canvas, SequentialTransition animation) {
    turtle.clear(canvas, animation);
  }
}
