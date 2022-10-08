package oolala.Command;

import javafx.animation.SequentialTransition;
import oolala.Views.ViewComponents.CanvasScreen;
import oolala.Views.TurtleView;

public class CommandMake extends Command {
  public CommandMake(){
    prefix =CmdName.MAKE;
  }

  public void runCommand(TurtleView turtle, CanvasScreen canvas, SequentialTransition animation) {

  }
}
