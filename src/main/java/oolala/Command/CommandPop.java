package oolala.Command;

import oolala.Views.TurtleView;

public class CommandPop extends Command{
  public CommandPop(){
    prefix = CommandName.PENDOWN;
  }
  public void runCommand(TurtleView turtle) { turtle.popTurtle(); }
}
