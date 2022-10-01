package oolala;

public class CommandShowTurtle extends Command {

    public CommandShowTurtle(){
        prefix =CmdName.SHOWT;
    }
    public void runCommand(Turtle turtle, AppView display) {
        turtle.showTurtle();
    }
}
