package oolala;

public class CommandForward extends Command {
    public CommandForward(){
        prefix =CmdName.FORWARD;
    }

    public void runCommand(Turtle turtle, AppView display) {
        turtle.moveForward(param, display);
    }
}
