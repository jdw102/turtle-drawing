package oolala;

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
    HOME, STAMP, TELL
  }
  public int param;
  public ArrayList<Integer> params;

  public Command(){

  }

  public Command(CmdName prefix, int param){
    this.prefix = prefix;
    this.param = param;
  }

  public Command(CmdName prefix, ArrayList<Integer> params){
    this.prefix = prefix;
    this.params = params;
  }
}
