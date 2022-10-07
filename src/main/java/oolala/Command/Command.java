package oolala.Command;

import javafx.animation.SequentialTransition;
import oolala.Views.ViewComponents.CanvasScreen;
import oolala.Views.TurtleView;

import java.util.ArrayList;

/**
 *  A single class containing the information of each command.
 *  Might refactor later into separate classes for each command.
 *
 * @author Aditya Paul
 */
public class Command {
  public CmdName prefix;
  public enum CmdName {
    FORWARD, BACK, LEFT, RIGHT,
    PENDOWN, PENUP, SHOWT, HIDET,
    HOME, STAMP, TELL, CLEAR,
    TOWARDS, GOTO
  }

  public void setParam(int param) {
    this.param = param;
  }

  public int param;
  public ArrayList<Integer> params;

  public void runCommand(TurtleView turtle, CanvasScreen canvas, SequentialTransition animation){

  }
  @Override
  public String toString(){
    String s = "";
    switch (this.prefix){
      case FORWARD -> s ="fd";
      case BACK -> s = "bk";
      case LEFT -> s = "lt";
      case RIGHT -> s = "rt";
      case PENDOWN -> s = "pd";
      case PENUP -> s = "pu";
      case SHOWT -> s = "st";
      case HIDET -> s = "ht";
      case HOME -> s = "home";
      case STAMP -> s = "stamp";
      case TELL -> s = "tell";
    }
    if (this.params != null){
      for (Integer i: this.params){
        s += " ";
        s += Integer.toString(i);
      }
    }
    else if (this.param != 0){
      s += " ";
      s += Integer.toString(this.param);
    }
    return s;
  }
}
