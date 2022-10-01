package oolala;

public class CommandPenDown extends Command {

    public CommandPenDown(){
        prefix =CmdName.PENDOWN;
    }
    public void runCommand(Turtle turtle, AppView display) {
        turtle.putPenDown();
    }
}
