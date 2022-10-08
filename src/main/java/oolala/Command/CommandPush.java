package oolala.Command;

import oolala.Views.TurtleView;

public class CommandPush extends Command{
  public CommandPush(){
    prefix = CommandName.PENDOWN;
  }
  public void runCommand(TurtleView turtle) { turtle.pushTurtle(); }
}