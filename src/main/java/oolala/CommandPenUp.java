package oolala;

public class CommandPenUp extends Command {
    public CommandPenUp(){
        prefix =CmdName.PENUP;
    }

    public void runCommand(Turtle turtle, AppView display) {
        turtle.putPenUp();
    }
}
