package oolala;

public class CommandHideTurtle extends Command {
    public CommandHideTurtle(){
        prefix =CmdName.HIDET;
    }

    public void runCommand(Turtle turtle, AppView display) {
        turtle.hideTurtle();
    }
}
